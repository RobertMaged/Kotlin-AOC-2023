package aoc2023

import utils.checkEquals
import utils.readInput
import utils.sendAnswer
import utils.alsoPrintln

fun main(): Unit = with(Day${DAY_NUMBER_FULL}) {

    part1(testInput).checkEquals(TODO())
    part1(input)
        .alsoPrintln()
        .sendAnswer(part = 1, day = "${DAY_NUMBER_FULL}", year = 2023)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .alsoPrintln()
        .sendAnswer(part = 2, day = "${DAY_NUMBER_FULL}", year = 2023)
}
private typealias PuzzleNum = Int
object Day${DAY_NUMBER_FULL} {

    fun part1(input: List<String>): PuzzleNum {
        return input.size
    }

    fun part2(input: List<String>): PuzzleNum {
        return input.size
    }
    
    val input
        get() = readInput("Day${DAY_NUMBER_FULL}", "aoc2023")

    val testInput
        get() = readInput("Day${DAY_NUMBER_FULL}_test", "aoc2023")

}
