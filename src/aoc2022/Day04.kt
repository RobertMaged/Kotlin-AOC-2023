package aoc2022



fun main() {
    fun String.splitElves() = this.split(',').map { elf ->
        val (startSection, endSection) = elf.split('-').map { it.toInt() }
        startSection..endSection
    }

    fun part1(input: List<String>): Int = input.count { assignmentPair ->

        val (firstElf, secondElf) = assignmentPair.splitElves()

        return@count when ((firstElf intersect secondElf).size) {
            firstElf.count(), secondElf.count() -> true
            else -> false
        }
    }

    fun part2(input: List<String>): Int = input.count { line ->
        val (firstElf, secondElf) = line.splitElves()

        return@count (firstElf intersect secondElf).any()
    }


    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 2)
    check(part2(testInput).also(::println) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

// part one alternative
//        firstElf.first <= secondElf.first && firstElf.last >= secondElf.last -> count++
//        secondElf.first <= firstElf.first && secondElf.last >= firstElf.last -> count++