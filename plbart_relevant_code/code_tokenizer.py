# Copyright (c) 2019-present, Facebook, Inc.
# All rights reserved.
#
# This source code is licensed under the license found in the
# LICENSE file in the root directory of this source tree.
#

import argparse
import logging
import os
import random
import re
import sys
import io
import tokenize
import javalang_tokenizer as javalang_tok

from io import BytesIO
# from sacrebleu import tokenize_v14_international

TOK_NO_SPACE_BEFORE = {',', ';'}

logging.basicConfig(level=logging.INFO)

JAVA_TOKEN2CHAR = {
    'STOKEN0': "//",
    'STOKEN1': "/*",
    'STOKEN2': "*/",
    'STOKEN3': "/**",
    'STOKEN4': "**/",
    'STOKEN5': '"""',
    'STOKEN6': '\\n'
}
JAVA_CHAR2TOKEN = {
    "//": ' STOKEN0 ',
    "/*": ' STOKEN1 ',
    "*/": ' STOKEN2 ',
    "/**": ' STOKEN3 ',
    "**/": ' STOKEN4 ',
    '"""': ' STOKEN5 ',
    '\\n': ' STOKEN6 '
}

class ind_iter(object):
    def __init__(self, len):
        self.i = 0
        self.len = len

    def next(self):
        self.i += 1
        if self.i > (self.len - 1):
            raise StopIteration

    def prev(self):
        self.i -= 1
        if self.i < 0:
            raise StopIteration


def process_string(tok, char2tok, tok2char, is_comment):
    if is_comment:
        tok = re.sub(' +', ' ', tok)
        tok = re.sub(r"(.)\1\1\1\1+", r"\1\1\1\1\1", tok)
        if len(re.sub(r'\W', '', tok)) < 2:
            return ''
    tok = tok.replace(' ', ' ▁ ')
    for char, special_token in char2tok.items():
        tok = tok.replace(char, special_token)
    if tok.startswith(' STOKEN0'):
        if tok.endswith('\n'):
            tok = tok[:-1]
        tok += ' ENDCOM'
    tok = tok.replace('\n', ' STRNEWLINE ')
    tok = tok.replace('\t', ' TABSYMBOL ')
    tok = re.sub(' +', ' ', tok)
    # tok = tokenize_v14_international(tok)
    tok = re.sub(' +', ' ', tok)
    for special_token, char in tok2char.items():
        tok = tok.replace(special_token, char)
    tok = tok.replace('\r', '')

    return tok


def tokenize_java(s, keep_comments=False):
    try:
        tokens = []
        assert isinstance(s, str)
        s = s.replace(r'\r', '')
        tokens_generator = javalang_tok.tokenize(
            s, keep_comments=keep_comments)
        for token in tokens_generator:
            if isinstance(token, javalang_tok.String):
                tokens.append(process_string(
                    token.value, JAVA_CHAR2TOKEN, JAVA_TOKEN2CHAR, False))
            elif isinstance(token, javalang_tok.Comment):
                com = process_string(
                    token.value, JAVA_CHAR2TOKEN, JAVA_TOKEN2CHAR, True)
                if len(com) > 0:
                    tokens.append(com)
            else:
                tokens.append(token.value)
        return tokens
    except:
        return []


def indent_lines(lines):
    prefix = ''
    for i, line in enumerate(lines):
        line = line.strip()
        if re.match('CB_COLON|CB_COMA|CB_', line):
            prefix = prefix[2:]
            line = prefix + line
        elif line.endswith('OB_'):
            line = prefix + line
            prefix += '  '
        else:
            line = prefix + line
        lines[i] = line
    untok_s = '\n'.join(lines)
    return untok_s


def detokenize_java(s):
    assert isinstance(s, str) or isinstance(s, list)
    if isinstance(s, list):
        s = ' '.join(s)
    s = s.replace('ENDCOM', 'NEW_LINE')
    s = s.replace('▁', 'SPACETOKEN')

    s = s.replace('} "', 'CB_ "')
    s = s.replace('" {', '" OB_')
    s = s.replace('*/ ', '*/ NEW_LINE')
    s = s.replace('} ;', 'CB_COLON NEW_LINE')
    s = s.replace('} ,', 'CB_COMA')
    s = s.replace('}', 'CB_ NEW_LINE')
    s = s.replace('{', 'OB_ NEW_LINE')
    s = s.replace(';', '; NEW_LINE')
    lines = re.split('NEW_LINE', s)

    untok_s = indent_lines(lines)
    untok_s = untok_s.replace('CB_COLON', '};').replace(
        'CB_COMA', '},').replace('CB_', '}').replace('OB_', '{')
    untok_s = untok_s.replace('> > >', '>>>').replace('<< <', '<<<')
    untok_s = untok_s.replace('> >', '>>').replace('< <', '<<')

    try:
        # call parser of the tokenizer to find comments and string and detokenize them correctly
        tokens_generator = javalang_tok.tokenize(untok_s, keep_comments=True)
        for token in tokens_generator:
            if isinstance(token, javalang_tok.String) or isinstance(token, javalang_tok.Comment):
                token_ = token.value.replace('STRNEWLINE', '\n').replace('TABSYMBOL', '\t').replace(' ', '').replace(
                    'SPACETOKEN', ' ')
                untok_s = untok_s.replace(token.value, token_)
    except KeyboardInterrupt:
        raise
    except:
        pass
    return untok_s


