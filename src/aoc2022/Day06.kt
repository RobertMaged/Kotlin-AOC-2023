package aoc2022


fun main() {
    infix fun String.markerIndexIfDistinct(markerDistinctChars: Int) = this.windowed(markerDistinctChars).indexOfFirst {
        it.toSet().size == it.length
    } + markerDistinctChars

    fun part1(input: String): Int {
        val markerDistinctChars = 4

        return input markerIndexIfDistinct markerDistinctChars
    }

    fun part2(input: String): Int {
        val markerDistinctChars = 14

        return input markerIndexIfDistinct markerDistinctChars
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    for (line in testInput)
        line.split(':')
            .also { check(it[0].markerIndexIfDistinct(4) == it[1].toInt()) }
            .also { check(it[0].markerIndexIfDistinct(14) == it[2].toInt()) }


    val input = readInputAsText("Day06")
    println(part1(input))
    println(part2(input))
}
