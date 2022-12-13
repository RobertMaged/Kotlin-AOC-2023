import resources.data
import resources.data2

fun main() {
    data class NCont(val nums: List<Int>, val subCon: NCont? = null)

    fun look(first: Any, second: Any): Int = when {
        first is Int && second is Int -> first - second

        first is List<*> && second is List<*> -> first.zip(second)
            .firstOrNull { pair -> look(pair.first!!, pair.second!!) != 0 }
            ?.let { look(it.first!!, it.second!!) } ?: (first.size - second.size)

        first is List<*> -> look(first, listOf(second))

        second is List<*> -> look(listOf(first), second)

        else -> error("Not accepted input")
    }


    fun part1(input: List<String>): Int {

        return (data + data2).chunked(2).mapIndexed { index, (a, b) -> if (look(a, b) < 0) index + 1 else 0 }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day13_test").take(23)
    val input = readInput("Day13")

//    part1(testInput).checkEquals(13)
    part1(input)
        .sendAnswer(part = 1, day = "13", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "13", year = 2022)
}