def extract_functions_java(s):
    tokens = s.split()
    i = ind_iter(len(tokens))
    functions_standalone = []
    functions_class = []
    try:
        token = tokens[i.i]
    except KeyboardInterrupt:
        raise
    except:
        return [], []
    while True:
        try:
            # detect function
            if token == ')' and (tokens[i.i + 1] == '{' or (tokens[i.i + 1] == 'throws' and tokens[i.i + 3] == '{')):
                # go previous until the start of function
                while token not in [';', '}', '{', '*/', 'ENDCOM']:
                    i.prev()
                    token = tokens[i.i]

                if token == '*/':
                    while token != '/*':
                        i.prev()
                        token = tokens[i.i]
                    function = [token]
                    while token != '*/':
                        i.next()
                        token = tokens[i.i]
                        function.append(token)
                elif token == 'ENDCOM':
                    while token != '//':
                        i.prev()
                        token = tokens[i.i]
                    function = [token]
                    while token != 'ENDCOM':
                        i.next()
                        token = tokens[i.i]
                        function.append(token)
                else:
                    i.next()
                    token = tokens[i.i]
                    function = [token]

                while token != '{':
                    i.next()
                    token = tokens[i.i]
                    function.append(token)
                if token == '{':
                    number_indent = 1
                    while not (token == '}' and number_indent == 0):
                        try:
                            i.next()
                            token = tokens[i.i]
                            if token == '{':
                                number_indent += 1
                            elif token == '}':
                                number_indent -= 1
                            function.append(token)
                        except StopIteration:
                            break
                    if 'static' in function[0:function.index('{')]:
                        functions_standalone.append(
                            remove_java_annotation(' '.join(function)))
                    else:
                        functions_class.append(
                            remove_java_annotation(' '.join(function)))
            i.next()
            token = tokens[i.i]
        except KeyboardInterrupt:
            raise
        except:
            break
    return functions_standalone, functions_class


def remove_java_annotation(function):
    return re.sub('^(@ (Override|Deprecated|SuppressWarnings) (\( .* \) )?)*', '', function)


def get_first_token_before_first_parenthesis(s):
    assert isinstance(s, str) or isinstance(s, list)
    if isinstance(s, str):
        s = s.split()
    return s[s.index('(') - 1]


def get_function_name_java(s):
    return get_first_token_before_first_parenthesis(s)


def extract_arguments_java(f):
    return extract_arguments_java_using_parentheses(f)


def extract_arguments_java_using_parentheses(f):
    f = f.split(' ')
    types = []
    names = []
    par = 0
    arguments = []
    f = f[f.index('('):]
    for tok in f:
        if tok == '(':
            par += 1
        elif tok == ')':
            par -= 1
        arguments.append(tok)
        if par == 0:
            break
    arguments = ' '.join(arguments[1:-1])
    if arguments == '':
        return ['None'], ['None']
    arguments = arguments.split(',')
    for arg in arguments:
        bracks = re.findall('\[ \]', arg)
        bracks = ' '.join(bracks)
        arg = arg.replace(bracks, '')
        arg = arg.strip()
        arg = re.sub(' +', ' ', arg)
        t = ' '.join(arg.split(' ')[:-1] + [bracks])
        n = arg.split(' ')[-1]
        types.append(t)
        names.append(n)
    return types, names


if __name__ == '__main__':
    # parser
    parser = argparse.ArgumentParser()
    parser.add_argument('--input_file', default='',
                        help='The file to strip comments from.')
    parser.add_argument('--l', default='python',
                        choices=['python', 'java'], help='language of input code')
    args = parser.parse_args()
    assert args.input_file == '' or os.path.isfile(args.input_file)

    # read from standard input, or from input file
    if args.input_file == '':
        source = sys.stdin.read()
    else:
        with io.open(args.input_file, encoding='utf-8') as f:
            source = f.read()

    tokenize = globals()[f"tokenize_{args.l}"]
    # tokenize
    print(tokenize(source), end='')
