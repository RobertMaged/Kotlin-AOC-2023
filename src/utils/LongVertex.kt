package utils

data class LongVertex(val x: Long, val y: Long) {
    constructor(xyPair: Pair<Long, Long>) : this(xyPair.first, xyPair.second)

    infix fun isVerticallyEquals(other: LongVertex) = this.y == other.y
    infix fun isHorizontallyEquals(other: LongVertex) = this.x == other.x

    fun coerceAtLeastVertically(minimumLongVertex: LongVertex): LongVertex =
        if (this.y < minimumLongVertex.y) minimumLongVertex else this

    fun coerceAtMostVertically(maximumLongVertex: LongVertex): LongVertex =
        if (this.y > maximumLongVertex.y) maximumLongVertex else this

    fun coerceAtLeastHorizontally(minimumLongVertex: LongVertex): LongVertex =
        if (this.x < minimumLongVertex.x) minimumLongVertex else this

    fun coerceAtMostHorizontally(maximumLongVertex: LongVertex): LongVertex =
        if (this.x > maximumLongVertex.x) maximumLongVertex else this

    fun isInBounds(rowsR: LongRange, colsR: LongRange): Boolean = x in colsR && y in rowsR

    fun up(steps: Long = 1) = copy(y = y - steps)
    fun down(steps: Long = 1) = copy(y = y + steps)

    fun left(steps: Long = 1) = copy(x = x - steps)
    fun right(steps: Long = 1) = copy(x = x + steps)

    fun topLeft(upSteps: Long = 1, leftSteps: Long = 1) = up(upSteps).left(leftSteps)
    fun topRight(upSteps: Long = 1, rightSteps: Long = 1) = up(upSteps).right(rightSteps)
    fun downLeft(downSteps: Long = 1, leftSteps: Long = 1) = down(downSteps).left(leftSteps)
    fun downRight(downSteps: Long = 1, rightSteps: Long = 1) = down(downSteps).right(rightSteps)

    fun allAdjacent() = setOf(
        up(), topLeft(), topRight(), down(), downLeft(), downRight(), left(), right(),
    )

    fun northAdjacent() = setOf(up(), topLeft(), topRight())
    fun southAdjacent() = setOf(down(), downLeft(), downRight())
    fun westAdjacent() = setOf(left(), topLeft(), downLeft())
    fun eastAdjacent() = setOf(right(), topRight(), downRight())

    companion object {
        fun ofDestructuringStrings(orderedXY: List<String>) =
            LongVertex(orderedXY.first().toLong(), orderedXY[1].toLong())

        fun ofDestructuringLongs(orderedXY: List<Long>) =
            LongVertex(orderedXY.first(), orderedXY[1])
    }
}

