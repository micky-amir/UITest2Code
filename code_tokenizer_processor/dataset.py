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

import itertools
from pathlib import Path

from code_tokenizer_processor.utils import LocalExecutor, process_and_tokenize_json_file


class Language:

    def __init__(self, root, lang):
        self.folder = Path(str(root)).joinpath(lang)
        assert self.folder.is_dir(
        ), f"failed to initalize Language {self.l}, there is no directory {str(self.folder)}"
        self.l = lang

    def process_json_and_tok(self, keep_comments, executor=None):
        if executor is None:
            executor = LocalExecutor()
        suffix = '.with_comments' if keep_comments else ''
        assert len(list(self.folder.glob('*.json.gz'))
                   ) > 0, f"there is no json in {str(self.folder)}"
        jsons = [json for json in self.folder.glob(
            '*.json.gz') if not Path(str(json).replace('.json.gz', suffix + '.tok')).is_file()]
        print(f"{self.l}: tokenizing {len(jsons)} json files ...")
        if len(jsons) > 0:
            jobs = executor.map_array(process_and_tokenize_json_file, jsons, itertools.repeat(
                self.l), itertools.repeat(keep_comments))
            for job in jobs:
                job.result()
        else:
            return

    def process(self, keep_comments, tok_executor=None, test_size=1000, split_executor=None):
        suffix = '.with_comments' if keep_comments else ''
        print(f"{self.l}: process ...")
        self.process_json_and_tok(keep_comments, tok_executor)


class Dataset:

    def __init__(self, root, lang1, lang2=None, keep_comments=False, test_size=1000, lang3=None):
        self.test_size = test_size
        self.root = Path(root)
        assert self.root.is_dir(
        ), f"failed to build the dataset, there is no directory {str(root)}"

        langs = [lang1]

        # if lang2 is not None:
        #     langs.append(lang2)
        # if lang3 is not None:
        #     langs.append(lang3)

        langs = sorted(langs)
        self.langs = []

        self.langs.append(Language(root, langs[0]))
        if len(langs) >= 2:
            self.langs.append(Language(root, langs[1]))
        if len(langs) == 3:
            self.langs.append(Language(root, langs[2]))

        self.keep_comments = keep_comments
        self.suffix = ".with_comments" if keep_comments else ''
        prefix = '-'.join(langs)
        self.folder = self.root.joinpath(f"{prefix}{self.suffix}")
        self.codes = self.folder.joinpath("codes")
        self.vocab = self.folder.joinpath("vocab")
        self.sizes = {l.l: [] for l in self.langs}
        if not self.folder.is_dir():
            self.folder.mkdir()

    def process_languages(self, lang_executor=None, tok_executor=None, split_executor=None):
        jobs = [lang_executor.submit(lang.process, self.keep_comments, tok_executor, self.test_size, split_executor)
                for lang in self.langs]
        for i, lang in enumerate(self.langs):
            self.sizes[lang.l] = jobs[i].result()
