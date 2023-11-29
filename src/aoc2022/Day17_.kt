package aoc2022

import checkEquals
import sendAnswer
import java.util.*
import kotlin.collections.ArrayDeque

/*
####    the - shape falls first, sixth, 11th, 16th, etc.

.#.
###
.#.

..#
..#
###

#
#
#
#

##
##
 */
private const val ROCK = 1
private const val AIR = 0
private const val REST = -1
private const val LEFT = -1
private const val RIGHT = 1
fun main() {

    /**
     * The tall, vertical chamber is exactly seven units wide.
     * Each rock appears so that its left edge is two units away from the left wall and
     * its bottom edge is three units above the highest rock in the room
     * (or the floor, if there isn't one).
     */
    /*
    fall first , push second
     */
    fun part1(input: String): Int {
        var rocks = 2022

        val rockShapes = arrayOf(
            arrayOf( intArrayOf(AIR, AIR, ROCK, ROCK, ROCK, ROCK, AIR)),
            arrayOf(
                IntArray(7){if (it == 3) ROCK else AIR },
                IntArray(7){if (it in 2..4) ROCK else AIR },
                IntArray(7){if (it == 3) ROCK else AIR },
            ),
            arrayOf(
                IntArray(7){if (it == 4) ROCK else AIR },
                IntArray(7){if (it == 4) ROCK else AIR },
                IntArray(7){if (it in 2..4) ROCK else AIR },
                ),
            Array(4){ IntArray(7){if (it == 2) ROCK else AIR } },
            Array(2){ IntArray(7){if (it in 2..3) ROCK else AIR } }
        )

        val jetDir = ArrayDeque<Int>()
        for (d in input) when(d){
            '<' -> jetDir.addLast(LEFT)
            '>' -> jetDir.addLast(RIGHT)
        }
        var dirInd = 0

        val tower = ArrayDeque<IntArray>().apply { addFirst(IntArray(7) { ROCK }) }

        var fallRocks = 0
        rockFalling@ while (fallRocks < 2023){
            fallRocks++

//            repeat(3){
//                tower.addFirst(IntArray(7){AIR})
//            }

//            val shape = rockShapes[fallRocks % 5] + Array(3){IntArray(7){AIR} }
            var shape = (rockShapes[fallRocks % 5] + Array(3){IntArray(7){ AIR } }).map { it.toMutableList() }.toMutableList()
//            shape.forEach(tower::addFirst)
            repeat(3){
                for (line in shape) {
                    when (val d = jetDir[dirInd % jetDir.size]) {
                        LEFT -> {
                            if (line.first() == ROCK)
                                continue
                            else
                                Collections.rotate(line, LEFT)
                        }

                        RIGHT -> {
                            if (line.last() == ROCK)
                                continue
                            else
                                Collections.rotate(line, RIGHT)
                        }
                    }
                    dirInd++
                }
                Collections.rotate(shape, 1)
            }
            when (val d = jetDir[dirInd % jetDir.size]) {
                LEFT -> {
                    if (shape.last().first() == ROCK)
                        continue
                    else
                        Collections.rotate(shape.last(), LEFT)
                }

                RIGHT -> {
                    if (shape.last().last() == ROCK)
                        continue
                    else
                        Collections .rotate(shape.last(), RIGHT)
                }
            }
            dirInd++
            shape = shape.dropWhile { it.all { it == AIR } }.toMutableList()

                var isDeadEnd = false
                var lastLine = tower.last()
            var lastIndex= 0
            rendevo@ while (true){
                val lc= lastLine.mapIndexed { index, i -> if (i == ROCK) index else -2}.filterNot { it == -2 }.toSet()
                val sc = shape.last().mapIndexed { index, i -> if (i == ROCK) index else -2}.filterNot { it == -2 }.toSet()
                if ((lc intersect sc).any()) {
                    shape.map{it.toIntArray()}.forEach(tower::addLast)
                    break@rendevo
                }
                when (val d = jetDir[dirInd % jetDir.size]) {
                    LEFT -> {
                        if (shape.last().first() == ROCK)
                            continue
                        else
                            Collections.rotate(shape.last(), LEFT)
                    }

                    RIGHT -> {
                        if (shape.last().last() == ROCK)
                            continue
                        else
                            Collections .rotate(shape.last(), RIGHT)
                    }
                }
                dirInd++
                lastLine = tower[tower.lastIndex-1]
                lastIndex++
            }
        }



        return tower.size
    }

    fun part2(input: String): Int {
        return 0
    }

    // parts execution
    val testInput = readInputAsText("Day17_test")
    val input = readInputAsText("Day17")

    part1(testInput).checkEquals(3068)
    part1(input)
        .sendAnswer(part = 1, day = "17", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "17", year = 2022)
}
