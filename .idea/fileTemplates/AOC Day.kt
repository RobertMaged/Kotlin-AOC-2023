
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${DAY_NUMBER_FULL}_test")
    check(part1(testInput).also(::println) == 1)
    //check(part2(testInput).also(::println) == 1)

    val input = readInput("Day${DAY_NUMBER_FULL}")
    println(part1(input))
    println(part2(input))
}
