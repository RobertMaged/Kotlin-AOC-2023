package aoc2023

import utils.alsoPrintln
import utils.checkEquals
import utils.readInput
import utils.sendAnswer

fun main() = with(Day1) {

    part1(testInput)
        .checkEquals(TODO())
    part1(input)
        .alsoPrintln()
        .sendAnswer(part = 1, day = "1", year = 2023)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .alsoPrintln()
        .sendAnswer(part = 2, day = "1", year = 2023)
}

object Day1 {
    val input
        get() = readInput("Day1", "aoc2023")

    val testInput
        get() = readInput("Day1_test", "aoc2023")

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }
}
