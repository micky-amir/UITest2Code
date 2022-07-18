import math
import os

import pandas


def read_single_excel(project_path, website_name):
    """
    Reads a single test cases excel of a single website and formats it into a dictionary
    :param project_path: the path to the UITest2Code directory
    :param website_name: string that contains the current website name
    :return: a dictionary that contains all the tests cases of a single website
    """

    # creates a data frame
    df = pandas.read_excel(
        os.path.join(project_path, 'src', 'test', 'resources', 'test_descriptions', website_name + '_TestCases.xlsx'))
    df = df.rename(columns={'Expected Results': 'Steps'})

    # initializes variables
    ignore_columns = ['Type', 'Comments']
    testcases_dictionary = {}
    test_name = ''
    test_case_data = {}

    # irritates over the data frame rows
    for i, j in df.iterrows():
        for value in j.values:
            # if the value is a part of a test case
            if ((not isinstance(value, float)) or not (math.isnan(value))) and (not str(value).startswith('Section')):
                current_column_title = j.keys()[list(j.values).index(value)]
                if current_column_title == 'Test Case ID':
                    # the value will become a key in the new dictionary
                    if len(test_name) != 0:
                        testcases_dictionary[test_name] = test_case_data
                    test_name = value
                    test_case_data = {}
                elif current_column_title not in ignore_columns:
                    # the value will be inserted as a value into the current test case dictionary
                    if current_column_title in test_case_data.keys():
                        test_case_data[current_column_title].append(value)
                    else:
                        test_case_data[current_column_title] = [value]
        if len(test_case_data) != 0:
            # a test case will be inserted into the final test cases dictionary
            testcases_dictionary[test_name] = test_case_data

    return testcases_dictionary


def simplify_single_test(test):
    """
    Formats a single test case dictionary (from a dictionary of dictionaries to a dictionary of strings)
    :param test: single test case dictionary
    :return: a formatted test case dictionary
    """
    simplified_test = {}
    for key, value in test.items():
        if key == 'Preconditions' or key == 'Steps':
            simplified_test[key] = value
        else:
            simplified_test[key] = ', '.join(value)
    return simplified_test
