import json
import os
from collections import deque

def tokenize_tests_from_single_website(file_name):
    """Tokenizes tests from a single class and writes them into a json file
    Parameters
        ----------
        file_name : string
            tests from a single website file name
    """
    filepath = "C:/Users/Public/project/UITest2Code/src/test/java/websites/" + file_name
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
    json_result_file = open(
        "C:/Users/Public/project/UITest2Code/json-files/tokenized-class-" + file_name.split('.')[0] + ".json", "w+")
    write_tests_from_single_website_to_json(json_result_file, filepath, all_tests, all_tests_names)


def write_tests_from_single_website_to_json(json_result_file, path, all_tests, all_tests_names):
    """Writes tests to a json file
    Parameters
        ----------
        json_result_file : TextIO
            json file to write into
        path : str
            the file path of the original tests
        all_tests :  list[list[str]]
            the tests from the original file
        all_tests_names : list[str]
            the tests' names
    """
    json_result_file.write("[")
    for test, test_name in zip(all_tests, all_tests_names):
        instance = {'repo_name': test_name,
                    'ref': "refs/heads/master",
                    'path': path.split("project/")[1],
                    'content': test}
        json_result_file.write(json.dumps(instance))
        json_result_file.write(',\n')

    json_result_file.write("]")


def tokenize_tests():
    """Tokenizes all the tests from all the websites and writes them into json files"""
    list_of_flies = os.listdir('C:/Users/Public/project/UITest2Code/src/test/java/websites')
    for fileName in list_of_flies:
        tokenize_tests_from_single_website(fileName)


if __name__ == "__main__":
    tokenize_tests()
