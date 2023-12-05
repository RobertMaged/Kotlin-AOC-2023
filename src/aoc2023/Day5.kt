package aoc2023

import utils.checkEquals
import utils.readInputAsText

fun main(): Unit = with(Day5) {

    part1(testInput).checkEquals(35)
    part1(input)
        .checkEquals(84470622)
//        .sendAnswer(part = 1, day = "5", year = 2023)

    part2(testInput).checkEquals(46)
    part2(input)
        .checkEquals(26714516)
//        .sendAnswer(part = 2, day = "5", year = 2023)
}

object Day5 {

    data class PlantMap(
        val sourceRange: LongRange,
        val desRange: LongRange,
    ) {
        fun getCorrespondingDestination(currTrack: Long): Long {
            val step = currTrack - sourceRange.first

            return desRange.first + step
        }

    }


    fun part1(input: String): Long {
        val parts = input.split("\n\n")

        val seeds = parts.first().substringAfter("seeds: ").split(' ').map { it.toLong() }

        val seedPlantPath: List<List<PlantMap>> = parts.drop(1).parseTypesMapsToList()


        return seeds.minOf { seed ->

            return@minOf seedPlantPath.getLocationTrackedFromSeed(seed)
        }
    }

    fun part2(input: String): Long {
        val parts = input.split("\n\n")
        val seeds = parts.first().substringAfter("seeds: ").split(' ')
            .chunked(2)
            .map {
                val seedN = it[0].toLong()
                val range = it[1].toLong()
                seedN..<seedN + range
            }
            .asSequence()
            .flatMap {
                it.asSequence()
            }

        val seedPlantPath: List<List<PlantMap>> = parts.drop(1).parseTypesMapsToList()

        return seeds.minOf { seed ->

                return@minOf seedPlantPath.getLocationTrackedFromSeed(seed)
            }
    }

    /**
     * maps here is sorted as it parsed, that important.
     */
    private fun List<String>.parseTypesMapsToList(): List<List<PlantMap>> = map {
        val currMapRangesWithoutTitle = it.split("\n").drop(1)

        currMapRangesWithoutTitle.map {
            val (desRange, sourceRange, len) = it.split(' ').map { it.toLong() }

            PlantMap(
                sourceRange..<sourceRange + len,
                desRange..<desRange + len
            )
        }
    }


    private fun List<List<PlantMap>>.getLocationTrackedFromSeed(
        seed: Long
    ) = fold(seed) { currTrack: Long, nextMap: List<PlantMap> ->

        val nextCorrespondingNumber = nextMap
            .firstOrNull { currTrack in it.sourceRange }
            ?.getCorrespondingDestination(currTrack)

        return@fold nextCorrespondingNumber ?: currTrack
    }


    val input
        get() = readInputAsText("Day5", "aoc2023")

    val testInput
        get() = readInputAsText("Day5_test", "aoc2023")

}
