import java.math.BigInteger

private val Old = -1
fun main() {
//    data class Monkey(val name: Int, val holdingItems: ArrayDeque<BigInteger>,val operation: Pair<Char, Int> , val divisableBy: Int, val decection: Pair<Int, Int>, val inspectedItems: MutableList<BigInteger> = mutableListOf())
    data class Monkey(val name: Int, val holdingItems: ArrayDeque<BigInteger>,val operation: Pair<Char, Int> , val divisableBy: Int, val decection: Pair<Int, Int>, val inspectedItems: MutableList<Long> = mutableListOf())
    /*
    worry level to be divided by three and rounded down to the nearest integer

     Monkey inspects an item with a worry level of 79.
    Worry level is multiplied by 19 to 1501.
    Monkey gets bored with item. Worry level is divided by 3 to 500.
    Current worry level is not divisible by 23.
    Item with worry level 500 is thrown to monkey 3.
     */
/*
    fun part1(input: List<String>): Int {
        val roundsAfter = 20
        val dividedbythenround = 3

        var score = 0

        val all = input.filter { it.isNotBlank() } .chunked(6)

        val monkeys = mutableListOf<Monkey>()
        for (monky in all){
            val monNum = monky.first().split(" ")[1].takeWhile { it.isDigit() }.toInt()
            val items = monky[1].substringAfter("Starting items: ").chunked(2).filter { it.toIntOrNull() != null}.map { it.toInt() }
            val operation = monky[2].substringAfter("Operation: new = ").split(" ")
            val test = monky[3].substringAfter("Test: divisible by ").toInt()
            val isTrue = monky[4].substringAfter(" monkey ").toInt()
            val infalse = monky[5].substringAfter(" monkey ").toInt()

            Monkey(monNum,
                ArrayDeque(items),
                operation[1].first() to if(operation[2].toIntOrNull() != null) operation[2].toInt() else -1,
                test,
                isTrue to infalse
                ).also(monkeys::add)
        }
        repeat(roundsAfter) {
            for (m in monkeys) {
                    m.inspectedItems += m.holdingItems.size
                while (m.holdingItems.isNotEmpty()){
                    val item = m.holdingItems.removeFirstOrNull() ?: continue

                    val wory: Int = (when (m.operation.first) {
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

                    monkeys.single { it.name == newNum }.holdingItems.addLast(wory)
                    val itm=0
                }
                val monky=0
            }
            val round = 0
        }
        monkeys.map { it.name to it.inspectedItems.sum() }.forEach(::println)

       return monkeys.map{
            it.inspectedItems.sum() }
            .sortedByDescending{it}.take(2).let {
                it[0] * it[1]
        }
    }
*/

    fun part2(input: List<String>): BigInteger {
        val roundsAfter = 10_000
        val dividedbythenround = 3

        var score = 0

        val all = input.filter { it.isNotBlank() } .chunked(6)

        val monkeys = mutableListOf<Monkey>()
        for (monky in all){
            val monNum = monky.first().split(" ")[1].takeWhile { it.isDigit() }.toInt()
            val items = monky[1].substringAfter("Starting items: ").chunked(2).filter { it.toIntOrNull() != null}.map { it.toBigInteger() }
            val operation = monky[2].substringAfter("Operation: new = ").split(" ")
            val test = monky[3].substringAfter("Test: divisible by ").toInt()
            val isTrue = monky[4].substringAfter(" monkey ").toInt()
            val infalse = monky[5].substringAfter(" monkey ").toInt()

            Monkey(monNum,
                ArrayDeque(items),
                operation[1].first() to if(operation[2].toIntOrNull() != null) operation[2].toInt() else -1,
                test,
                isTrue to infalse
            ).also(monkeys::add)
        }

        val total = monkeys.map { it.divisableBy }.reduce { acc, i ->  acc * i }.toLong()

        repeat(roundsAfter) {
            for (m in monkeys) {
//                m.inspectedItems += m.holdingItems.size.toBigInteger()
                m.inspectedItems += m.holdingItems.size.toLong()
                while (m.holdingItems.isNotEmpty()){
                    val item = m.holdingItems.removeFirstOrNull()?.toLong() ?: continue

                    val wory = (when (m.operation.first) {
                        '+' -> if (m.operation.second == -1) item + item else item + m.operation.second
                        '*' -> if (m.operation.second == -1) item * item else item * m.operation.second
                        else -> error("wrong parse")
                    }  % total).toBigInteger()

//                        if ((wory / m.divisableBy).toString().substringAfter('.', "null") != "null")
                        if ( wory.mod(m.divisableBy.toBigInteger())  == BigInteger.ZERO) {
                    monkeys.single { it.name == m.decection.first }.holdingItems.addLast(wory)
                        } else {
//                    monkeys.single { it.name == m.decection.second }.holdingItems.addLast(wory.divide((m.divisableBy).toBigInteger()))
                    monkeys.single { it.name == m.decection.second }.holdingItems.addLast(wory)
                        }

                    val itm=0
                }
                val monky=0
            }
            val round = 0
        }
        monkeys.map { it.name to it.inspectedItems.sum()}.forEach(::println)

        return monkeys.map {
            it.inspectedItems.sum()
        }
            .sortedByDescending{it}.take(2).let {
                (it[0] * it[1])
            }.toBigInteger()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
//    check(part1(testInput).also(::println) ==10605)
    check(part2(testInput).also(::println) == BigInteger.valueOf (2713310158))

    val input = readInput("Day11")
//    println(part1(input))
//    check(part1(input).also(::println) ==55458)
    println(part2(input))
    check(part2(input).also(::println) == BigInteger.valueOf (14508081294))
}
