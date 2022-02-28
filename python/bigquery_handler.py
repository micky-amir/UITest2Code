import json
import os
import re
from code_tokenizer_processor import preprocess
import utils

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))).replace("\\", "/") + '/'
"""the path to the UITest2Code directory"""

methods_counter = 0
"""identifier for the tokenized tests"""

all_methods_dictionary = {}
"""a dictionary that contains the info of all the filtered methods"""


def tokenize_bigquery_json():
    """Tokenizes functions from the bigquery json file and writes them into a new json file"""
    global all_methods_dictionary
    filepath = project_path + 'bigquery_files/bq-results-20220207-151512-biubj4ywzbd1.json'
    data = []
    with open(filepath, "r+", encoding='utf-8') as file:
        line = file.readline()
        while line:
            data.append(json.loads(line))
            line = file.readline()
    for instance in data:
        # for each file in the bigquery data - extracts methods and adds them to a dictionary
        single_file_content_methods_dictionary = extract_methods_from_single_class(instance['content'])
        additions_to_values = {'path': instance['sample_path'], 'repo name': instance['sample_repo_name']}
        for value in single_file_content_methods_dictionary.values():
            value.update(additions_to_values)
        all_methods_dictionary.update(single_file_content_methods_dictionary)

    # writes all the methods to a json file
    write_all_methods_in_dictionary_to_json()


def extract_methods_from_single_class(content):
    """
    Extracts and filters methods from the content into a dictionary

    :param content: a string that contains the code of a single file
    :return: dictionary that contains the filtered methods and details about them
    """
    global methods_counter
    code_by_lines = iter(content.splitlines())
    in_method = False
    methods_dictionary = {}
    brackets_counter = 0
    comments = []
    for line in code_by_lines:
        if brackets_counter == 0 and is_method_beginning(line):
            # starts reading test
            in_method = True
            current_method_code_lines = []
            url_exists = False
            # handles the name of the method
            name = line.split("(")[0].split(" ")[-1]
            number_of_words_in_name, name_with_spaces = get_number_of_words(name)
            line = re.sub(r'[\w]+[\s]*\(', 'function(', line)
        if in_method:
            current_method_code_lines.append(line + '\n')
            brackets_counter += (line.count("{") - line.count("}"))
            if not url_exists:
                # checks if url exists
                url_exists = bool(
                    re.search(r'\.(get|navigate\(\).to)\([\w\s+\\/]*(\"www|\"http|url|Url)[\"\w\s+\\/]*\);', line))
            if brackets_counter == 0:
                # finishing reading test
                in_method = False
                if url_exists and number_of_words_in_name >= 3:
                    # filtering methods
                    instance = {'repo_name': name + '_' + str(methods_counter),
                                'method name': name_with_spaces, 'comments': list(comments),
                                'url': url_exists, 'content': ' '.join(current_method_code_lines)}
                    methods_dictionary[methods_counter] = instance
                    methods_counter += 1
                comments.clear()
        else:
            # the line isn't inside a method
            comments = update_outside_method_comments(line, comments)
    return methods_dictionary


def write_all_methods_in_dictionary_to_json():
    """Writes all the methods from the global dictionary to a json file"""
    json_file_path = project_path + '/bigquery_files/tokenized-bigquery.json'
    with open(json_file_path, "w+", encoding='utf-8') as json_result_file:
        for method_info in all_methods_dictionary.values():
            json_result_file.write(json.dumps(method_info, ensure_ascii=False))
            json_result_file.write('\n')


def is_method_beginning(line):
    """
    Checks if a line is the beginning of a method

    :param line: string that contains a line of code
    :return: boolean that indicates weather the line is a beginning of a method
    """
    return re.match(
        r'^[\s]*((public([\s]+))|(private([\s]+)))?(static([\s]+))?[\w<>\[\]?]+[\s]+[\w]+[\s]*\([\w \[\],'
        r'<>?]*\)([\s]+(throws)[\s]+[\w]*)?[\s]*[{]$',
        line)


def get_number_of_words(string):
    """
    Analyzes a string using snake_case or camelCase

    :param string: a string to analyze
    :return: number of words in the string and the string in a space delimited format
    """
    if '_' in string:
        # handle snake_case
        string = string.replace('_', ' ')
    elif string != string.lower() and string != string.upper():
        # handle camelCase
        string = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', string)
    return len(string.split()), string


def update_outside_method_comments(line, comments):
    """
    Handles the comments variable for each out of method line

    :param line: string that contains the line to process
    :param comments: string that contains the current comments
    :return: the updated comments variable
    """
    result = re.split(r'^[\s]*(//|@|/\*\*|/\*|\*/|\*)', line)
    if len(result) > 1 and len(result[-1]):
        # get the descriptions without the comment chars
        comments.append(result[-1])
    elif re.match(r'^[\s]*$', line):
        # if the line is empty, clears the variable
        comments.clear()
    return comments


if __name__ == "__main__":
    utils.gzip_to_json(project_path + 'bigquery_files/bq-results-20220207-151512-biubj4ywzbd1.json.gz')
    tokenize_bigquery_json()
    utils.json_to_gzip('bigquery_files/tokenized-bigquery.json', 'bigquery_files/java/')
    preprocess.preprocess_v2(project_path + 'bigquery_files/', 'java', False, 2000)
    utils.create_single_final_json('bigquery_files/java/tokenized-bigquery.tok', 'bigquery_files/', 'bigquery',
                                   all_methods_dictionary)
