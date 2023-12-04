package aoc2023

import utils.checkEquals
import utils.readInput

fun main(): Unit = with(Day4) {

    part1(testInput).checkEquals(13)
    part1(input)
        .checkEquals(21485)
//        .sendAnswer(part = 1, day = "4", year = 2023)

    part2(testInput).checkEquals(30)
    part2(input)
        .checkEquals(11024379)
//        .sendAnswer(part = 2, day = "4", year = 2023)
}

object Day4 {
    private val spaceRegex = "\\s+".toRegex()

    fun part1(input: List<String>): Int = input.sumOf { line ->

        val (winningNums, myNums) = line.substringAfter(":").split(" | ")
            .map {
                it.trim().split(spaceRegex)
            }

        return@sumOf myNums.filter { it in winningNums }
            .fold(0) { acc, _ ->
                if (acc == 0)
                    1
                else
                    acc * 2
            }.toInt()

    }

    fun part2(input: List<String>): Int {
        val cardsCurrentInstances = IntArray(input.size) { 1 }
        val cardsInstancesLog = IntArray(input.size) { 1 }

        val cardWinningCachedProcess = Array<(() -> Unit)?>(input.size) { null }

        var cardPos = 0

        while (cardPos <= input.lastIndex) {
            cardsCurrentInstances[cardPos] = cardsCurrentInstances[cardPos] - 1

            if (cardWinningCachedProcess[cardPos] == null) {

                val (winningNums, myNums) = input[cardPos].substringAfter(":").split(" | ")
                    .map {
                        it.trim().split(spaceRegex)
                    }

                val winCount = myNums.count { it in winningNums }

                cardWinningCachedProcess[cardPos] = {
                    for (winingCopyCard in (cardPos + 1)..cardPos + winCount) {
                        cardsCurrentInstances[winingCopyCard] = cardsCurrentInstances[winingCopyCard] + 1
                        cardsInstancesLog[winingCopyCard] = cardsInstancesLog[winingCopyCard] + 1
                    }
                }
            }

            cardWinningCachedProcess[cardPos]!!.invoke()

            if (cardsCurrentInstances[cardPos] < 1)
                cardPos++

        }

        return cardsInstancesLog.sum()
    }

    val input
        get() = readInput("Day4", "aoc2023")

    val testInput
        get() = readInput("Day4_test", "aoc2023")

}
