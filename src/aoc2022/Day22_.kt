package aoc2022

import utils.Vertex
import utils.checkEquals


private const val EMPTY = -1
private const val WALL = 1
private const val TILE = 0
private const val RIGHT = 0
private const val DOWN = 1
private const val LEFT = 2
private const val UP = 3
fun main() {
    fun part1(input: List<String>): Int {
        val instructions = ArrayDeque<Int>()
        Regex("\\d+|[RL]").findAll(input.first()).map {
            it.value.toIntOrNull()
                ?: it.value.single().let { if (it == 'R') 1 else -1 }
        }.toCollection(instructions)

        val colN = input.drop(1).maxOf { it.length }
        val rowN = input.drop(1).size

        val mapBoard = Array(rowN) { i ->
            IntArray(colN) { j ->
                when (input[i + 1].getOrElse(j, { ' ' })) {
                    ' ' -> EMPTY
                    '#' -> WALL
                    '.' -> TILE
                    else -> error("unsupported ${input[i + 1][j]}")
                }
            }
        }

        val pacman = Pacman(y = 0, x = mapBoard[0].indexOfFirst { it == TILE })


        while (instructions.any()) {
            sd@ for (i in 0 until instructions.removeFirst()) {
                when (pacman.facing) {
                    RIGHT -> {
                        val next = mapBoard[pacman.y].getOrElse(pacman.x + 1, { EMPTY })
                        when {
                            next == TILE -> pacman.x++
                            next == WALL -> continue@sd
                            next == EMPTY && mapBoard[pacman.y].first { it != EMPTY } == TILE -> pacman.x =
                                mapBoard[pacman.y].indexOfFirst { it != EMPTY }
                        }
                    }

                    LEFT -> {
                        val previous = mapBoard[pacman.y].getOrElse(pacman.x - 1, { EMPTY })
                        when {
                            previous == TILE -> pacman.x--
                            previous == WALL -> continue@sd
                            previous == EMPTY && mapBoard[pacman.y].last { it != EMPTY } == TILE -> pacman.x =
                                mapBoard[pacman.y].indexOfLast { it != EMPTY }
                        }
                    }

                    DOWN -> {
                        val after = mapBoard.getOrNull(pacman.y + 1)?.get(pacman.x) ?: EMPTY
                        when {
                            after == TILE -> pacman.y++
                            after == WALL -> continue@sd
                            after == EMPTY && mapBoard.first { it[pacman.x] != EMPTY }[pacman.x] == TILE -> pacman.y =
                                mapBoard.indexOfFirst { it[pacman.x] != EMPTY }
                        }
                    }

                    UP -> {
                        val before = mapBoard.getOrNull(pacman.y - 1)?.get(pacman.x) ?: EMPTY
                        when {
                            before == TILE -> pacman.y--
                            before == WALL -> continue@sd
                            before == EMPTY && mapBoard.last { it[pacman.x] != EMPTY }[pacman.x] == TILE -> pacman.y =
                                mapBoard.indexOfLast { it[pacman.x] != EMPTY }
                        }
                    }
                }
            }

            instructions.removeFirstOrNull()?.also(pacman::rotate)
        }

        return (((pacman.y + 1) * 1000) + ((pacman.x + 1) * 4) + pacman.facing)
    }

    fun part2(input: List<String>, cubeSize: Int = 4): Int {
        val instructions = ArrayDeque<Int>()
        Regex("\\d+|[RL]").findAll(input.first()).map {
            it.value.toIntOrNull()
                ?: it.value.single().let { if (it == 'R') 1 else -1 }
        }.toCollection(instructions)

        val colN = input.drop(1).maxOf { it.length }
        val rowN = input.drop(1).size

        val mapBoard = Array(rowN) { i ->
            IntArray(colN) { j ->
                when (input[i + 1].getOrElse(j, { ' ' })) {
                    ' ' -> EMPTY
                    '#' -> WALL
                    '.' -> TILE
                    else -> error("unsupported ${input[i + 1][j]}")
                }
            }
        }

        var turn = 0
        val sides = mapBoard.toList().chunked(cubeSize).flatMapIndexed { yI, yIt ->
            val yRange = yI * cubeSize until yI * cubeSize + cubeSize
            yIt.random().toList().chunked(cubeSize)
                .mapIndexedNotNull { i, it -> if (it.all { it == EMPTY }) null else i to it }
                .map { (i, it) ->
                    val xRange = i * cubeSize until i * cubeSize + cubeSize
                    CubeSide(++turn, yRange, xRange, yRange.mapNotNull {y-> if (!mapBoard[y].contains(WALL))null else xRange.mapNotNull { if(mapBoard[y][it] == WALL) Vertex(it, y) else null } }.flatten().toSet())
                }
        }
            .let {
                val y = 0
                it
            }
        sides.foldCubeMap()


//        val pacman = Pacman(y = 0, x = mapBoard[0].indexOfFirst { it == TILE })
        val pacman = Pacman(y = sides.single { it.sideN == 1 }.rows.first, x = sides.single { it.sideN == 1 }.cols.first)


        while (instructions.any()) {
            sd@ for (i in 0 until instructions.removeFirst()) {
                val currSide = sides.single { pacman.y in it.rows && pacman.x in it.cols }
                when (pacman.facing) {
                    RIGHT -> {
                        when {
                            pacman.x +1 in currSide.cols -> {
                                if(pacman.toVertex() !in currSide.walls) pacman.x++
                                else continue@sd
                            }
                            with(currSide.neighbors.right) {
                                Vertex(
                                    cols.first,
                                    rows.elementAt(currSide.rows.indexOf(pacman.y))
                                ) !in walls } -> {
                                pacman.x = currSide.neighbors.right.cols.first

                                pacman.y = currSide.neighbors.right.rows.elementAt(currSide.rows.indexOf(pacman.y))
                                pacman.facing = currSide.movingFace(currSide.neighbors.right)
                            }
                        }
                    }

                    LEFT -> {
                        when {
                            pacman.x -1 in currSide.cols -> {
                                if(pacman.toVertex() !in currSide.walls) pacman.x--
                                else continue@sd
                            }
                            with(currSide.neighbors.left) {
                                Vertex(
                                    cols.last,
                                    rows.elementAt(currSide.rows.indexOf(pacman.y))
                                ) !in walls } -> {
                                pacman.x = currSide.neighbors.left.cols.last

                                pacman.y = currSide.neighbors.left.rows.elementAt(currSide.rows.indexOf(pacman.y))
                                pacman.facing = currSide.movingFace(currSide.neighbors.left)
                            }
                        }
                    }

                    DOWN -> {
                        when {
                            pacman.y + 1 in currSide.rows -> {
                                if(pacman.toVertex() !in currSide.walls) pacman.y++
                                else continue@sd
                            }
                            with(currSide.neighbors.down) {
                                Vertex(cols.elementAt(currSide.cols.indexOf(pacman.x)), rows.first) !in walls
                            } -> {
                                pacman.y = currSide.neighbors.down.rows.first

                                pacman.x = currSide.neighbors.down.cols.elementAt(currSide.cols.indexOf(pacman.x))
                                pacman.facing = currSide.movingFace(currSide.neighbors.down)
                            }
                        }
                    }

                    UP -> {
                        when {
                            pacman.y - 1 in currSide.rows -> {
                                if(pacman.toVertex() !in currSide.walls) pacman.y--
                                else continue@sd
                            }
                            with(currSide.neighbors.up) {
                                Vertex(
                                    cols.elementAt(currSide.cols.indexOf(pacman.x)),
                                    rows.last
                                ) !in walls } -> {
                                pacman.y = currSide.neighbors.up.rows.last

                                pacman.x = currSide.neighbors.up.cols.elementAt(currSide.cols.indexOf(pacman.x))
                                pacman.facing = currSide.movingFace(currSide.neighbors.up)
                            }
                        }
                    }
                }
            }

            instructions.removeFirstOrNull()?.also(pacman::rotate)
        }

        return (((pacman.y + 1) * 1000) + ((pacman.x + 1) * 4) + pacman.facing)
    }


    // parts execution
    val testInput = readInput("Day22_test").takeWhile { it.isNotBlank() }
    val input = readInput("Day22")

    part1(testInput).checkEquals(6032)
    part1(input)
        .checkEquals(197160)
//        .sendAnswer(part = 1, day = "22", year = 2022)

    part2(testInput, 4).checkEquals(5031)
    part2(input, 50)
        .checkEquals(145065)
//        .sendAnswer(part = 2, day = "22", year = 2022)
}

