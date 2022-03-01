# MIT License
#
# Copyright (c) 2021 Wasi Ahmad
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:

from concurrent.futures import ProcessPoolExecutor
from code_tokenizer_processor.dataset import Dataset


def preprocess_v2(root, lang1, keep_comments, test_size=1000):
    dataset = Dataset(root, lang1, keep_comments, test_size=test_size)

    mp_executor = ProcessPoolExecutor()

    cluster_ex1 = None
    cluster_ex2 = None
    dataset.process_languages(
        lang_executor=mp_executor, tok_executor=cluster_ex1, split_executor=cluster_ex2)
