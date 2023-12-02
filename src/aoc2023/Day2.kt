package aoc2023

import utils.checkEquals
import utils.readInput

fun main(): Unit = with(Day2) {

    part1(testInput).checkEquals(8)
    part1(input)
        .checkEquals(2176)
//        .sendAnswer(part = 1, day = "2", year = 2023)

    part2(testInput).checkEquals(2286)
    part2(input)
        .checkEquals(63700)
//        .sendAnswer(part = 2, day = "2", year = 2023)
}

object Day2 {

    data class GameSet(
        val id: Int,
        val red: Int,
        val green: Int,
        val blue: Int,
    )


    fun part1(input: List<String>): Int = input.sumOf { line ->

        val gameSets = line.parseGameSets()

        if (
            gameSets.any {
                it.red > 12 || it.green > 13 || it.blue > 14
            }
        )
            return@sumOf 0

        return@sumOf gameSets.random().id
    }

    fun part2(input: List<String>): Int = input.sumOf { line ->

        val gameSets = line.parseGameSets()


        val r = gameSets.maxOf { it.red }
        val g = gameSets.maxOf { it.green }
        val b = gameSets.maxOf { it.blue }


        return@sumOf r * g * b
    }

    fun String.parseGameSets(): List<GameSet> {
        val line = this
        val id = line.substringBefore(':').drop(5).toInt()
        val sets = line.substringAfter(':').split(';')

        val gameSets = sets.map {
            val colors = it.split(',').map { it.trim() }
            val red = colors.singleOrNull { it.endsWith("red") }?.takeWhile { it.isDigit() }
            val green = colors.singleOrNull { it.endsWith("green") }?.takeWhile { it.isDigit() }
            val blue = colors.singleOrNull { it.endsWith("blue") }?.takeWhile { it.isDigit() }

            GameSet(
                id,
                red = red?.toInt() ?: 0,
                green = green?.toInt() ?: 0,
                blue = blue?.toInt() ?: 0
            )

        }

        return gameSets
    }


    val input
        get() = readInput("Day2", "aoc2023")

    val testInput
        get() = readInput("Day2_test", "aoc2023")

}
