package aoc2023

import utils.*

fun main(): Unit = with(Day10) {

    part1(testInput.take(5).toTypedArray()).checkEquals(4)
    part1(testInput.drop(6).toTypedArray()).checkEquals(8)
    part1(input)
        .checkEquals(6956)
//        .sendAnswer(part = 1, day = "10", year = 2023)


    part2(testInput.copyOfRange(12, 21)).checkEquals(4)
    part2(testInput.copyOfRange(22, 32)).checkEquals(8)
    part2(testInput.drop(33).toTypedArray()).checkEquals(10)
    part2(input)
        .alsoPrintln()
        .sendAnswer(part = 2, day = "10", year = 2023)
}


object Day10 {

    operator fun Array<CharArray>.get(s: Vertex): Char = getOrNull(s.y)?.getOrNull(s.x) ?: '#'

    fun part1(area: Array<CharArray>): Int {

        var sIndex: Vertex? = null
        find@ for (i in area.indices)
            for (j in area[i].indices)
                if (area[i][j] == 'S') {
                    sIndex = Vertex(y = i, x = j)
                    break@find
                }

        val pips = ArrayDeque<Vertex>(listOf(sIndex!!))
        val walked = mutableSetOf<Vertex>()
        var count = 0

        while (pips.any()) {
            val curr = pips.removeFirst()

            if (curr in walked)
                continue

            walked.add(curr)

            count++
            val neighbours = mutableListOf<Vertex>()
            when (area[curr]) {
                'S' -> {
                    curr.up().takeIf { area[it] in "|F7" }?.apply(neighbours::add)
                    curr.down().takeIf { area[it] in "|JL" }?.apply(neighbours::add)
                    curr.right().takeIf { area[it] in "-J7" }?.apply(neighbours::add)
                    curr.left().takeIf { area[it] in "-FL" }?.apply(neighbours::add)
                }

                '|' -> {
                    curr.up().takeIf { area[it] in "|F7" }?.apply(neighbours::add)
                    curr.down().takeIf { area[it] in "|JL" }?.apply(neighbours::add)
                }

                '-' -> {
                    curr.right().takeIf { area[it] in "-7J" }?.apply(neighbours::add)
                    curr.left().takeIf { area[it] in "-FL" }?.apply(neighbours::add)
                }

                '7' -> {
                    curr.left().takeIf { area[it] in "-LF" }?.apply(neighbours::add)
                    curr.down().takeIf { area[it] in "|LJ" }?.apply(neighbours::add)
                }

                'F' -> {
                    curr.right().takeIf { area[it] in "-7J" }?.apply(neighbours::add)
                    curr.down().takeIf { area[it] in "|LJ" }?.apply(neighbours::add)
                }

                'L' -> {
                    curr.up().takeIf { area[it] in "|F7" }?.apply(neighbours::add)
                    curr.right().takeIf { area[it] in "-J7" }?.apply(neighbours::add)
                }

                'J' -> {
                    curr.up().takeIf { area[it] in "|F7" }?.apply(neighbours::add)
                    curr.left().takeIf { area[it] in "-FL" }?.apply(neighbours::add)
                }

                else -> error("")

            }


            neighbours.forEach(pips::addLast)
        }

        // since it's a square loop,
        return count / 2
    }

    fun part2(input: Array<CharArray>): Int = 0

    val input
        get() = readInputAs2DCharArray("Day10", "aoc2023")

    val testInput
        get() = readInputAs2DCharArray("Day10_test", "aoc2023")

}
