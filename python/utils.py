import os
import shutil
import gzip
import json
from bs4 import BeautifulSoup
from python import excels_reader

project_path = os.path.dirname(os.path.dirname(os.path.abspath(__file__))).replace("\\", "/") + '/'
"""the path to the UITest2Code directory"""


def create_directories(is_bigquery=False):
    """
    Creates the json_files/ and gzip_files/java/ directories

    :param is_bigquery: optional boolean that indicates whether the required directories are for bigquery data
    """
    try:
        new_directories = ['bigquery_files/java/'] if is_bigquery else ['json_files/', 'gzip_files/',
                                                                        'gzip_files/java/']
        for new_dir in new_directories:
            new_dir_path = project_path + new_dir
            if not os.path.isdir(new_dir_path):
                os.mkdir(new_dir_path)
    except OSError as error:
        print(error)


def delete_unnecessary_dirs_and_files(dir_name_to_delete_entirely, selective_dir_name, original_file=''):
    """
    Deletes the draft files and directories

    :param dir_name_to_delete_entirely: string with the path in the project to a directory that will be deleted entirely
    :param selective_dir_name: string with the path in the project to the directory to remove drafts from
    :param original_file: optional string with the name of the original file that was tokenized, who won't be deleted
    """
    try:
        shutil.rmtree(project_path + dir_name_to_delete_entirely)
        delete_not_finished_files_from_dir_except_original(selective_dir_name, original_file)
    except OSError as e:
        print("Error: %s - %s." % (e.filename, e.strerror))


def delete_not_finished_files_from_dir_except_original(dir_name, original_file_name=''):
    """
    Removes draft files from a directory

    :param dir_name: a string with the path in the project to the directory to remove drafts from
    :param original_file_name: a string with the name of the original file that was tokenized, who won't be deleted
    """
    list_of_flies = os.listdir(project_path + dir_name)
    for file_name in list_of_flies:
        if (not file_name.startswith("finished")) and (file_name != original_file_name):
            os.remove(project_path + dir_name + file_name)


def gzip_to_json(gzip_file_path):
    """
    Unzip a gzip file into a json file

    :param gzip_file_path: original gzip file path string
    """
    with gzip.open(gzip_file_path, 'rb') as file_in, open(gzip_file_path.rsplit('.', 1)[0], 'wb') as file_out:
        contents = file_in.read()
        file_out.write(contents)


def json_to_gzip(json_file_path, gzip_dir):
    """
    Formats a json file to a gzip file

    :param gzip_dir: the directory in the project in which the gzip file should be created
    :param json_file_path: the json file's path
    """
    with open(project_path + json_file_path, 'rb') as json_file, gzip.open(
            project_path + gzip_dir + os.path.basename(json_file.name) + '.gz', 'wb') as gz_file:
        gz_file.writelines(json_file)


def create_single_final_json(tok_file_path, json_dir_name, website_name, tests_dictionary, id_counter=0):
    """
    Creates the final tokenized json file for a single website

    :param tok_file_path:  string that contains the tok file path
    :param json_dir_name: string that contains the directory name for the finalized json
    :param website_name: string that contains the current website name
    :param tests_dictionary: a dictionary that contains the tests from the website
    :param id_counter: counter that is responsible for the id of each tokenized method
    """
    with open(project_path + tok_file_path, 'r', encoding="utf-8") as tok_file, open(
            project_path + json_dir_name + 'finished-tokenized-class-' + website_name + '.json', 'w',
            encoding="utf-8") as json_file:
        line = tok_file.readline()
        while line:
            # gets original test id
            content = BeautifulSoup(line, features="html.parser")
            tag_name = content.findAll()[0].name
            test_id = tag_name.split('document_id="')[1]

            # gets the current test from the dictionary based on its id and whether the method is from bigquery
            if website_name == 'bigquery':
                id_counter = int(test_id.rsplit('_', 1)[1])
                current_test = tests_dictionary[id_counter]
                current_test_simplified = get_some_fields_from_dictionary(current_test, ['method name', 'comments'])
            else:
                test_id = test_id.rsplit('_', 1)[0].upper()
                current_test = tests_dictionary[test_id]
                current_test_simplified = excels_reader.simplify_single_test(current_test)

            # creates a dictionary instance, and writes it to the new json file
            instance = {'id': id_counter,
                        'website': website_name,
                        'code': content.find(text=True)}
            instance.update(current_test_simplified)
            json_file.write(json.dumps(instance, ensure_ascii=False))
            json_file.write('\n')

            # updates variables
            if website_name != 'bigquery':
                id_counter += 1
            line = tok_file.readline()

    return id_counter


def get_some_fields_from_dictionary(dictionary, list_of_keys):
    """
    extracts required fields (key+value) from dictionary

    :param dictionary: a dictionary that contains several keys
    :param list_of_keys: a list of required keys from the dictionary
    :return: a new dictionary, with only the required fields
    """
    new_dict = {key: dictionary[key] for key in dictionary.keys() & list_of_keys}
    return new_dict
