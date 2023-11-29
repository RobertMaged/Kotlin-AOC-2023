package aoc2022

import Vertex
import checkEquals


private typealias Elf = Vertex

fun main() {
    
    fun part1(input: List<String>): Int {
        return input.locateElvas().spreadOut(rounds = 10)
    }

    fun part2(input: List<String>): Int {
        var firstNoMoveRound = 0
        input.locateElvas().spreadOut(rounds = Int.MAX_VALUE) { firstNoMoveRound = it }

        return firstNoMoveRound
    }

    // parts execution
    val testInput = readInput("Day23_test")
    val input = readInput("Day23")

    part1(testInput).checkEquals(110)
    part1(input)
        .checkEquals(3874)
//        .sendAnswer(part = 1, day = "23", year = 2022)

    part2(testInput).checkEquals(20)
    part2(input)
        .checkEquals(948)
//        .sendAnswer(part = 2, day = "23", year = 2022)
}

private fun List<String>.locateElvas() = flatMapIndexed { index, s ->
    s.mapIndexedNotNull { i, c ->
        if (c == '.')
            null
        else
            aoc2022.Elf(x = i, y = index)
    }
}.toHashSet()


private fun HashSet<Elf>.spreadOut(rounds: Int, onNoMoves: ((round: Int) -> Unit)? = null): Int {
    val elves: HashSet<Elf> = this

    val validDirections = ArrayDeque(listOf(
        { e: Elf -> e.northAdjacent() to e.up() },
        { e: Elf -> e.southAdjacent() to e.down() },
        { e: Elf -> e.westAdjacent() to e.left() },
        { e: Elf -> e.eastAdjacent() to e.right() }
    ))

    var round = 1
    while (round <= rounds) {
        //first half
        val movingAbility = elves.filter { elf ->
            elf.allAdjacent().any { it in elves }
        }

        if (movingAbility.isEmpty()) break

        round++

        val proposedElvasMoves: Map<Elf, List<Elf>> = movingAbility.mapNotNull { elf ->

            return@mapNotNull validDirections.firstNotNullOfOrNull { consideringDirection ->
                val (adjacent, destinationDir) = consideringDirection.invoke(elf)

                (elf to destinationDir).takeIf { adjacent.none { it in elves } }
            }
        }
            .groupBy { (_, destination) -> destination }
            .mapValues { (_, elvasWithSameDes) -> elvasWithSameDes.map { it.first } }


        // second half
        proposedElvasMoves.filter { it.value.singleOrNull() != null }.forEach {(desElfPos, currElfPos) ->
            elves.remove(currElfPos.single())
            elves.add(desElfPos)
        }

        validDirections.addLast(validDirections.removeFirst())
    }

    // no need for tiles counts on second part
    if (onNoMoves != null) {
        onNoMoves(round)
        return -1
    }

    return elves.countTiles()
}

private fun Set<Elf>.countTiles(): Int {
    val mostLeft = minOf { it.x }
    val mostRight = maxOf { it.x }
    val mostTop = minOf { it.y }
    val mostDown = maxOf { it.y }

    return (mostTop..mostDown).sumOf { row ->
        (mostLeft..mostRight).count { col ->
            aoc2022.Elf(x = col, y = row) !in this
        }
    }
}