private data class CubeSide(val sideN: Int, val rows: IntRange, val cols: IntRange, val walls: Set<Vertex> ) {
    lateinit var neighbors: Neighbors
    lateinit var pov: POV

    data class Neighbors(
        val up: CubeSide,
        val down: CubeSide,
        val left: CubeSide,
        val right: CubeSide,
    ){
        operator fun contains(cubeSide: CubeSide): Boolean {
            return cubeSide in listOf(up, down, left, right)
        }
    }

    fun movingFace(c: CubeSide): Int = when (c) {
        neighbors.right -> RIGHT
        neighbors.left -> LEFT
        neighbors.up -> UP
        neighbors.down -> DOWN
        else -> error("$c is not a neighbor to $this")
    }

    enum class POV { UP, DOWN, RIGHT, LEFT, FRONT, BACK }
}
private typealias POV = CubeSide.POV

private data class Pacman(var x: Int, var y: Int, var facing: Int = RIGHT) {
    constructor(xyPair: Pair<Int, Int>) : this(xyPair.first, xyPair.second)
fun toVertex(): Vertex = Vertex(x = x, y = y)
    fun rotate(degree: Int) {
        require(degree == -1 || degree == 1)
        facing = (facing + degree).mod(4)
    }
    /*
        fun rotate(dir: Int): Unit = when (dir) {
            RIGHT -> facing = when (facing) {
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
                UP -> RIGHT
                else -> error("wrong dir")
            }

            LEFT -> facing = when (facing) {
                RIGHT -> UP
                UP -> LEFT
                LEFT -> DOWN
                DOWN -> RIGHT
                else -> error("wrong dir")
            }

            else -> error("rotation only in Left or right")
        }
    */

