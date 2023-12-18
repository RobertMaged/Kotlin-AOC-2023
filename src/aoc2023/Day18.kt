package aoc2023

import utils.*
import kotlin.math.abs

fun main(): Unit = with(Day18) {

    part1(testInput).checkEquals(62)
    part1(input)
        .checkEquals(35244)
//        .sendAnswer(part = 1, day = "18", year = 2023)

    part2(testInput).checkEquals(952408144115)
        .alsoPrintln()
    part2(input)
        .alsoPrintln()
        .checkEquals(85070763635666)
//        .sendAnswer(part = 2, day = "18", year = 2023)
}

object Day18 {

    fun part1(input: List<String>): Long {
        val v = buildSet<Vertex> {
                var curr = Vertex(0, 0)
                add(curr)

                for (line in input) {
                    val (d, count) = line.split(" ").let {
                        it.first().first() to it[1].toInt()
                    }
                    val process: Vertex.() -> Vertex = when (d) {
                        'R' -> Vertex::right
                        'L' -> Vertex::left
                        'U' -> Vertex::up
                        'D' -> Vertex::down
                        else -> error("sss")
                    }

                    repeat(count) {
                        curr.process()
                        curr = curr.process()
                        add(curr)
                    }
                }
            }


        val rowRange = v.minOf { it.y }..v.maxOf { it.y }
        val colRange = v.minOf { it.x }..v.maxOf { it.x }

        val rectangleBoundsThatNotWalked = buildList<Vertex> {
            for (i in rowRange){
                add(Vertex(colRange.first, i))
                add(Vertex(colRange.last, i))
            }

            for (j in colRange){
                add(Vertex(j, rowRange.first))
                add(Vertex(j, rowRange.last))
            }

        }.filter { it !in v }

        val q = ArrayDeque<Vertex>(rectangleBoundsThatNotWalked)

        val seen = hashSetOf<Vertex>()

        while (q.any()) {
            val curr = q.removeFirst()
            if (curr in seen)
                continue
            seen += curr

            curr.allAdjacent().filter {
                it.isInBounds(rowRange, colRange) && it !in v
            }
                .also(q::addAll)
        }
        val rowsCount = ((rowRange.last - rowRange.first) + 1).toLong()
        val colsCount = ((colRange.last - colRange.first) + 1)
        return rowsCount * colsCount - seen.size
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun part2(input: List<String>): Long = input.solve {
        it.substringAfter("#").take(6)
            .let {
                it.last() to it.take(5).hexToInt()
            }
    }

    fun List<String>.solve(parser: (String) -> Pair<Char, Int>): Long {
        var currVertex = LongVertex(0, 0)
        var area = 0L
        var walkedCount = 1L
        for (line in this) {
            val (dir, steps) = parser(line)
            val newVertex = when (dir) {
                '0' -> currVertex.right(steps.toLong())
                '1' -> currVertex.down(steps.toLong())
                '2' -> currVertex.left(steps.toLong())
                '3' -> currVertex.up(steps.toLong())
                else -> error("sss")
            }

            // shoelace area
            area += (newVertex.x - currVertex.x) * (newVertex.y + currVertex.y)
            walkedCount += steps
            currVertex = newVertex
        }
        return abs(area) / 2 + walkedCount / 2 + 1
    }

    val input
        get() = readInput("Day18", "aoc2023")

    val testInput
        get() = readInput("Day18_test", "aoc2023")

}
