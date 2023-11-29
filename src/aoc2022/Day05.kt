package aoc2022



private typealias StacksMap = Map<Int, ArrayDeque<Char>>
private data class RearrangementProcedure(val movesCount: Int, val sourceStackNo: Int, val desStackNo: Int)

fun main() {
    fun List<String>.splitStacksFromProcedures(): Pair<List<String>, List<String>> {
        val stacksCrates = this.takeWhile { it.isNotBlank() }
        val procedures = this.dropWhile { !it.contains("move") }

        return stacksCrates to procedures
    }

    fun List<String>.readProcedures(): List<RearrangementProcedure> = map { instruction ->
        val count = instruction.substringAfter("move ").takeWhile { it.isDigit() }.toInt()
        val from = instruction.substringAfter("from ").takeWhile { it.isDigit() }.toInt()
        val to = instruction.substringAfter("to ").takeWhile { it.isDigit() }.toInt()

        return@map RearrangementProcedure(count, from, to)
    }

    fun List<String>.loadStacksCrates(): StacksMap =
        fold(mutableMapOf()) { stacksMap, line ->

            line.chunked(4).forEachIndexed { index, block ->
                val crate = block[1].takeIf { it.isLetter() } ?: return@forEachIndexed

                val cratesStack = stacksMap[index + 1]
                if (cratesStack == null)
                    stacksMap[index + 1] = ArrayDeque(listOf(crate))
                else
                    cratesStack.addFirst(crate)
            }

            return@fold stacksMap
        }

    fun List<String>.loadStacksAndProcedures(): Pair<StacksMap, List<RearrangementProcedure>> {
        val (stacks, procedures) = this.splitStacksFromProcedures()

        return stacks.loadStacksCrates() to procedures.readProcedures()
    }

    fun StacksMap.topCratesAsMsg(): String =
        (1..this.size).fold("") { msgAcc, i ->
            msgAcc + this.getValue(i).last()
        }

    fun part1(input: List<String>): String {

        val (stacksMap, procedures) = input.loadStacksAndProcedures()

        for (p in procedures) {
            val source = stacksMap.getValue(p.sourceStackNo)
            val des = stacksMap.getValue(p.desStackNo)

            repeat(p.movesCount) { _ ->
                des.addLast(source.removeLast())
            }
        }

        return stacksMap.topCratesAsMsg()
    }

    fun part2(input: List<String>): String {

        val (stacksMap, procedures) = input.loadStacksAndProcedures()

        for (p in procedures) {
            val source = stacksMap.getValue(p.sourceStackNo)
            val des = stacksMap.getValue(p.desStackNo)

            val desInputIndex = des.size
            repeat(p.movesCount) { _ ->
                des.add(desInputIndex, source.removeLast())
            }
        }

        return stacksMap.topCratesAsMsg()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == "CMZ")
    check(part2(testInput).also(::println) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
