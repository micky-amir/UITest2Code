# Copyright (c) 2019-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

from concurrent.futures import ProcessPoolExecutor
from plbart_relevant_code.dataset import Dataset


def preprocess_v2(root, lang1, keep_comments, test_size=1000):
    dataset = Dataset(root, lang1, keep_comments, test_size=test_size)

    mp_executor = ProcessPoolExecutor()

    cluster_ex1 = None
    cluster_ex2 = None
    dataset.process_languages(
        lang_executor=mp_executor, tok_executor=cluster_ex1, split_executor=cluster_ex2)
