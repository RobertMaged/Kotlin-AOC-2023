package utils

import java.io.File

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