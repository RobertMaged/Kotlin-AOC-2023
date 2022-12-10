import java.util.Collections

fun main() {
    fun List<String>.designEachCycleAddedX() = flatMap { line ->
        val instructionSplit = line.split(" ")

        return@flatMap if (instructionSplit.first() == "noop")
            listOf(0)
        else
            listOf(0, instructionSplit[1].toInt())
    }

    fun part1(input: List<String>): Int = signalStrengths@ buildList<Int> {
        input.designEachCycleAddedX().foldIndexed(1) { index, registerX, add ->
            if (index + 1 in listOf(20, 60, 100, 140, 180, 220))
                add((index + 1) * registerX)

            return@foldIndexed registerX + add
        }
    }.sum()

    fun part2(input: List<String>): String = buildString {
        var spiritMiddleIndex = 1

        input.designEachCycleAddedX().forEachIndexed { index, add ->

            val rowDrawingIndex = (index % 40)
            append(
                // like "i in (-1..1)"
                if (spiritMiddleIndex - 1 <= rowDrawingIndex && rowDrawingIndex <= spiritMiddleIndex + 1)
                    '#'
                else
                    '.'
            )

            if ((index + 1) % 40 == 0)
                appendLine()

            spiritMiddleIndex += add
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput).also(::println) == 13140)
//    check(part2(testInput).also(::println) == part2ExampleResult)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))




    fun part2Variant(input: List<String>): String = buildString {
        val spiritPosition = List(40) { if (it < 3) "#" else "." }.toMutableList()

        input.designEachCycleAddedX().forEachIndexed { index, add ->

            append(spiritPosition[index % 40])

            if ((index + 1) % 40 == 0) {
                appendLine()
            }
            Collections.rotate(spiritPosition, add)
        }
    }
}

private const val part2ExampleResult =
    """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######....."""
