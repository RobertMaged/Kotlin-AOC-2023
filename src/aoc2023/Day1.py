
def read_input(day): 
    with open(f"src/aoc2023/resources/{day}.txt") as f:
        return f.readlines()
    
input = tuple(read_input("Day1"))
test_input = tuple(read_input("Day1_test"))

def ext_num(line):
    firstN = next(filter(lambda c: c.isdigit(), line))
    lastN = next(filter(lambda c: c.isdigit(), reversed(line)))
    return int(f"{firstN}{lastN}")


def part1(input: tuple[str]):
    
    return sum([ext_num(line) for line in input])

def part2(input: tuple[str]):
    sum = 0
    
    numbers = {
        "one": "on1e",
        "two": "tw2o",
        "three": "thre3e",
        "four": "fou4r",
        "five": "fiv5e",
        "six": "si6x",
        "seven": "seve7n",
        "eight": "eigh8t",
        "nine": "nin9e",
    }
    
    
    for line in input:
        for plain_num, hacky_num in numbers.items():
            line = line.replace(plain_num, hacky_num)
        
        sum += ext_num(line)
        
    return sum
            

print(part1(test_input[:4]))
print(part1(input))

print(part2(test_input[4:]))
print(part2(input))