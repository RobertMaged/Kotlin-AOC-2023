package aoc2022

import Vertex
import checkEquals

import sendAnswer

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

    fun createShape(type: Int, maxY: Int,pos: Int, lastY: (Int) -> Unit): List<List<Vertex>> {
        var starty = maxY
        val list = listOf(when(type) {
            0 -> (2..5).map { Vertex(x = it + pos, y = starty++) }
                else-> emptyList()
            }
        )
        lastY(starty)
        return list
        }


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
            var x = 0
        val dir = {
            if(input[x++ % input.length] == '<') -1 else 1
        }
        var readyY = 1
        var type = -1

        val base = ArrayDeque(listOf((0..6).map { Vertex(x = it, y = 0) }))

        val lineShape = createShape(++type % 4, readyY, dir()+dir()+dir()+dir()) { newY: Int -> readyY = newY}

        //if intersect go tp next shape
        if(base.last().zip(lineShape.first()).any { it.first.x == it.second.x})
            lineShape.forEach(base::addLast)


        return 0
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
