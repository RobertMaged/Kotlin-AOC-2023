fun main() {
    infix fun String.markerIndexIfDistinct(markerDistinctChars: Int) = this.windowed(markerDistinctChars).indexOfFirst {
        it.toSet().size == it.length
    } + markerDistinctChars

    fun part1(input: String): Int {
        val markerDistinctChars = 4

        return input markerIndexIfDistinct markerDistinctChars

    }

    fun part2(input: String): Int {

        val markerDistinctChars = 4

        return input markerIndexIfDistinct markerDistinctChars

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput1("Day06_test")
//    check(part1(testInput).also(::println) == 5)
    check(part2(testInput).also(::println) == 19)

    val input = readInput1("Day06")
    println(part1(input))
    println(part2(input))
}
