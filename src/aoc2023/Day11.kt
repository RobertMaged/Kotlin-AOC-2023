package aoc2023

import utils.checkEquals
import utils.readInputAs2DCharArray
import kotlin.math.abs

fun main(): Unit = with(Day11) {

    testInput.solve(2).checkEquals(374)
    part1(input)
        .checkEquals(10313550)
//        .sendAnswer(part = 1, day = "11", year = 2023)

    testInput.solve(10).checkEquals(1030)
    testInput.solve(100).checkEquals(8410)
    part2(input)
        .checkEquals(611998089572)
//        .sendAnswer(part = 2, day = "11", year = 2023)
}


object Day11 {
    data class Galaxy(val id: Int, val y: Long, val x: Long)

    fun part1(input: Array<CharArray>) = input.solve(2)

    fun part2(input: Array<CharArray>) = input.solve(1_000_000)

    fun Array<CharArray>.solve(factor: Int): Long {
        val input = this

        val (colHeight, rowWidth) = input.size to input.first().size

        val emptyRowsIndcies = input.mapIndexedNotNull { index, chars ->
            index.takeIf { chars.all { it == '.' } }
        }

        val emptyColsIndcies = (0..<colHeight).mapNotNull { x ->
            x.takeIf { (0..<rowWidth).map { y -> input[y][x] }.all { it == '.' } }
        }

        val newSpaceToAppend = factor - 1L
        val galaxies = buildList {
            for (i in input.indices)
                for (j in input[i].indices)
                    if (input[i][j] == '#') {
                        val newY = i + emptyRowsIndcies.count { it < i } * newSpaceToAppend
                        val newX = j + emptyColsIndcies.count { it < j } * newSpaceToAppend
                        add(Galaxy(this.size, y = newY, x = newX))
                    }
        }

        val galaxyCombinationPairs = buildSet {
            for (i in galaxies.indices)
                for (j in i + 1..galaxies.lastIndex)
                    add(galaxies[i] to galaxies[j])
        }

        return galaxyCombinationPairs.sumOf { (g1, g2) ->
            abs(g1.x - g2.x) + abs(g1.y - g2.y)
        }

    }

    val input
        get() = readInputAs2DCharArray("Day11", "aoc2023")

    val testInput
        get() = readInputAs2DCharArray("Day11_test", "aoc2023")

}
