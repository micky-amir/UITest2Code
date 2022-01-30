import math
import pandas


def read_single_excel(project_path, website_name):
    df = pandas.read_excel(
        project_path + r'src/test/resources/test_descriptions/' + website_name + '_TestCases.xlsx')
    df = df.rename(columns={'Expected Results': 'Steps'})
    ignore_columns = ['Type', 'Comments']
    testcases_dictionary = {}
    test_name = ''
    test_case_data = {}
    # iterating over rows using iterrows() function
    for i, j in df.iterrows():
        # i is row
        for value in j.values:
            # j[0] is first column (not including title), j[1] is second column etc.
            if ((not isinstance(value, float)) or not (math.isnan(value))) and (not str(value).startswith('Section')):
                current_column_title = j.keys()[list(j.values).index(value)]
                if current_column_title == 'Test Case ID':
                    if len(test_name) != 0:
                        testcases_dictionary[test_name] = test_case_data
                    test_name = value
                    test_case_data = {}
                elif current_column_title not in ignore_columns:
                    if current_column_title in test_case_data.keys():
                        test_case_data[current_column_title].append(value)
                    else:
                        test_case_data[current_column_title] = [value]
        if len(test_case_data) != 0:
            testcases_dictionary[test_name] = test_case_data

    return testcases_dictionary


# def add_excel_data_to_tok(website_name, testcases_dictionary, project_path):
#     # open the tok file, and according to the id adds the testcases dictionary values
#     path = project_path + 'gzip_files/java/tokenized-class-' + website_name + '.tok'
#     with open(path, "r", encoding="utf-8") as file:
#         new_lines = []
#         line = file.readline()
#         while line:
#             # test_code = json.loads(line)
#             print(f'line: {line}')
#             test_name = line.split('DOCUMENT_ID="')[1].split('/')[0].rsplit('_', 1)[0]
#             current_test = testcases_dictionary[test_name]
#             # find a way to split the content ↓
#             split_line = re.split(r'<DOCUMENT_ID[A-z.-9]*>', line)
#             new_line = split_line[1] + ' '.join(current_test) + split_line[2]
#             # test_code['test case'] = testcases_dictionary[test_name]
#             # test_code['Test Case'] = ' '.join([
#             #     # [current_test['Test Name'], current_test['Test Description'],
#             #     ' '.join(current_test['Preconditions']), ' '.join(current_test['Steps'])])
#             # new_lines.append(line + ' '.join(testcases_dictionary[test_name]))
#
#             new_lines.append(current_test)
#             line = file.readline()
#     print(' '.join(new_lines))
#     with open(path, "w", encoding="utf-8") as file:
#         file.writelines(new_lines)


# def add_excel_data_to_json(website_name, testcases_dictionary, project_path):
#     # open the json file, and according to the id adds the testcases dictionary values
#     path = project_path + 'json_files/tokenized-class-' + website_name + '.json'
#     with open(path, "r", encoding="utf-8") as file:
#         new_lines = []
#         line = file.readline()
#         while line:
#             test_code = json.loads(line)
#             test_name = test_code['repo_name'].rsplit('_', 1)[0]
#             current_test = testcases_dictionary[test_name]
#             # test_code['test case'] = testcases_dictionary[test_name]
#             test_code['Test Case'] = ' '.join([
#                 # [current_test['Test Name'], current_test['Test Description'],
#                 ' '.join(current_test['Preconditions']), ' '.join(current_test['Steps'])])
#             # new_lines.append(line + ' '.join(testcases_dictionary[test_name]))
#
#             # check how it is done in amir's code (join maybe?) ↓
#             new_lines.append(json.dumps(test_code))
#             line = file.readline()
#     with open(path, "w", encoding="utf-8") as file:
#         file.writelines('\n'.join(new_lines))


def simplify_single_test(test):
    simplified_test = {}
    for key, value in test.items():
        simplified_test[key] = ' '.join(value)
    return simplified_test

# def handle_single_website(website_name, project_path):
#     testcases_dictionary_result = read_single_excel(project_path, website_name)
#     add_excel_data_to_tok(website_name, testcases_dictionary_result, project_path)

