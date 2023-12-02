package utils

import sendResult
import java.math.BigInteger
import java.security.MessageDigest

inline fun Array<CharArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

inline fun Array<IntArray>.cloneOf2D() = this.map { it.clone() }.toTypedArray()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


inline fun <reified T: Any> T.alsoPrintln(): T = this.also(::println)

inline fun <reified T: Any> T.checkEquals(expected: T): T = this.also { check(it == expected){"\nExpected: $expected\nFound: $this"} }
inline fun <reified T: Any> T.checkNotEquals(notExpected: T): T = this.also { check(it != notExpected){"\nThe not expected value is Found: $notExpected\n"} }

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
