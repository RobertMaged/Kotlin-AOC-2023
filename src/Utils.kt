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

fun readInputAs2DIntList(name: String) = readInput(name)
    .map { line -> line.map { it.digitToInt() } }

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
