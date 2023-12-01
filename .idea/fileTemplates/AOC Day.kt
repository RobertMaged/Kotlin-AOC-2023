package aoc2023

import utils.checkEquals
import utils.readInput
import utils.sendAnswer
import utils.alsoPrintln

fun main(): Unit = with(Day${DAY_NUMBER_FULL}) {

    part1(testInput).checkEquals(TODO())
    part1(input)
        .sendAnswer(part = 1, day = "${DAY_NUMBER_FULL}", year = 2023)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "${DAY_NUMBER_FULL}", year = 2023)
}

object Day${DAY_NUMBER_FULL} {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }
    
    val input
        get() = readInput("Day${DAY_NUMBER_FULL}", "aoc2023")

    val testInput
        get() = readInput("Day${DAY_NUMBER_FULL}_test", "aoc2023")

}
