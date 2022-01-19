import json
import os
import gzip
import re

from plbart_relevant_code import preprocess

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))) + '/'


def tokenize_tests_from_single_website(file_name):
    """Tokenizes tests from a single class and writes them into a json file
    Parameters
        ----------
        file_name : string
            tests from a single website file name
    """
    filepath = project_path + 'src/test/java/websites/' + file_name
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
            if in_test:
                current_test_code_lines.append(line)
                brackets_counter += (line.count("{") - line.count("}"))
                if brackets_counter == 0:
                    # finish reading test
                    in_test = False
                    all_tests.append(current_test_code_lines)
            line = file.readline()

    # opening json file and writing into it
    json_file_path = project_path + 'json_files/tokenized-class-' + file_name.split('.')[0] + ".json"
    # json_result_file = open(json_file_path, "w+")
    write_tests_from_single_website_to_json(json_file_path, filepath, all_tests, all_tests_names)
    json_to_gzip(json_file_path)


def write_tests_from_single_website_to_json(json_file_path, path, all_tests, all_tests_names):
    """
    Writes tests to a json file
    Parameters
        ----------
        json_file_path : str
            json file path to write into
        path : str
            the file path of the original tests
        all_tests :  list[list[str]]
            the tests from the original file
        all_tests_names : list[str]
            the tests' names
    """

    original_website_path_in_project = ''.join(re.split(r'(UITest2Code/)', path)[1:])
    with open(json_file_path, "w+") as json_result_file:
        for test, test_name in zip(all_tests, all_tests_names):
            instance = {'repo_name': test_name,
                        'ref': "refs/heads/master",
                        'path': original_website_path_in_project,
                        'content': ' '.join(test)}
            json_result_file.write(json.dumps(instance))
            json_result_file.write('\n')


def json_to_gzip(json_file_path):
    """
    Formats a json file to a gzip file
    :param json_file_path: the json file's path
    """
    with open(json_file_path, 'rb') as json_file, gzip.open(
            project_path + 'gzip_files/java/' + os.path.basename(json_file.name) + '.gz', 'wb') as gz_file:
        gz_file.writelines(json_file)


def tokenize_tests():
    """Tokenizes all the tests from all the websites and writes them into json files"""
    list_of_flies = os.listdir(project_path + 'src/test/java/websites')
    for fileName in list_of_flies:
        tokenize_tests_from_single_website(fileName)


if __name__ == "__main__":
    tokenize_tests()
    preprocess.preprocess_v2(project_path + 'gzip_files/', 'java', False, 110)
