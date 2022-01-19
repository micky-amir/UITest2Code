# Copyright (c) 2019-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

# import argparse
# import subprocess
import typing as tp
import tqdm
import json
import fileinput
import os

from pathlib import Path
from multiprocessing import Pool, cpu_count
import plbart_relevant_code.code_tokenizer as code_tokenizer

# from data.github.preprocessing.src.timeout import timeout, TimeoutError

FAST = str(Path(__file__).parents[2].joinpath("XLM/tools/fastBPE/fast"))
XLM_PP = str(Path(__file__).parents[2].joinpath("XLM/preprocess.py"))

FALSY_STRINGS = {'off', 'false', '0'}
TRUTHY_STRINGS = {'on', 'true', '1'}


def tokenize_json_helper(inpt):
    tokenizer, content, path, keep_comments = inpt
    content_tokenized = tokenizer(content, keep_comments)
    return content_tokenized, path


# @timeout(3600)
def output_all_tokenized_results(docs, f_tok):
    with Pool(cpu_count()) as pool, tqdm.tqdm(total=len(docs)) as pbar:
        for i, (content_tokenized, path) in enumerate(
                pool.imap_unordered(tokenize_json_helper, docs)
        ):
            pbar.update()
            if len(content_tokenized) == 0:
                continue
            else:
                content_tokenized = ' '.join(content_tokenized)
                s = f"<DOCUMENT_ID=\"{path}\"> {content_tokenized} </DOCUMENT>"
                # for some reason sometimes, some caracters of s
                # cannot be encoded into utf-8 and it failed to print, so use try/catch
                try:
                    f_tok.write(s)
                    f_tok.write('\n')
                except:
                    continue


def process_and_tokenize_json_file(input_path, language, keep_comments):
    suffix = '.with_comments' if keep_comments else ''
    output_path = str(input_path).replace('.json.gz', suffix + '.tok')
    # print('input: ' + str(input_path) + ', output: ' + str(output_path))
    tokenizer = getattr(code_tokenizer, f"tokenize_{language}")
    docs = []
    paths = []
    for line in fileinput.input(str(input_path), openhook=fileinput.hook_compressed):
        x = json.loads(line)
        # print(x)
        if 'content' not in x:
            continue
        content = x['content']
        path = f"{x['repo_name']}/tree/master/{x['path']}"
        docs.append((tokenizer, content, path, keep_comments))

    f_tok = open(output_path, 'w', encoding='utf-8')
    try:
        output_all_tokenized_results(docs, f_tok)
    except TimeoutError:
        # The tokenization process is sometimes killed and it makes the multiprocessing hang forever
        f_tok.close()
        print('Program closed automatically after one hour')
        os._exit(0)


class DelayedJob:
    """Future-like object which delays computation
    """

    def __init__(self, func: tp.Callable[..., tp.Any], *args: tp.Any, **kwargs: tp.Any) -> None:
        self.func = func
        self.args = args
        self.kwargs = kwargs
        self._result: tp.Optional[tp.Any] = None
        self._computed = False

    def done(self) -> bool:
        return True

    def result(self) -> tp.Any:
        if not self._computed:
            self._result = self.func(*self.args, **self.kwargs)
            self._computed = True
        return self._result


class LocalExecutor:
    """Executor which run sequentially and locally
    (just calls the function and returns a FinishedJob)
    """

    def submit(self, fn: tp.Callable[..., tp.Any], *args: tp.Any, **kwargs: tp.Any) -> DelayedJob:
        return DelayedJob(fn, *args, **kwargs)

    def map_array(self, fn, *arg_iterators):
        grouped_args = zip(*arg_iterators)
        return [self.submit(fn, *g) for g in grouped_args]
