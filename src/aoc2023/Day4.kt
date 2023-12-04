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
        val cardToProcess = IntArray(input.size) { 1 }
        val cardToProcessLog = IntArray(input.size) { 1 }

        val cardCachedResultProcess = Array<(() -> Unit)?>(input.size) { null }

        var cardNum = 1
        fun cardPosition() = cardNum - 1

        while (cardNum <= input.size) {
            cardToProcess[cardPosition()] = cardToProcess[cardPosition()] - 1

            if (cardCachedResultProcess[cardPosition()] == null) {

                val (winningNums, myNums) = input[cardPosition()].substringAfter(":").split(" | ")
                    .map {
                        it.trim().split(spaceRegex)
                    }

                val winCount = myNums.count { it in winningNums }

                cardCachedResultProcess[cardPosition()] = {
                    for (winingCopyCard in ((cardNum + 1)..cardNum + winCount)) {
                        val copyCardPosition = winingCopyCard - 1
                        cardToProcess[copyCardPosition] = cardToProcess[copyCardPosition] + 1
                        cardToProcessLog[copyCardPosition] = cardToProcessLog[copyCardPosition] + 1
                    }
                }
            }

            cardCachedResultProcess[cardPosition()]!!.invoke()

            if (cardToProcess[cardPosition()] < 1)
                cardNum++

        }

        return cardToProcessLog.sum()
    }

    val input
        get() = readInput("Day4", "aoc2023")

    val testInput
        get() = readInput("Day4_test", "aoc2023")

}
