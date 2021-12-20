def tokenize_tests():
    filename = "C:/Users/Public/project/UITest2Code/src/test/java/websites/RottenTomatoes.java"
    json_result_file = open("C:/Users/Public/project/UITest2Code/python/tokenized-class.json", "w+")
    with open(filename, "r", encoding="utf-8") as file:
        # file = open("C:/Users/Public/project/UITest2Code/src/test/java/websites/test.java")
        line = file.readline()
        in_test = False
        brackets_counter = 0
        while line:
            if "@Test" in line:
                in_test = True
            elif in_test:
                print(line)
                json_result_file.write(line)
                brackets_counter += line.count("{") - line.count("}")
                if brackets_counter == 0:
                    in_test = False
                    json_result_file.write("\n")
            line = file.readline()


if __name__ == "__main__":
    tokenize_tests()
