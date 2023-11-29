package aoc2022



fun main() {
    fun Set<Char>.calculatePriority(): Int = sumOf { duplicatedItemChar ->
        if (duplicatedItemChar.isLowerCase())
            (duplicatedItemChar.code % 'a'.code) + 1
        else
            (duplicatedItemChar.code % 'A'.code) + 27
    }


    fun part1(input: List<String>): Int = input.sumOf { line ->

        val (first, second) = line.chunked(line.length / 2).map { it.toHashSet() }

        return@sumOf (first intersect second).calculatePriority()
    }

    fun part2(input: List<String>): Int = input.chunked(3) { threeElves ->

        val (first, second, third) = threeElves.map { it.toHashSet() }

        return@chunked (first intersect second intersect third).calculatePriority()
    }.sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput).also(::println) == 157)
    check(part2(testInput).also(::println) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))


}
