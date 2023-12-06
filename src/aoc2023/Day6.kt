package aoc2023

import utils.checkEquals
import utils.readInputAsText

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

    private fun String.extractAllNums() =
        "\\d+".toRegex().findAll(this).map { it.value.toLong() }.toList()

    fun part1(input: String): Long {
        val allNums = input.extractAllNums()

        val (times, diss) = allNums.chunked(allNums.size / 2)

        val raceMillisToDistancePairs = times.zip(diss)

        return raceMillisToDistancePairs.fold(1) { prod, (time, dis) ->
            prod * countRecordBeat(time, dis)
        }

    }

    fun part2(input: String): Long {
        val allNums = input.extractAllNums()

        val (time, dis) = allNums.chunked(allNums.size / 2).map {
            it.joinToString(separator = "").toLong()
        }

        return countRecordBeat(time, dis)
    }

    private fun countRecordBeat(time: Long, dis: Long): Long {
        var recordBeatCount = 0L
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
        get() = readInputAsText("Day6", "aoc2023")

    val testInput
        get() = readInputAsText("Day6_test", "aoc2023")

}