    infix fun isVerticallyEquals(other: Pacman) = this.y == other.y
    infix fun isHorizontallyEquals(other: Pacman) = this.x == other.x

    fun coerceAtLeastVertically(minimumVertex: Pacman): Pacman =
        if (this.y < minimumVertex.y) minimumVertex else this

    fun coerceAtMostVertically(maximumVertex: Pacman): Pacman =
        if (this.y > maximumVertex.y) maximumVertex else this

    fun coerceAtLeastHorizontally(minimumVertex: Pacman): Pacman =
        if (this.x < minimumVertex.x) minimumVertex else this

    fun coerceAtMostHorizontally(maximumVertex: Pacman): Pacman =
        if (this.x > maximumVertex.x) maximumVertex else this

    fun up(steps: Int = 1) = copy(y = y - steps)
    fun down(steps: Int = 1) = copy(y = y + steps)

    fun left(steps: Int = 1) = copy(x = x - steps)
    fun right(steps: Int = 1) = copy(x = x + steps)

    fun topLeft(upSteps: Int = 1, leftSteps: Int = 1) = up(upSteps).left(leftSteps)
    fun topRight(upSteps: Int = 1, rightSteps: Int = 1) = up(upSteps).right(rightSteps)
    fun downLeft(downSteps: Int = 1, leftSteps: Int = 1) = down(downSteps).left(leftSteps)
    fun downRight(downSteps: Int = 1, rightSteps: Int = 1) = down(downSteps).right(rightSteps)

    companion object {
        fun ofDestructuringStrings(orderedXY: List<String>) =
            Pacman(orderedXY.first().toInt(), orderedXY[1].toInt())

        fun ofDestructuringInts(orderedXY: List<Int>) =
            Pacman(orderedXY.first(), orderedXY[1])
    }
}

private fun List<CubeSide>.foldCubeMap() {
    val cubeSize = random().cols.count()
    val idn = associateBy { it.sideN }

    when (cubeSize) {
        4 -> forEach {
            val (pov, neighbors) = when (it.sideN) {
                4 -> {
                    CubeSide.POV.UP to
                            CubeSide.Neighbors(right = idn[6]!!, left = idn[3]!!, down = idn[1]!!, up = idn[5]!!)
                }

                1 -> {
                    CubeSide.POV.FRONT to
                            CubeSide.Neighbors(right = idn[6]!!, left = idn[3]!!, down = idn[2]!!, up = idn[4]!!)
                }

                2 -> {
                    CubeSide.POV.DOWN to
                            CubeSide.Neighbors(right = idn[6]!!, left = idn[3]!!, down = idn[5]!!, up = idn[1]!!)
                }

                5 -> {
                    CubeSide.POV.BACK to
                            CubeSide.Neighbors(right = idn[6]!!, left = idn[3]!!, down = idn[2]!!, up = idn[4]!!)
                }

                3 -> {
                    CubeSide.POV.LEFT to
                            CubeSide.Neighbors(right = idn[1]!!, left = idn[5]!!, down = idn[2]!!, up = idn[4]!!)
                }

                6 -> {
                    CubeSide.POV.RIGHT to
                            CubeSide.Neighbors(right = idn[5]!!, left = idn[1]!!, down = idn[2]!!, up = idn[4]!!)
                }


                else -> error("")
            }
            it.pov = pov
            it.neighbors = neighbors
        }

        50 -> forEach {
            val (pov, neighbors) = when (it.sideN) {
                4 -> {
                    CubeSide.POV.RIGHT to
                            CubeSide.Neighbors(right = idn[5]!!, left = idn[1]!!, down = idn[6]!!, up = idn[3]!!)
                }

                1 -> {
                    CubeSide.POV.FRONT to
                            CubeSide.Neighbors(right = idn[4]!!, left = idn[2]!!, down = idn[6]!!, up = idn[3]!!)
                }

                2 -> {
                    CubeSide.POV.LEFT to
                            CubeSide.Neighbors(right = idn[1]!!, left = idn[5]!!, down = idn[6]!!, up = idn[3]!!)
                }

                5 -> {
                    CubeSide.POV.BACK to
                            CubeSide.Neighbors(right = idn[2]!!, left = idn[4]!!, down = idn[6]!!, up = idn[3]!!)
                }

                3 -> {
                    CubeSide.POV.UP to
                            CubeSide.Neighbors(right = idn[4]!!, left = idn[2]!!, down = idn[1]!!, up = idn[5]!!)
                }

                6 -> {
                    CubeSide.POV.DOWN to
                            CubeSide.Neighbors(right = idn[4]!!, left = idn[3]!!, down = idn[5]!!, up = idn[1]!!)
                }


                else -> error("")
            }
            it.pov = pov
            it.neighbors = neighbors
        }

        else -> error("not supported size")
    }
}
