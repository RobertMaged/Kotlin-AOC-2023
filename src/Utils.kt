import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/resources", "$name.txt")
    .readLines()

fun readInputAsText(name: String) = File("src/resources", "$name.txt")
    .readText()

fun readInputAs2DIntArray(name: String) = readInput(name)
    .map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()
inline fun Array<IntArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

fun readInputAs2DCharArray(name: String) = readInput(name)
    .map { line -> line.toCharArray()}.toTypedArray()

inline fun Array<CharArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

inline fun <reified T: Any> T.alsoPrintln(): T = this.also(::println)

fun String.checkEquals(expected: String): String = this.also { check(it == expected){"\nExpected: $expected\n Found: $this"} }

inline fun <reified T: Number> T.checkEquals(expected: T): T = this.also { check(it == expected){"\nExpected: $expected\nFound: $this"} }
//    sendResult(answer = p2.toString(), part = 2, day = "12", year = 2022)

fun String.sendAnswer(part: Int, day:String, year: Int = 2022) = sendResult(
    day = day,
    part = part,
    answer = this,
    year = year
)
inline fun <reified T: Number> T.sendAnswer(part: Int, day:String, year: Int = 2022) = sendResult(
    day = day,
    part = part,
    answer = this.toString(),
    year = year
)
