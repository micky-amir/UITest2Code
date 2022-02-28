import json
import os
import re

from code_tokenizer_processor import preprocess
import utils

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))).replace("\\", "/") + '/'
methods_counter = 0
all_methods_dictionary = {}


def tokenize_bigquery_json():
    global all_methods_dictionary
    filepath = project_path + 'bigquery_files/bq-results-20220207-151512-biubj4ywzbd1.json'
    data = []
    with open(filepath, "r+", encoding='utf-8') as file:
        line = file.readline()
        while line:
            data.append(json.loads(line))
            line = file.readline()
    # print(len(data))
    for instance in data:
        # assert not instance['binary']
        single_file_content_methods_dictionary = extract_methods_from_single_class(instance['content'])
        additions_to_values = {'path': instance['sample_path'], 'repo name': instance['sample_repo_name']}
        for value in single_file_content_methods_dictionary.values():
            value.update(additions_to_values)
        all_methods_dictionary.update(single_file_content_methods_dictionary)

    values_from_all_dictionary = all_methods_dictionary.values()
    unique_paths = set(m['path'] for m in values_from_all_dictionary if 'path' in m)
    unique_repos = set(m['repo name'] for m in values_from_all_dictionary if 'repo name' in m)
    # print(f'number of unique files is {len(unique_paths)}\n' + '\n'.join(unique_paths))
    # print(f'number of unique repos is {len(unique_repos)}\n' + '\n'.join(unique_repos))

    write_all_methods_in_dictionary_to_json()


def extract_methods_from_single_class(content):
    global methods_counter
    code_by_lines = iter(content.splitlines())
    in_method = False
    methods_dictionary = {}
    brackets_counter = 0
    comments = []
    for line in code_by_lines:
        if brackets_counter == 0 and is_method_beginning(line):
            in_method = True
            current_method_code_lines = []
            name = line.split("(")[0].split(" ")[-1]
            number_of_words_in_name, name_with_spaces = get_number_of_words(name)
            line = re.sub(r'[\w]+[\s]*\(', 'function(', line)
            url_exists = False
        if in_method:
            current_method_code_lines.append(line + '\n')
            brackets_counter += (line.count("{") - line.count("}"))
            if not url_exists:
                url_exists = bool(
                    re.search(r'\.(get|navigate\(\).to)\([\w\s\+\\/]*(\"www|\"http|url|Url)[\"\w\s\+\\/]*\);', line))
            if brackets_counter == 0:
                # finish reading test
                in_method = False

                if url_exists and number_of_words_in_name >= 3:
                    # filtering methods
                    instance = {'repo_name': name + '_' + str(methods_counter),
                                'name': name_with_spaces, 'comments': list(comments),
                                'url': url_exists, 'content': ' '.join(current_method_code_lines)}
                    methods_dictionary[methods_counter] = instance
                    methods_counter += 1
                comments.clear()
        else:
            comments = update_outside_method_comments(line, comments)
    return methods_dictionary


def write_all_methods_in_dictionary_to_json():
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
        r'^[\s]*((public([\s]+))|(private([\s]+)))?(static([\s]+))?[\w<>?]+[\s]+[\w]+[\s]*\([\w ,<>?]*\)([\s]+(throws)['
        r'\s]+[\w]*)?[\s]*[{]$',
        line)


def get_number_of_words(string):
    if '_' in string:
        # handle snake_case
        string = string.replace('_', ' ')
    elif string != string.lower() and string != string.upper():
        # handle camelCase
        string = re.sub(r'((?<=[a-z])[A-Z]|(?<!\A)[A-Z](?=[a-z]))', r' \1', string)
    return len(string.split()), string


def update_outside_method_comments(line, comments):
    """
    handles the comments variable for each out of method line
    :param line: string that contains the line to process
    :param comments: string that contains the current comments
    :return: the updates comments variable
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
    preprocess.preprocess_v2(project_path + 'bigquery_files/', 'java', True, 110)
    utils.create_single_final_json('bigquery_files/java/tokenized-bigquery.tok',
                                   'bigquery_files/', 'bigquery', all_methods_dictionary)

# https://cloud.google.com/bigquery/docs/quickstarts/quickstart-client-libraries#bigquery_simple_app_client-python
# https://cloud.google.com/bigquery/docs/reference/libraries
# https://dataform.co/blog/exporting-data-bigquery

# go through all the contents. identify when entering a method. if method's name isn't from a list(function[num],
# func[num], a three letters or less name) or have a documentation before - tokenize just like my tests
# uploading the bigquery data to github- gzip or splitting the file?
