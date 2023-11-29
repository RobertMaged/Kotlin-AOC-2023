package aoc2022

import checkEquals

import sendAnswer

fun main() {
    data class Valve(val name :String,val rate:Int, val que : Set<String>, var isOpen :Boolean = false)

    fun part1(input: List<String>): Int {
        var score = 0
        var minutes = 31
        val valves = mutableSetOf<Valve>()
        for (line in input){
            val name = line.substringAfter("Valve ").takeWhile { it.isUpperCase() }
            val rate = line.substringAfter("rate=").takeWhile { it.isDigit() }.toInt()
            val leadTunnels = line.substringAfter(';').dropWhile { !it.isUpperCase() }.split(", ").sorted()
            valves += Valve(name, rate, leadTunnels.toSet())
        }
        val map = valves.associateBy { it.name }
        val graph = valves.associate { it.name to it.que.toSet() }

        val visted = mutableSetOf<String>()
        val stack = ArrayDeque<String>(listOf("AA"))
        val rateToMin = mutableListOf<Pair<Int, Int>>()
        val pro = mutableListOf<Int>()

        var currValve = map["AA"]!!

        val knownValues = mutableListOf<Valve>()

        while (minutes >0 && stack.any()){

        }


        return pro.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    part1(testInput).checkEquals(1651)
    part1(input)
        .sendAnswer(part = 1, day = "16", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "16", year = 2022)
}
