package aoc2022

import utils.checkEquals

import utils.sendAnswer

private typealias Num = Pair<Int, Long>
private val Num.value get() = second
fun main() {
    fun part1(input: List<String>): Long {
        val original: List<Num> = input.mapIndexed { i, num -> i to num.toLong() }
        val mixingList = original.toMutableList()

        for (num in original) {
            val oldIndex = mixingList.indexOf(num)

            val newIndex = (oldIndex + num.value).mod(mixingList.lastIndex)

            mixingList.removeAt(oldIndex)
            mixingList.add(newIndex, num)
        }

        val zeroIndex = mixingList.indexOfFirst { it.value == 0L }

        return listOf(1000,2000,3000).sumOf { mixingList[((zeroIndex + it) % mixingList.size)].value }
    }

    fun part2(input: List<String>): Long {
        val original : List<Num> = input.mapIndexed { i, num -> i to num.toLong() * 811589153L }
        val mixingList = original.toMutableList()

        repeat(10) {
            for (num in original) {
                val index = mixingList.indexOf(num)
                mixingList.removeAt(index)

                val newIndex = (index + num.value).mod(mixingList.size)
                mixingList.add(newIndex, num)
            }
        }

        val zeroIndex = mixingList.indexOfFirst { it.value == 0L }

        return listOf(1000,2000,3000).sumOf { mixingList[((zeroIndex + it) % mixingList.size)].value }
    }

    // parts execution
    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    part1(testInput).checkEquals(3)
    part1(input)
//        .sendAnswer(part = 1, day = "20", year = 2022)

    part2(testInput).checkEquals(1623178306)
    part2(input)
        .sendAnswer(part = 2, day = "20", year = 2022)
}
