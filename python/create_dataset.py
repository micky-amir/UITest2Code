import json
import os
import re
import excels_reader
from code_tokenizer_processor import preprocess
import utils

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
"""the path to the UITest2Code directory"""

id_counter = 0
"""identifier for the tokenized tests"""


def tokenize_tests_from_single_website(file_name):
    """
    Tokenizes tests from a single class and writes them into a json file

    :param file_name: string that contains the file name of tests from a single website
    """
    filepath = os.path.join(project_path, 'src', 'test', 'java', 'websites', file_name)
    with open(filepath, "r", encoding="utf-8") as file:
        line = file.readline()
        all_tests = []
        all_tests_names = []
        in_test = False
        brackets_counter = 0
        while line:
            if "@Test" in line:
                # starts reading test
                in_test = True
                current_test_code_lines = []
                line = file.readline()
                name = line.split("void ")[1].split("()")[0]
                all_tests_names.append(name)
                line = re.sub(r'public void [A-Za-z_0-9]*\(\)', 'void function()', line)
            if in_test:
                current_test_code_lines.append(line)
                brackets_counter += (line.count("{") - line.count("}"))
                if brackets_counter == 0:
                    # finish reading test
                    in_test = False
                    all_tests.append(current_test_code_lines)
            line = file.readline()

    # opening json file, writing into it, than gzip the file
    json_file_path = 'json_files/tokenized-class-' + file_name.split('.')[0] + ".json"
    write_tests_from_single_website_to_json(json_file_path, filepath, all_tests, all_tests_names)
    utils.json_to_gzip(json_file_path, 'gzip_files/java/')


def write_tests_from_single_website_to_json(json_file_path, path, all_tests, all_tests_names):
    """
    Writes tests to a json file

    :param json_file_path: string that contains a json file path to write into
    :param path: string that contains the file path of the original tests
    :param all_tests: a list[list[str]] of the tests from the original file
    :param all_tests_names: a list[str] of the tests' names
    """
    # todo might not work as expected for windows
    original_website_path_in_project = ''.join(re.split(r'(UITest2Code/)', path)[1:])
    with open(os.path.join(project_path, json_file_path), "w+") as json_result_file:
        for test, test_name in zip(all_tests, all_tests_names):
            instance = {'repo_name': test_name,
                        'ref': "refs/heads/master",
                        'path': original_website_path_in_project,
                        'content': ' '.join(test)}
            json_result_file.write(json.dumps(instance))
            json_result_file.write('\n')


def tokenize_tests():
    """Tokenizes all the tests from all the websites and writes them into json files"""
    list_of_flies = os.listdir(os.path.join(project_path, 'src', 'test', 'java', 'websites'))
    for file_name in list_of_flies:
        tokenize_tests_from_single_website(file_name)


def tok_to_final_json():
    """Goes through all the tok files, creates test cases dictionary for each and final tokenized json file"""
    global id_counter
    list_of_flies = os.listdir(os.path.join(project_path, 'gzip_files', 'java'))
    for file_name in list_of_flies:
        if file_name.endswith(".tok"):
            website_name = file_name.split('.')[0].split('-')[2]
            testcases_dictionary = excels_reader.read_single_excel(project_path, website_name)
            id_counter = utils.create_single_final_json('gzip_files/java/' + file_name, 'json_files/', website_name,
                                                        testcases_dictionary, id_counter)


if __name__ == "__main__":
    utils.create_directories()
    tokenize_tests()
    preprocess.preprocess_v2(os.path.join(project_path, 'gzip_files'), 'java', False, 110)
    tok_to_final_json()
    utils.delete_unnecessary_dirs_and_files('gzip_files', 'json_files')
