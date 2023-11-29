import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, sub: String) = File("src/$sub/resources", "$name.txt")
    .readLines()

fun readInputAsText(name: String, sub: String) = File("src/$sub/resources", "$name.txt")
    .readText()

fun readInputAs2DIntArray(name: String, sub: String) = readInput(name, sub)
    .map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()

fun readInputAs2DCharArray(name: String, sub: String) = readInput(name, sub)
    .map { line -> line.toCharArray()}.toTypedArray()

inline fun Array<CharArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

inline fun Array<IntArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

inline fun Array<IntArray>.getOrDefault(ver: Vertex, defaultValue: Int): Int {
    return this.getOrNull(ver.y)?.getOrNull(ver.x) ?: defaultValue
}

inline operator fun Array<IntArray>.get(ver: Vertex): Int = this[ver.y][ver.x]

inline operator fun Array<IntArray>.set(ver: Vertex, value: Int) {
    this[ver.y][ver.x] = value
}

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

data class Vertex(val x: Int, val y: Int) {
    constructor(xyPair: Pair<Int, Int>) : this(xyPair.first, xyPair.second)

    infix fun isVerticallyEquals(other: Vertex) = this.y == other.y
    infix fun isHorizontallyEquals(other: Vertex) = this.x == other.x

    fun coerceAtLeastVertically(minimumVertex: Vertex): Vertex =
        if (this.y < minimumVertex.y) minimumVertex else this

    fun coerceAtMostVertically(maximumVertex: Vertex): Vertex =
        if (this.y > maximumVertex.y) maximumVertex else this

    fun coerceAtLeastHorizontally(minimumVertex: Vertex): Vertex =
        if (this.x < minimumVertex.x) minimumVertex else this

    fun coerceAtMostHorizontally(maximumVertex: Vertex): Vertex =
        if (this.x > maximumVertex.x) maximumVertex else this

    fun up(steps: Int = 1) = copy(y = y - steps)
    fun down(steps: Int = 1) = copy(y = y + steps)

    fun left(steps: Int = 1) = copy(x = x - steps)
    fun right(steps: Int = 1) = copy(x = x + steps)

    fun topLeft(upSteps: Int = 1, leftSteps: Int = 1) = up(upSteps).left(leftSteps)
    fun topRight(upSteps: Int = 1, rightSteps: Int = 1) = up(upSteps).right(rightSteps)
    fun downLeft(downSteps: Int = 1, leftSteps: Int = 1) = down(downSteps).left(leftSteps)
    fun downRight(downSteps: Int = 1, rightSteps: Int = 1) = down(downSteps).right(rightSteps)

    fun allAdjacent() = setOf(
        up(), topLeft(), topRight(), down(), downLeft(), downRight(), left(), right(),
    )

    fun northAdjacent() = setOf(up(), topLeft(), topRight())
    fun southAdjacent() = setOf(down(), downLeft(), downRight())
    fun westAdjacent() = setOf(left(), topLeft(), downLeft())
    fun eastAdjacent() = setOf(right(), topRight(), downRight())

    companion object {
        fun ofDestructuringStrings(orderedXY: List<String>) =
            Vertex(orderedXY.first().toInt(), orderedXY[1].toInt())

        fun ofDestructuringInts(orderedXY: List<Int>) =
            Vertex(orderedXY.first(), orderedXY[1])
    }
}


inline fun <reified T: Any> T.alsoPrintln(): T = this.also(::println)

fun String.checkEquals(expected: String): String = this.also { check(it == expected){"\nExpected: $expected\n Found: $this"} }

inline fun <reified T: Number> T.checkEquals(expected: T): T = this.also { check(it == expected){"\nExpected: $expected\nFound: $this"} }
inline fun <reified T: Number> T.checkNotEquals(notExpected: T): T = this.also { check(it != notExpected){"\nThe not expected value is Found: $notExpected\n"} }

fun String.sendAnswer(part: Int, day:String, year: Int) = sendResult(
    day = day,
    part = part,
    answer = this,
    year = year
)
inline fun <reified T: Number> T.sendAnswer(part: Int, day:String, year: Int) = sendResult(
    day = day,
    part = part,
    answer = this.toString(),
    year = year
)
