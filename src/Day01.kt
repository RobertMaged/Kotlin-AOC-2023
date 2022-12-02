fun part1(input: List<String>): Int {
    var maxRecordedCalorie = 0
    var currentElvesCalorieSum = 0

    for (line in input) {
        when (val calorie = line.toIntOrNull()) {
            null -> currentElvesCalorieSum = 0
            else -> currentElvesCalorieSum += calorie
        }

        maxRecordedCalorie = maxOf(maxRecordedCalorie, currentElvesCalorieSum)
    }

    return maxRecordedCalorie
}

fun part2(input: List<String>): Int = buildList {
    var currentElvesCalorieSum = 0

    for (lineIndex in input.indices) {
        val calorie = input[lineIndex].toIntOrNull()

        when {
            calorie == null -> {
                add(currentElvesCalorieSum)
                currentElvesCalorieSum = 0
            }

            // to not miss adding last number in file
            lineIndex == input.lastIndex -> add(currentElvesCalorieSum + calorie)

            else -> currentElvesCalorieSum += calorie
        }

    }


}.sortedDescending().take(3).sum()

fun main() {


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")//.plus("\n")
    check(part1(testInput).also(::println) == 24000)
    check(part2(testInput).also(::println) == 45000)

    val input = readInput("Day01").plus("\n")
    println("part1: ${part1(input)}")
    println("part2: ${part2(input)}")
}
