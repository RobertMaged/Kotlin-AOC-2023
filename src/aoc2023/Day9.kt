package aoc2023

import utils.checkEquals
import utils.readInput

fun main(): Unit = with(Day9) {

    part1(testInput).checkEquals(114)
    part1(input)
        .checkEquals(1980437560)
//        .sendAnswer(part = 1, day = "9", year = 2023)

    part2(testInput).checkEquals(2)
    part2(input)
        .checkEquals(977)
//        .sendAnswer(part = 2, day = "9", year = 2023)
}

object Day9 {

    fun String.buildHistoryHierarchy(transform: (List<Long>) -> Long): List<Long> {
        val sequence = this.split(' ').map { it.toLong() }

        val history = mutableListOf<List<Long>>(sequence)

        while (history.last().any { it != 0L }) {
            val diff = history.last().zipWithNext { a, b -> b - a }
            history.add(diff)
        }

        return history.map(transform)
    }

    fun part1(input: List<String>): Long = input.sumOf { line ->

        val lastStepInHistoryHierarchy = line.buildHistoryHierarchy { it.last() }


        return@sumOf lastStepInHistoryHierarchy.foldRight(0L) { acc, n ->
            acc + n
        }
    }


    fun part2(input: List<String>): Long = input.sumOf { line ->

        val firstValueInEachHistory = line.buildHistoryHierarchy { it.first() }

        return@sumOf firstValueInEachHistory.foldRight(0L) { acc, n ->
            acc - n
        }
    }


    val input
        get() = readInput("Day9", "aoc2023")

    val testInput
        get() = readInput("Day9_test", "aoc2023")

}
