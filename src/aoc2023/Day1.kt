package aoc2023

import utils.alsoPrintln
import utils.checkEquals
import utils.checkNotEquals
import utils.readInput

fun main(): Unit = with(Day1) {

    part1(testInput.take(4))
        .checkEquals(142)
    part1(input)
        .alsoPrintln()
//        .sendAnswer(part = 1, day = "1", year = 2023)

    part2(testInput.drop(4)).checkEquals(281)
    part2(input)
        .checkNotEquals(55624)
        .checkNotEquals(55929)
        .checkEquals(55902)
        .alsoPrintln()
//        .sendAnswer(part = 2, day = "1", year = 2023)
}

object Day1 {

    fun part1(input: List<String>): Int = input.sumOf { line ->

        (line.first { it.isDigit() } + "" + line.last { it.isDigit() }).toInt()
    }

    fun part2(input: List<String>): Int {

        val spelledNumbersMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

        val regexToCaptureFirstN =
            spelledNumbersMap.map { it.key }.joinToString(prefix = "\\d|", separator = "|").toRegex()

        val regexToCaptureLastNReversed =
            spelledNumbersMap.map { it.key.reversed() }.joinToString(prefix = "\\d|", separator = "|").toRegex()

        return input.sumOf { line ->

            val firstN = regexToCaptureFirstN.find(line)!!.value.let { digitOrSpelledN ->
                digitOrSpelledN.toIntOrNull() ?: spelledNumbersMap.getValue(digitOrSpelledN)
            }

            val lastN = regexToCaptureLastNReversed.find(line.reversed())!!.value
                .let { digitOrReversedSpelledN ->

                    digitOrReversedSpelledN.toIntOrNull()
                        ?: spelledNumbersMap.getValue(digitOrReversedSpelledN.reversed())
                }

            firstN * 10 + lastN
        }
    }

    val input
        get() = readInput("Day1", "aoc2023")

    val testInput
        get() = readInput("Day1_test", "aoc2023")
}
