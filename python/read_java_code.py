import re
import json
# from code_tokenizer import tokenize_java
# from javalang_tokenizer import tokenize
# import pandas as pd

def read_excels():
    file_name = "/Users/amirbenami/UniProjects/BI university/thesis/UITest2Code/src/test/resources/test_descriptions/Amazon_TestCases.xls"
    xl_file = pd.ExcelFile(file_name)

    dfs = {sheet_name: xl_file.parse(sheet_name)
           for sheet_name in xl_file.sheet_names}
    test_id = None
    current_test = {}
    current_test['steps'] = []
    current_test['preconditions'] = []
    all_tests = []

    for idx, row in list(dfs.values())[0].iterrows():
        if str(row['Test Case ID']).startswith('SK'):
            test_id = row['Test Case ID']
            if 'test_id' in current_test:
                all_tests.append(current_test)
                current_test = {}
                current_test['steps'] = []
                current_test['preconditions'] = []


        if test_id is not None:
            current_test['test_id'] = test_id
            if str(row['Test Name']) != 'nan':
                test_title = row['Test Name']
                current_test['title'] = test_title
            if str(row['Test Description'])!= 'nan':
                test_description = row['Test Description']
                current_test['description'] = test_description


            if str(row['Steps']) != 'nan':
                step_and_res = row['Steps']
                if str(row['Expected Results'])!= 'nan':
                    step_and_res = step_and_res + ' ' + row['Expected Results']
                current_test['steps'].append(step_and_res)
            if str(row['Preconditions']) != 'nan':
                current_test['preconditions'].append(row['Preconditions'])
    all_tests.append(current_test)
    with open('test_ui_fixed.json', 'rb') as f_read:
        with open('uitest.json', 'w+') as f_write:
            line = f_read.readline()
            while line:
                test_code = json.loads(line)
                test_id = test_code['nl']
                find_test = [test for test in all_tests if test['test_id'] == test_id]
                if len(find_test) > 0:
                    relevant_test = find_test[0]
                    test_code['nl'] = '. '.join([relevant_test['title'],
                                                 relevant_test['description'],
                                                 '' if len(relevant_test) == 0 else ' '.join(relevant_test['preconditions']),
                                                 ' '.join(relevant_test['steps'])])

                    f_write.write(json.dumps(test_code, ensure_ascii=False))
                    f_write.write('\n')
                line = f_read.readline()



def replace_method_names():
    new_lines = []
    all_test_jsons = []
    with open('test_ui.tok') as f:
        line = f.readline()
        while line:
            func_num = re.findall("SK_[0-9]*", line)[0]
            line = line.replace("_Tamar (", " (")
            all_func_name = re.findall("public void SK_[0-9]*", line)[0]
            # test1 = line.find("public void SK_[0-9].*")
            line = line.replace(all_func_name, "void function")
            index_start = line.index("void function ( )")

            test_json = {}
            test_json['nl'] = func_num
            test_json['code'] = line[index_start:]
            new_lines.append(line)
            all_test_jsons.append(test_json)
            line = f.readline()
    with open('test_ui_fixed.json', 'w+') as f:
        # for line in new_lines:
            # f.write(line)
        for test_json in all_test_jsons:
            f.write(json.dumps(test_json, ensure_ascii=False))
            f.write('\n')


def tokenize_tests():
    with open('C:/Users/Public/project/UITest2Code/src/test/java/websites/Amazon.java') as f:
        line = f.readline()
        reading_a_test = False
        all_tests = []
        test_names = []
        while line:
            if "@Test" in line:
                reading_a_test = True
                current_test = []
                total_opening_curly_brackets = 0
                line = f.readline()
                if line:
                    test_name = line.split("void")[1].split()[0][:-2]
                    test_names.append(test_name)
                continue


            if reading_a_test:
                current_test.append(line)
                opening_curly_brackets = line.count('{')
                closing_curly_brackets = line.count('}')
                total_opening_curly_brackets = total_opening_curly_brackets + opening_curly_brackets - closing_curly_brackets
                if closing_curly_brackets > 0 and total_opening_curly_brackets == 0:
                    # done reading method
                    reading_a_test = False
                    all_tests.append(current_test)
                line.count('}')
                # print(line)
            line = f.readline()
    # _tokens = tokenize_java('\n'.join(current_test))
    # tokenized_code = ' '.join(_tokens)
    # tokenized_code = re.sub("[\n\r\t ]+", " ", tokenized_code).strip()

    # b = tokenize(' '.join(current_test))
    # a = ""
    instances = []
    for test_n, test_list in zip(test_names, all_tests):
        instance = {}
        instance['repo_name'] = test_n
        instance['ref'] = "refs/heads/master"
        instance['path'] = "/UITest2Code/src/test/java/websites/Amazon.java"
        instance['content'] = ' '.join(test_list)
        # print(json.dumps(instance))
        instances.append(instance)
    with open('test_ui.json', 'w+') as f:
        for doc in instances:
            f.write(json.dumps(doc))
            f.write('\n')
        # f.writelines([json.dumps(doc) for doc in instances])
        # f.write(json.dumps(instances[1]))


if __name__ == '__main__':
    tokenize_tests()
    # replace_method_names()
    # read_excels()

