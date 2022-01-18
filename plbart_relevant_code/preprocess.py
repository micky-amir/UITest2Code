# Copyright (c) 2019-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

import argparse
import os
import subprocess

from concurrent.futures import ProcessPoolExecutor
from dataset import Dataset
from utils import bool_flag


def preprocess_v2(root, lang1, lang2, keep_comments, lang3=None, test_size=1000):
    dataset = Dataset(root, lang1, lang2, keep_comments, test_size=test_size, lang3=lang3)

    mp_executor = ProcessPoolExecutor()

    cluster_ex1 = None
    cluster_ex2 = None
    dataset.process_languages(
        lang_executor=mp_executor, tok_executor=cluster_ex1, split_executor=cluster_ex2)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='')
    parser.add_argument('root', help='root folder')
    parser.add_argument('--lang1', help='language 1')
    parser.add_argument('--lang2', help='language 2')
    parser.add_argument('--lang3', default=None, help='language 3')
    parser.add_argument('--test_size', type=int,
                        default=1000, help='size of test set')
    parser.add_argument('--keep_comments', type=bool_flag, default=False,
                        help='used bpe trained on data with comments or not')

    args = parser.parse_args()

    preprocess_v2(
        args.root, args.lang1, args.lang2,
        args.keep_comments,
        lang3=args.lang3, test_size=args.test_size
    )
