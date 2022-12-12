
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

     // parts execution   
    val testInput = readInput("Day${DAY_NUMBER_FULL}_test")
    val input = readInput("Day${DAY_NUMBER_FULL}")

    part1(testInput).checkEquals(TODO())
    part1(input)
        .sendAnswer(part = 1, day = "${DAY_NUMBER_FULL}", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "${DAY_NUMBER_FULL}", year = 2022)
}
