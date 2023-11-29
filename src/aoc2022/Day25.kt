package aoc2022

import checkEquals

import kotlin.math.roundToLong

fun main() {
    val system = mapOf('=' to -2L, '-' to -1L, '0' to 0L, '1' to 1L, '2' to 2L)
    val xSystem = system.map { it.value to it.key }.toMap()

    tailrec fun toSnafu(acc: String, n: Long): String {
        if (n < 1) return acc

        return when (n % 5) {
            in 0..2 -> toSnafu("${xSystem[n % 5]}$acc", n / 5)

            in 3..4 -> toSnafu("${xSystem[n % 5 - 5]}$acc", (n / 5.0).roundToLong())

            else -> error("not supported")
        }
    }

    fun part1(input: List<String>): String {

        val n = input.map {
            it.fold(0L) { acc, c -> (acc * 5) + system.getValue(c) }
        }

        return toSnafu("", n.sum())
    }

    // parts execution
    val testInput = readInput("Day25_test")
    val input = readInput("Day25")

    part1(testInput)
//        .checkEquals("4890")
        .checkEquals("2=-1=0")
    part1(input)
        .checkEquals("2=2-1-010==-0-1-=--2")
//        .sendAnswer(part = 1, day = "25", year = 2022)

}
