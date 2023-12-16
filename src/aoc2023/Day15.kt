package aoc2023

import utils.checkEquals
import utils.readInput

fun main(): Unit = with(Day15) {

    part1(testInput.first()).checkEquals(52)
    part1(testInput[1]).checkEquals(1320)
    part1(input.single())
        .checkEquals(516657)
//        .sendAnswer(part = 1, day = "15", year = 2023)

    part2(testInput[1]).checkEquals(145)
    part2(input.single())
        .checkEquals(210906)
//        .sendAnswer(part = 2, day = "15", year = 2023)
}

object Day15 {

    fun part1(input: String): Int = input
        .split(',')
        .sumOf { it.getHash() }


    fun part2(input: String): Int {

        val regex = "(\\w+)([=-])(\\d*)".toRegex()
        val boxes = Array(256) { mutableListOf<Pair<String, Int>>() }

        for (it in input.split(',')) {
            val (label, operation, focalLengthOrBlank) = regex.matchEntire(it)!!.destructured

            val box = label.getHash()
            val slots = boxes[box]

            val slotIndexOrNull = slots.indexOfFirst { it.first == label }.takeUnless { it == -1 }

            when {
                operation == "-" && slotIndexOrNull != null -> slots.removeAt(slotIndexOrNull)

                operation == "=" -> with(label to focalLengthOrBlank.toInt()) {
                    if (slotIndexOrNull == null)
                        slots.add(this)
                    else
                        slots[slotIndexOrNull] = this
                }

            }
        }

        return boxes.flatMapIndexed { boxIndex, slots: List<Pair<String, Int>> ->
            slots.mapIndexed { slotIndex, slot ->
                (boxIndex + 1) * (slotIndex + 1) * slot.second
            }
        }.sum()

    }

    private fun String.getHash() = fold(0) { acc, c ->
        (acc + c.code) * 17 % 256
    }

    val input
        get() = readInput("Day15", "aoc2023")

    val testInput
        get() = readInput("Day15_test", "aoc2023")

}
