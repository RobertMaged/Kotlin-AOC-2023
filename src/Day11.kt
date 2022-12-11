private val Old = -1

@JvmInline
value class MonkeyId(val vlu: Int)
private data class Monkey(
    val id: Int,
    val holdingItems: ArrayDeque<Int>,
    val operation: Pair<Char, Int>,
    val divisableBy: Int,
    val decection: Pair<Int, Int>,
//        val inspectedItems: MutableList<Long> = mutableListOf()
    var inspectedItems: Long = 0
)
private fun List<String>.parseMonkeys(): Map<Int, Monkey> = filter { it.isNotBlank() }
    .chunked(6).map{ monkeyNotes->

        val id = monkeyNotes.first().split(" ")[1].takeWhile { it.isDigit() }.toInt()
        val startingItems = monkeyNotes[1].substringAfter("Starting items: ").chunked(2).filter { it.toIntOrNull() != null }
            .map { it.toInt() }
        val operation = monkeyNotes[2].substringAfter("Operation: new = ").split(" ")
        val divisableBy = monkeyNotes[3].substringAfter("Test: divisible by ").toInt()
        val receiverMonkeyIdWhenTrue = monkeyNotes[4].substringAfter(" monkey ").toInt()
        val receiverMonkeyIdWhenFalse = monkeyNotes[5].substringAfter(" monkey ").toInt()

        return@map Monkey(
            id,
            ArrayDeque(startingItems),
            operation[1].first() to if (operation[2].toIntOrNull() != null) operation[2].toInt() else -1,
            divisableBy,
            receiverMonkeyIdWhenTrue to receiverMonkeyIdWhenFalse
        )
    }.associateBy { it.id }
fun main() {


        fun part1(input: List<String>): Long {
            val roundsAfter = 20
            val dividedbythenround = 3

            val monkeys = input.parseMonkeys().values.toList()

            repeat(roundsAfter) {
                for (m in monkeys) {
                        m.inspectedItems += m.holdingItems.size
                    while (m.holdingItems.isNotEmpty()){
                        val item = m.holdingItems.removeFirstOrNull() ?: continue

                        val wory = (when (m.operation.first) {
                            '+' -> if (m.operation.second == -1) item + item else item + m.operation.second
                            '*' -> if (m.operation.second == -1) item * item else item * m.operation.second
                            else -> error("wrong parse")
                        } / dividedbythenround)

                        val newNum =
    //                        if ((wory / m.divisableBy).toString().substringAfter('.', "null") != "null")
                            if (wory % m.divisableBy == 0)
                            m.decection.first
                        else
                            m.decection.second

                        monkeys.single { it.id == newNum }.holdingItems.addLast(wory)
                    }
                }
            }

           return monkeys.map{
                it.inspectedItems }
                .sortedByDescending{it}.take(2).let {
                    it[0] * it[1]
            }
        }


    fun part2(input: List<String>): Long {
        val roundsAfter = 10_000

        val monkeys = input.parseMonkeys().values.toList()
        val total = monkeys.map { it.divisableBy }.reduce { acc, i -> acc * i }.toLong()

        repeat(roundsAfter) {
            for (m in monkeys) {
                m.inspectedItems += m.holdingItems.size
                while (m.holdingItems.isNotEmpty()) {
                    val item = m.holdingItems.removeFirstOrNull()?.toLong() ?: continue

                    val wory = (when (m.operation.first) {
                        '+' -> if (m.operation.second == -1) item + item else item + m.operation.second
                        '*' -> if (m.operation.second == -1) item * item else item * m.operation.second
                        else -> error("wrong parse")
                    } % total )

                    val throwToMonkeyN = if (wory % m.divisableBy == 0L)
                        m.decection.first
                    else
                        m.decection.second


                    monkeys.single { it.id == throwToMonkeyN }.holdingItems.addLast(wory.toInt())
                }
            }
        }

        return monkeys.map {
            it.inspectedItems
        }
            .sortedByDescending { it }.take(2).let {
                (it[0] * it[1])
            }.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput).also(::println) ==10605L)
    check(part2(testInput).also(::println) == 2713310158)

    val input = readInput("Day11")
//    println(part1(input))
    check(part1(input).also(::println) ==55458L)
    println(part2(input))
    check(part2(input).also(::println) == 14508081294)
}
