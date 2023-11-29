package aoc2022

import utils.checkEquals


object Day21 {
    fun List<String>.graphYelling(): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        this.forEach {
            val (name, yell) = it.split(": ")
            map[name] = yell
        }
        return map
    }

    fun HashMap<String, String>.yell(name: String): Long {
        val yell = getValue(name)

        if (yell.toLongOrNull() != null) return yell.toLong()

        val (first, op, second) = yell.split(' ')

        val result = op.eval(yell(first), yell(second))
        put(name, "$result")

        return getValue(name).toLong()
    }

    fun part1(input: List<String>): Long {
        return input.graphYelling().yell("root")
    }


    fun part2(input: List<String>): Long {
        val original = input.graphYelling()
        original["root"] = original.getValue("root").replace('+', '=')


        var minHumn = 1L
        var maxHumn = 100000000000000
        var humn: Long

        do {
            val graph = HashMap(original)
            humn = (maxHumn + minHumn) / 2

            graph["humn"] = "$humn"

            when (graph.yell("root")) {
                -1L -> maxHumn =humn - 1
                1L -> minHumn = humn + 1
//                0L -> break
            }

        } while (graph["root"]!!.toInt() != 0)

        return humn
    }


    fun HashMap<String, String>.yell(): Long = DeepRecursiveFunction { name ->
        val yell = getValue(name)

        if (yell.toLongOrNull() != null) return@DeepRecursiveFunction yell.toLong()

        val (first, op, second) = yell.split(' ')

        val result = op.eval(callRecursive(first), callRecursive(second))

        put(name, "$result")
        return@DeepRecursiveFunction result
    }
        .invoke("root")


    private fun String.eval(first: Long, second: Long): Long = when (this.single()) {
        '+' -> first + second
        '-' -> first - second
        '*' -> first * second
        '/' -> first / second
        '=' -> first.compareTo(second).toLong()
        else -> error("unsupported operation")
    }

    val testInput = readInput("Day21_test")
    val input = readInput("Day21")
}

fun main(): Unit = with(Day21) {

    part1(testInput).checkEquals(152)
    part1(input).checkEquals(256997859093114)
//        .sendAnswer(part = 1, day = "21", year = 2022)

//    part2(testInput).checkEquals(301)
    part2(input).checkEquals(3952288690726)
//        .sendAnswer(part = 2, day = "21", year = 2022)
}
