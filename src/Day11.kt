private typealias MonkeyId = Int

private data class Monkey(
    val id: Int,
    val holdingItems: ArrayDeque<Long>,
    val operation: Pair<Char, Int>,
    val divisibleBy: Int,
    val decision: Pair<Int, Int>,
    var inspectedItems: Long = 0
)

private fun List<String>.parseMonkeys(): Map<MonkeyId, Monkey> = filter { it.isNotBlank() }.chunked(6).map { monkeyNotes ->

    val id = monkeyNotes.first().split(" ")[1].takeWhile { it.isDigit() }.toInt()
    val startingItems = monkeyNotes[1].substringAfter("Starting items: ").chunked(2).filter { it.toIntOrNull() != null }
        .map { it.toLong() }
    val operation = monkeyNotes[2].substringAfter("Operation: new = ").split(" ")
    val dividableBy = monkeyNotes[3].substringAfter("Test: divisible by ").toInt()
    val receiverMonkeyIdWhenTrue = monkeyNotes[4].substringAfter(" monkey ").toInt()
    val receiverMonkeyIdWhenFalse = monkeyNotes[5].substringAfter(" monkey ").toInt()

    return@map Monkey(
        id,
        ArrayDeque(startingItems),
        operation[1].first() to if (operation[2].toIntOrNull() != null) operation[2].toInt() else -1,
        dividableBy,
        receiverMonkeyIdWhenTrue to receiverMonkeyIdWhenFalse
    )
}.associateBy { it.id }

fun main() {
    fun Map<MonkeyId, Monkey>.startThrowingItems(rounds: Int, onWorryManage: (Long) -> Long): List<Monkey> {

        repeat(rounds) {
            for (monkey in this.values) {
                monkey.inspectedItems += monkey.holdingItems.size
                while (monkey.holdingItems.isNotEmpty()) {
                    val item = monkey.holdingItems.removeFirstOrNull() ?: continue

                    val worryOnItemInspection = when (monkey.operation.first) {
                        '+' -> if (monkey.operation.second == -1) item + item else item + monkey.operation.second
                        '*' -> if (monkey.operation.second == -1) item * item else item * monkey.operation.second
                        else -> error("wrong parse")
                    }

                    val managedWorry = onWorryManage(worryOnItemInspection)

                    val receiverMonkeyId = if (managedWorry % monkey.divisibleBy == 0L) monkey.decision.first
                    else monkey.decision.second

                    this.getValue(receiverMonkeyId).holdingItems.addLast(managedWorry)
                }
            }
        }
        return this.values.toList()
    }

    fun List<Monkey>.most2ActiveMonkeys() = sortedByDescending { it.inspectedItems }.take(2).let {
            it[0].inspectedItems * it[1].inspectedItems
        }


    fun part1(input: List<String>): Long {
        return input.parseMonkeys().startThrowingItems(rounds = 20) { it / 3 }.most2ActiveMonkeys()
    }


    fun part2(input: List<String>): Long {
        val monkeys = input.parseMonkeys()
        val totalDivisibleBy = monkeys.values.map { it.divisibleBy }.reduce { acc, i -> acc * i }

        return monkeys.startThrowingItems(rounds = 10_000) { it % totalDivisibleBy }.most2ActiveMonkeys()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput).also(::println) == 10605L)
    check(part2(testInput).also(::println) == 2713310158)

    val input = readInput("Day11")
//    println(part1(input))
    check(part1(input).also(::println) == 55458L)
    println(part2(input))
    check(part2(input).also(::println) == 14508081294)
}
