from google.cloud import bigquery
import json
import os
import re

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))).replace("\\", "/") + '/'
count_in_method = 0
count_end_method = 0
all_methods_dictionary = {}


def json_to_dictionary():
    global count_in_method
    global count_end_method
    global all_methods_dictionary
    filepath = project_path + 'bigquery_files/bq-results-20220207-151512-biubj4ywzbd1.json'
    # filepath = project_path + 'bigquery_files/test.json'
    data = []
    with open(filepath, "r+", encoding='utf8') as file:
        line = file.readline()
        while line:
            data.append(json.loads(line))
            line = file.readline()
        # print(data)
    for instance in data:
        assert not instance['binary']
        go_through_content(instance['content'])
        # print(str(instance['content']))

    # debugging prints
    print(f'{len(all_methods_dictionary)} entries')
    # for key, value in all_methods_dictionary.items():
    #     print(key, ' : ', value)
    print(f'{count_in_method} methods that began from json')
    print(f'{count_end_method} methods that ended from json')


def go_through_content(content):
    global count_in_method
    global count_end_method
    global all_methods_dictionary
    code_by_lines = iter(content.splitlines())
    in_method = False
    # all_methods_in_single_file_content = []
    brackets_counter = 0
    for line in code_by_lines:
        if brackets_counter == 0 and is_method_beginning(line):
            # print(line)
            in_method = True
            current_method_code_lines = []
            count_in_method += 1
            name = line.split("(")[0].split(" ")[-1]
            # print(name)

        if in_method:
            current_method_code_lines.append(line)
            brackets_counter += (line.count("{") - line.count("}"))
            if brackets_counter == 0:
                # finish reading test
                in_method = False
                count_end_method += 1

                # debugging here
                # if not count_in_method == count_end_method:
                #     print(current_method_code_lines)

                instance = {'repo_name': name, 'content': current_method_code_lines}
                all_methods_dictionary[count_end_method] = instance
                # all_methods_dictionary[count_end_method] = current_method_code_lines
                # all_methods_in_single_file_content.append(current_method_code_lines)
    # all_methods_dictionary.extend(all_methods_in_single_file_content)


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


if __name__ == "__main__":
    json_to_dictionary()

# https://cloud.google.com/bigquery/docs/quickstarts/quickstart-client-libraries#bigquery_simple_app_client-python
# https://cloud.google.com/bigquery/docs/reference/libraries
# https://dataform.co/blog/exporting-data-bigquery

# go through all the contents. identify when entering a method. if method's name isn't from a list(function[num],
# func[num], a three letters or less name) or have a documentation before - tokenize just like my tests
