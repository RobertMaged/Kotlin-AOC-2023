package aoc2023

import utils.Vertex
import utils.alsoPrintln
import utils.checkEquals
import utils.readInput
import kotlin.collections.List
import kotlin.collections.contains
import kotlin.collections.filter
import kotlin.collections.flatten
import kotlin.collections.getOrNull
import kotlin.collections.listOf
import kotlin.collections.listOfNotNull
import kotlin.collections.mutableMapOf
import kotlin.collections.plus
import kotlin.collections.set
import kotlin.collections.sumOf
import kotlin.collections.withIndex

fun main(): Unit = with(Day3) {

    part1(testInput).checkEquals(4361)
    part1(input)
        .checkEquals(546312)
//        .sendAnswer(part = 1, day = "3", year = 2023)

    part2(testInput).checkEquals(467835)
    part2(input)
        .alsoPrintln()
        .checkEquals(87449461)
//        .sendAnswer(part = 2, day = "3", year = 2023)
}

object Day3 {

    private val numRegex = "\\d+".toRegex()

    fun part1(input: List<String>): Int {
        fun Char.isSymbol() = !isDigit() && this != '.'

        var sum = 0
        for ((i, line) in input.withIndex()) {
            val nums: Sequence<MatchResult> = numRegex.findAll(line)

            match@ for (numMatchResult in nums) {
                val horizontalAdjacentRange = IntRange(
                    (numMatchResult.range.first - 1).coerceAtLeast(0),
                    (numMatchResult.range.last + 1).coerceAtMost(input[0].lastIndex)
                )

                val top = input.getOrNull(i - 1)?.substring(horizontalAdjacentRange)
                val down = input.getOrNull(i + 1)?.substring(horizontalAdjacentRange)
                val left = line.getOrNull(horizontalAdjacentRange.first)
                val right = line.getOrNull(horizontalAdjacentRange.last)

                val around: List<Char> =
                    listOfNotNull(top?.toList(), down?.toList()).flatten() + listOfNotNull(left, right)

                for (c in around)
                    if (c.isSymbol()) {
                        sum += numMatchResult.value.toInt()
                        continue@match
                    }

            }
        }


        return sum
    }


    fun part2(input: List<String>): Long {
        val gearAdjacentNumbers = mutableMapOf<Vertex, List<Int>>()

        fun Vertex.addToGearMap(num: Int) {
            if (this !in gearAdjacentNumbers)
                gearAdjacentNumbers[this] = listOf(num)
            else
                gearAdjacentNumbers[this] = gearAdjacentNumbers[this]!! + listOf(num)
        }


        for ((i, line) in input.withIndex()) {
            val nums = numRegex.findAll(line)

            for (numMatchResult in nums) {
                val horizontalAdjacentRange = IntRange(
                    (numMatchResult.range.first - 1).coerceAtLeast(0),
                    (numMatchResult.range.last + 1).coerceAtMost(input[0].lastIndex)
                )
                // left
                if (line.getOrNull(horizontalAdjacentRange.first) == '*') {
                    val gearVertex = Vertex(y = i, x = horizontalAdjacentRange.first)
                    gearVertex.addToGearMap(numMatchResult.value.toInt())
                }
                //right
                if (line.getOrNull(horizontalAdjacentRange.last) == '*')
                    Vertex(y = i, x = horizontalAdjacentRange.last).addToGearMap(numMatchResult.value.toInt())

                // top
                input.getOrNull(i - 1)
                    ?.substring(horizontalAdjacentRange)
                    ?.forEachIndexed { cIndex, c ->
                        if (c == '*')
                            Vertex(y = i - 1, x = horizontalAdjacentRange.first + cIndex).addToGearMap(numMatchResult.value.toInt())
                    }

                // down
                input.getOrNull(i + 1)
                    ?.substring(horizontalAdjacentRange)
                    ?.forEachIndexed { cIndex, c ->
                        if (c == '*')
                            Vertex(y = i + 1, x = horizontalAdjacentRange.first + cIndex).addToGearMap(numMatchResult.value.toInt())
                    }

            }
        }

        return gearAdjacentNumbers.values.filter { it.size == 2 }.sumOf { it[0].toLong() * it[1] }
    }


    val input
        get() = readInput("Day3", "aoc2023")

    val testInput
        get() = readInput("Day3_test", "aoc2023")

}
