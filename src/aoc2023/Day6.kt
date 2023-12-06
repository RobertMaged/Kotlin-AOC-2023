package aoc2023

import utils.checkEquals
import utils.readInput

fun main(): Unit = with(Day6) {

    part1(testInput).checkEquals(288)
    part1(input)
        .checkEquals(219849)
//        .sendAnswer(part = 1, day = "6", year = 2023)

    part2(testInput).checkEquals(71503)
    part2(input)
        .checkEquals(29432455)
//        .sendAnswer(part = 2, day = "6", year = 2023)
}

object Day6 {

    fun part1(input: List<String>): Int {
        val times = "\\d+".toRegex().findAll(input[0]).map { it.value.toInt() }
        val diss = "\\d+".toRegex().findAll(input[1]).map { it.value.toInt() }
        val raceMillisToDistancePairs = times.zip(diss)

        var prod = 1
        for ((time, dis) in raceMillisToDistancePairs) {
            var recordBeatCount = 0
            var buttonHold = 0

            while (buttonHold <= time) {
                val leftTime = time - buttonHold
                val raceDistance = leftTime * buttonHold

                if (raceDistance > dis)
                    recordBeatCount++

                buttonHold++
            }

            prod *= recordBeatCount
        }

        return prod
    }

    fun part2(input: List<String>): Int {
        val time = "\\d+".toRegex().findAll(input[0])
            .map { it.value }
            .fold("") { acc, s -> acc + s }
            .toLong()

        val dis = "\\d+".toRegex().findAll(input[1])
            .map { it.value }
            .fold("") { acc, s -> acc + s }
                .toLong()

        var recordBeatCount = 0
        var buttonHold = 0L

        while (buttonHold <= time) {
            val leftTime = time - buttonHold
            val raceDistance = leftTime * buttonHold

            if (raceDistance > dis)
                recordBeatCount++

            buttonHold++
        }

        return recordBeatCount
    }

    val input
        get() = readInput("Day6", "aoc2023")

    val testInput
        get() = readInput("Day6_test", "aoc2023")

}
