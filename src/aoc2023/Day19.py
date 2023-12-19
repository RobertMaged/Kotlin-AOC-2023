import re
from os import getenv


def read_input(day):
    with open(f"{getenv('AOC_DIR')}/resources/{day}.txt") as f:
        return f.readlines()


import more_itertools

test_input = tuple(read_input("Day19_test"))
input = tuple(read_input("Day19"))


def part1(input: tuple[str]) -> int:
    (workflows_lines, ratingsLines) = more_itertools.split_at(input, lambda line: line == '\n')

    workflows = {}

    for flow in workflows_lines:
        rule_start_index = flow.find('{') +1
        name = flow[0:rule_start_index-1]
        rules = flow[rule_start_index: -2].split(',')
        workflows[name] = rules


    sum_acc = 0
    for rating in map(lambda line: re.findall(r'\d+', line), ratingsLines):

        if rec(workflows, rating, 'in'):
            sum_acc += sum(map(int, rating))

    return sum_acc


the_map = {'x': 0, 'm': 1, 'a': 2, 's': 3}


def rec(workflows: dict[str, list[str]], rating: list[str], name: str) -> bool:
    if name == 'A':
        return True
    elif name == 'R':
        return False

    flow = workflows[name]
    for rule in flow:
        if '<' not in rule and '>' not in rule:
            next_name = rule
        else:
            left = int(rating[the_map[rule[0]]])
            op = rule[1]

            index_of_next = rule.find(':') + 1
            rigth = int(rule[2:index_of_next-1])

            next_name = rule[index_of_next:]

            if op == '<' and left >= rigth:
                continue
            elif op == '>' and left <= rigth:
                continue

        return rec(workflows, rating, next_name)
def rec(workflows: dict[str, list[str]], rating: list[str], name: str) -> bool:
    if name == 'A':
        return True
    elif name == 'R':
        return False

    flow = workflows[name]
    for rule in flow:
        if '<' not in rule and '>' not in rule:
            next_name = rule
        else:
            left = int(rating[the_map[rule[0]]])
            op = rule[1]

            index_of_next = rule.find(':') + 1
            rigth = int(rule[2:index_of_next-1])

            next_name = rule[index_of_next:]

            if op == '<' and left >= rigth:
                continue
            elif op == '>' and left <= rigth:
                continue

        return rec(workflows, rating, next_name)


def part2(input: tuple[str]) -> int:
    pass


if __name__ == "__main__":
    test_res_p1 = part1(test_input)
    if test_res_p1 != 19114:
        raise AssertionError(test_res_p1)

    print(part1(input))

    test_res_p2 = part2(test_input)
    if test_res_p2 != 167409079868000:
        raise AssertionError(test_res_p2)

    print(part2(input))
