fun main() {
    /*
        split every group chunk, using empty line

        sum each chunk
     */

    fun part1(input: List<String>): Int {

        var max = 0
        var oneElvesSum = 0
        for (line in input) {
            val calorie = line.toIntOrNull()

            if (calorie == null)
                max = maxOf(max, oneElvesSum).also { oneElvesSum = 0 }
            else
                oneElvesSum += calorie
        }

        return max
    }

    fun part2(input: List<String>): Int = buildList {
        var oneElvesSum = 0

        for (line in input) {

            val calorie = line.toIntOrNull()

            if (calorie == null) {
                add(oneElvesSum)
                oneElvesSum = 0
            } else
                oneElvesSum += calorie
        }

    }
        .sortedDescending()
        .take(3)
        .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").plus("\n")
    check(part1(testInput).also(::println) == 24000)
    check(part2(testInput).also(::println) == 45000)

    val input = readInput("Day01").plus("\n")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}
