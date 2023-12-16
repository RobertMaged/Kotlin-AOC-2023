package aoc2023

import utils.Vertex
import utils.checkEquals
import utils.readInputAs2DCharArray

fun main(): Unit = with(Day16) {

    part1(testInput)
        .checkEquals(46)
    part1(input)
        .checkEquals(6816)
//        .sendAnswer(part = 1, day = "16", year = 2023)

    part2(testInput).checkEquals(51)
    part2(input)
        .checkEquals(8163)
//        .sendAnswer(part = 2, day = "16", year = 2023)
}


object Day16 {
    enum class Dir {
        up, down, left, right
    }

    infix fun Vertex.to(d: Dir) = VertexToDir(this, d)
    data class VertexToDir(val vertex: Vertex, val dir: Dir)

    operator fun Array<CharArray>.get(v: Vertex) = this.getOrNull(v.y)?.getOrNull(v.x) ?: '#'

    fun part1(input: Array<CharArray>): Int = input.countEnergized(Vertex(0, 0) to Dir.right)

    fun part2(input: Array<CharArray>): Int {
        val cols = input[0].size
        val rows = input.size
        val possibleStart = buildList<VertexToDir> {
            for (i in 0..<rows) {
                for (j in 0..<cols) {
                    if (i in 1..rows - 2 && j in 1..cols - 2)
                        continue

                    if (i == 0)
                        add(Vertex(j, i) to Dir.down)
                    else if (i == rows - 1)
                        add(Vertex(j, i) to Dir.up)

                    if (j == 0)
                        add(Vertex(j, i) to Dir.right)
                    else if (j == cols - 1)
                        add(Vertex(j, i) to Dir.left)
                }
            }
        }


        return possibleStart.maxOf { input.countEnergized(it) }

    }

    fun Array<CharArray>.countEnergized(start: VertexToDir): Int {

        val energized = hashSetOf<VertexToDir>()

        val heads = ArrayDeque(listOf(start))


        while (heads.any()) {
            val curr = heads.removeFirst()

            val c = this[curr.vertex]

            if (c == '#' || curr in energized) {
                continue
            }
            energized.add(curr)

            val (nextPoint, nextSplitPointOrNull) = when (c) {

                '.' -> curr.getNextVertexToDir() to null

                '/' -> {

                    val newDir = when (curr.dir) {
                        Dir.right -> Dir.up
                        Dir.down -> Dir.left
                        Dir.left -> Dir.down
                        Dir.up -> Dir.right
                    }
                    val new = (curr.vertex to newDir).getNextVertexToDir()

                    new to null
                }

                '\\' -> {

                    val newDir = when (curr.dir) {
                        Dir.up -> Dir.left
                        Dir.right -> Dir.down
                        Dir.left -> Dir.up
                        Dir.down -> Dir.right
                    }
                    val new = (curr.vertex to newDir).getNextVertexToDir()

                    new to null
                }

                '-' -> {

                    var splitPoint: VertexToDir? = null
                    val newDir = when (curr.dir) {
                        Dir.left, Dir.right -> curr.dir
                        Dir.down, Dir.up -> {
                            splitPoint = (curr.vertex to Dir.right).getNextVertexToDir()
                            Dir.left
                        }
                    }
                    val new = (curr.vertex to newDir).getNextVertexToDir()

                    new to splitPoint
                }

                '|' -> {
                    var splitPoint: VertexToDir? = null

                    val newDir = when (curr.dir) {
                        Dir.down, Dir.up -> curr.dir
                        Dir.left, Dir.right -> {
                            splitPoint = (curr.vertex to Dir.down).getNextVertexToDir()
                            Dir.up
                        }
                    }
                    val new = (curr.vertex to newDir).getNextVertexToDir()

                    new to splitPoint
                }

                else -> error("not supported")
            }

            heads.addFirst(nextPoint)

            if (nextSplitPointOrNull != null)
                heads.addLast(nextSplitPointOrNull)

        }

        return energized.distinctBy { it.vertex }.count()
    }

    private fun VertexToDir.getNextVertexToDir(): VertexToDir {
        val v = vertex
        val dir = dir
        return when (dir) {
            Dir.up -> v.up()
            Dir.down -> v.down()
            Dir.left -> v.left()
            Dir.right -> v.right()
        } to dir
    }

    val input
        get() = readInputAs2DCharArray("Day16", "aoc2023")

    val testInput
        get() = readInputAs2DCharArray("Day16_test", "aoc2023")

}
