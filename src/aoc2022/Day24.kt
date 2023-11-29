package aoc2022

import utils.Vertex
import utils.checkEquals


private enum class Dir { UP, DOWN, LEFT, RIGHT }
private typealias Tracking = Pair<Int, Vertex>

private val Tracking.minutes get() = first
private val Tracking.pos get() = second

private data class Blizzard(private var _pos: Vertex, val dir: Dir, val boundsRange: IntRange) {
    val pos: Vertex
        get() = _pos

    fun moveAndGet(steps: Int = 1): Vertex {
        _pos = when (dir) {
            Dir.UP -> if (pos.up(steps).y in boundsRange) pos.up(steps) else pos.copy(y = boundsRange.last)

            Dir.DOWN -> if (pos.down(steps).y in boundsRange) pos.down(steps) else pos.copy(y = 1)

            Dir.LEFT -> if (pos.left(steps).x in boundsRange) pos.left(steps) else pos.copy(x = boundsRange.last)

            Dir.RIGHT -> if (pos.right(steps).x in boundsRange) pos.right(steps) else pos.copy(x = 1)
        }
        return _pos
    }
}

fun main() {

    fun List<String>.goToExtractionPoint(directedScanTimes: Int = 1): Int {

        val start = this.first().indexOfFirst { it == '.' }.let { Vertex(x = it, y = 0) }
        val target = this.last().indexOfFirst { it == '.' }.let { Vertex(x = it, y = this.lastIndex) }

        val xBounds = 1..this[1].trim('#').length
        val yBounds = 1..this.drop(1).dropLast(1).size

        val blizzards = this.flatMapIndexed { i, s ->
            s.mapIndexedNotNull { j, c ->
                val (dir, bounds) = when (c) {
                    '>' -> Dir.RIGHT to xBounds
                    '<' -> Dir.LEFT to xBounds
                    'v' -> Dir.DOWN to yBounds
                    '^' -> Dir.UP to yBounds

                    else -> return@mapIndexedNotNull null
                }

                return@mapIndexedNotNull Blizzard(_pos = Vertex(x = j, y = i), dir = dir, boundsRange = bounds)
            }
        }

        val runningReduceMinutes = mutableListOf(0 to start)

        repeat(directedScanTimes) {

            val queue = ArrayDeque<Tracking>(listOf(runningReduceMinutes.last()))
            val seen = hashSetOf<Tracking>()
            val movesHistory = hashMapOf(0 to blizzards.map { it.pos }.toSet())

            while (queue.any()) {
                val expedition = queue.removeFirst()
                if (expedition in seen) continue
                seen += expedition

                val next = movesHistory.computeIfAbsent(expedition.minutes + 1) {
                    blizzards.map { it.moveAndGet() }.toSet()
                }

                val neighbors = with(expedition.pos) { setOf(this@with, down(), right(), left(), up()) }

                //only needed for second part, else its always target
                val des = if (runningReduceMinutes.last().pos == start) target else start
                if (des in neighbors) {
                    runningReduceMinutes.add(expedition.minutes + 1 to des)
                    break
                }

                neighbors.filter {
                    it == runningReduceMinutes.last().pos ||
                            (it.x in xBounds && it.y in yBounds && it !in next)
                }
                    .map { expedition.minutes + 1 to it }.also(queue::addAll)
            }

        }

        return runningReduceMinutes.last().minutes
    }


    fun part1(input: List<String>) = input.goToExtractionPoint()

    fun part2(input: List<String>) = input.goToExtractionPoint(directedScanTimes = 3)


    // parts execution
    val testInput = readInput("Day24_test")
    val input = readInput("Day24")

    part1(testInput).checkEquals(18)
    part1(input)
        .checkEquals(225)
//        .sendAnswer(part = 1, day = "24", year = 2022)

    part2(testInput).checkEquals(54)
    part2(input)
        .checkEquals(711)
//        .sendAnswer(part = 2, day = "24", year = 2022)
}

