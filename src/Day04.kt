fun main() {
    fun String.splitTwoPairs() = this.split(',').map { onePair ->
        val (start, end) = onePair.split('-').map { it.toInt() }
        start..end
    }

    fun part1(input: List<String>): Int = input.count { line ->

        val (a, b) = line.splitTwoPairs()

        // alternative
//        a.first <= b.first && a.last >= b.last -> count++
//        b.first <= a.first && b.last >= a.last -> count++

        return@count when ((a intersect b).size) {
            a.count(), b.count() -> true
            else -> false
        }
    }

    fun part2(input: List<String>): Int = input.count { line ->
        val (a, b) = line.splitTwoPairs()

        return@count (a intersect b).any()
    }


    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 2)
    check(part2(testInput).also(::println) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
