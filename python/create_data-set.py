import json
import os
import gzip
import re
import excels_reader
from plbart_relevant_code import preprocess
from bs4 import BeautifulSoup
import shutil

# the path to the UITest2Code directory
project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))).replace("\\", "/") + '/'

# identifier for the tokenized tests
id_counter = 0


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
    json_file_path = project_path + 'json_files/tokenized-class-' + file_name.split('.')[0] + ".json"
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


def create_single_final_json(file_name, website_name, testcases_dictionary):
    """
    Creates the final tokenized json file for a single website
    :param file_name: string that contains the tok file name
    :param website_name: string that contains the current website name
    :param testcases_dictionary: a dictionary that contains the tests from the website
    """
    global id_counter
    with open(project_path + 'gzip_files/java/' + file_name, 'r', encoding="utf-8") as tok_file, open(
            project_path + 'json_files/finished-tokenized-class-' + file_name.split('.')[0] + '.json',
            'w', encoding="utf-8") as json_file:
        line = tok_file.readline()
        while line:
            # gets original test id
            content = BeautifulSoup(line, features="html.parser")
            tag_name = content.findAll()[0].name
            test_id = tag_name.split('document_id="')[1].rsplit('_', 1)[0].upper()

            # gets the current teat from the dictionary
            current_test = testcases_dictionary[test_id]
            current_test_simplified = excels_reader.simplify_single_test(current_test)

            # creates a dictionary instance, and writes it to the new json file
            instance = {'id': id_counter,
                        'website': website_name,
                        'code': content.find(text=True)}
            instance.update(current_test_simplified)
            json_file.write(json.dumps(instance, ensure_ascii=False))
            json_file.write('\n')

            # updates variables
            id_counter += 1
            line = tok_file.readline()


def tokenize_tests():
    """Tokenizes all the tests from all the websites and writes them into json files"""
    list_of_flies = os.listdir(project_path + 'src/test/java/websites')
    for file_name in list_of_flies:
        tokenize_tests_from_single_website(file_name)


def tok_to_json():
    """
    Goes through all the tok files, creates test cases dictionary for each and final tokenized json file
    """
    list_of_flies = os.listdir(project_path + 'gzip_files/java/')
    for file_name in list_of_flies:
        if file_name.endswith(".tok"):
            website_name = file_name.split('.')[0].split('-')[2]
            testcases_dictionary = excels_reader.read_single_excel(project_path, website_name)
            create_single_final_json(file_name, website_name, testcases_dictionary)


def create_directories():
    """
    creates the json_files/ and gzip_files/java/ directories
    """
    try:
        new_directories = ['json_files/', 'gzip_files/', 'gzip_files/java/']
        for new_dir in new_directories:
            new_dir_path = project_path + new_dir
            if not os.path.isdir(new_dir_path):
                os.mkdir(new_dir_path)
    except OSError as error:
        print(error)


def delete_unnecessary_dirs_and_files():
    """
    deletes the draft files and directories
    """
    try:
        shutil.rmtree(project_path + 'gzip_files/')
        list_of_flies = os.listdir(project_path + 'json_files/')
        for file_name in list_of_flies:
            if not file_name.startswith("finished"):
                os.remove(project_path + 'json_files/' + file_name)
    except OSError as e:
        print("Error: %s - %s." % (e.filename, e.strerror))


if __name__ == "__main__":
    create_directories()
    tokenize_tests()
    preprocess.preprocess_v2(project_path + 'gzip_files/', 'java', False, 110)
    tok_to_json()
    delete_unnecessary_dirs_and_files()
