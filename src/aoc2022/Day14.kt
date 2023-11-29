package aoc2022

import Vertex
import checkEquals
import get
import getOrDefault
import set

import kotlin.math.max

private const val ROCK = -1
private const val AIR = 0
private const val SAND = 1

//private val SAND_START_POINT = 0 to 500
private val SAND_START_POINT = Vertex(500, 0)
fun main() {

    // parts execution
    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    Day14.part1(testInput).checkEquals(24)
    Day14.part1(input)
        .checkEquals(979)
//        .sendAnswer(part = 1, day = "14", year = 2022)

    Day14.part2(testInput).checkEquals(93)
    Day14.part2(input)
        .checkEquals(29044)
//        .sendAnswer(part = 2, day = "14", year = 2022)
}

object Day14 {
    val input get() = readInput("Day14")
    fun part1(input: List<String>): Int {
        var highestY = -1
        val cave2D = input.scanCave { maxY, _ -> highestY = maxY }

        return cave2D.startSandFlowThenCount(highestY)
    }

    fun part2(input: List<String>): Int {
        var highestY = -1
        val cave2D = input.scanCave(afterScanTransform = { maxY, cave ->
            highestY = maxY
            cave[highestY + 2] = cave[highestY + 2].map { ROCK }.toIntArray()
        })

        return cave2D.startSandFlowThenCount(highestY + 2)
    }

}

private typealias Cave2D = Array<IntArray>

private fun List<String>.scanCave(afterScanTransform: (highestY: Int, cave: Cave2D) -> Unit): Cave2D {
    // y lies in 200 row, x lies in 1000 col
    val cave2D = Array(200) { IntArray(1000) { AIR } }

    var highestY = -1
    for (line in this)
        for ((from, to) in line.split(" -> ").windowed(2)) {
            val fromVertx = from.split(',').let(Vertex.Companion::ofDestructuringStrings)
            val toVertx = to.split(',').let(Vertex.Companion::ofDestructuringStrings)

            when {
                fromVertx isHorizontallyEquals toVertx -> {
                    val directionRange =
                        if (fromVertx.y < toVertx.y) (fromVertx.y..toVertx.y) else (toVertx.y..fromVertx.y)
                    for (i in directionRange)
                        cave2D[i][fromVertx.x] = ROCK
                }

                fromVertx isVerticallyEquals toVertx -> {
                    val directionRange =
                        if (fromVertx.x < toVertx.x) (fromVertx.x..toVertx.x) else (toVertx.x..fromVertx.x)
                    for (i in directionRange)
                        cave2D[fromVertx.y][i] = ROCK
                }

                else -> error("not accepted input")
            }
            max(fromVertx.y, toVertx.y).let {
                if (it > highestY)
                    highestY = it
            }
        }

    afterScanTransform(highestY, cave2D)
    return cave2D
}

private fun Cave2D.startSandFlowThenCount(maxHeight: Int): Int {
    val cave2D = this
    var score = 0

    var fallingSandVertex = SAND_START_POINT //(500 - 400)

    sandFlow@ while (fallingSandVertex.y < maxHeight) when {

        cave2D canGoDownFrom fallingSandVertex -> fallingSandVertex = fallingSandVertex.down()

        cave2D canGoDownLeftFrom fallingSandVertex -> fallingSandVertex = fallingSandVertex.downLeft()

        cave2D canGoDownRightFrom fallingSandVertex -> fallingSandVertex = fallingSandVertex.downRight()

        else -> {
            score++
            cave2D[fallingSandVertex] = SAND
            fallingSandVertex = SAND_START_POINT


            if (cave2D.getOrDefault(fallingSandVertex.downLeft(), defaultValue = SAND) == SAND &&
                cave2D[fallingSandVertex] == SAND &&
                cave2D.getOrDefault(fallingSandVertex.downRight(), defaultValue = SAND) == SAND
            ) {
                cave2D[fallingSandVertex] = SAND
                break@sandFlow
            }
        }
    }

    return score
}

private infix fun Cave2D.canGoDownFrom(ver: Vertex) = this[ver.down()] == AIR

private infix fun Cave2D.canGoDownLeftFrom(ver: Vertex) =
    this.getOrDefault(ver.downLeft(), ROCK) == AIR

private infix fun Cave2D.canGoDownRightFrom(ver: Vertex) =
    this.getOrDefault(ver.downRight(), ROCK) == AIR
