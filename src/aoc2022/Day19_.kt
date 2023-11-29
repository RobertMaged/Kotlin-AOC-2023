package aoc2022

import checkEquals

import sendAnswer

//
//private sealed class Goods{
//    data class ORE(val oreCost: Int) : Goods()
//    data class CLAY(val oreCost: Int) : Goods()
//    data class OBSIDIAN(val oreCost: Int, val clayCost: Int) : Goods()
//    data class GEODE(val oreCost: Int, val obsidianCost: Int) : Goods()
//    companion object {
//        fun values(): Array<Goods> {
//            return arrayOf(ORE, CLAY, OBSIDIAN, GEODE)
//        }
//
//        fun valueOf(value: String): Goods {
//            return when (value) {
//                "ORE" -> ORE
//                "CLAY" -> CLAY
//                "OBSIDIAN" -> OBSIDIAN
//                "GEODE" -> GEODE
//                else -> throw IllegalArgumentException("No object Goods.$value")
//            }
//        }
//    }
//}
//private fun Goods.of(oreCost: Int, clayCost: Int, obsedianOreCost: Int, obsedianClayCost: Int, geodeOreConst: Int, geode)

//private val startingRobots = Goods.values().associate { it.toString() to if(it == Goods.ORE) 1 else 0 }
//private data class Blueprint(val id: Int, val robotCosts: Map<String, Goods>, val workingRobots: MutableMap<String, Int> = startingRobots.toMutableMap(), val goods: MutableMap<String, Int>)
fun main() {
    fun part1(input: List<String>): Int {
       return input.withIndex().sumOf { (blueprintIndex, line) ->
            val id = blueprintIndex +1
            val robots = line.dropWhile { it != 'E' }.split(". ")
            val oreCost = robots[0].substringAfter("costs ").first().digitToInt()
            val clayCost = robots[1].substringAfter("costs ").first().digitToInt()
            val obsidianCostOre = robots[2].substringAfter("costs ").first().digitToInt()
            val obsidianCostClay = robots[2].substringAfter("and ").takeWhile { it.isDigit() }.toInt()
            val geodeOreCost = robots[3].substringAfter("costs ").first().digitToInt()
            val geodeObsidianCost = robots[3].substringAfter("and ").takeWhile { it.isDigit() }.toInt()

            var (oreRobs, clayRobs, obsidianRobs, geodeRobs) = listOf(1, 0, 0, 0)
            var (oreCount, clayCount, obsidianCount, geodeCount) = listOf(0, 0, 0, 0)

            var minutes = 1
           repeat(24){

           val robFactory: () -> Unit = when{
               oreCount >= geodeOreCost && obsidianCount >= geodeObsidianCost ->{
                   oreCount-= geodeOreCost
                   obsidianCount -= geodeObsidianCost
                   { geodeRobs++ }
               }
               oreCount >= obsidianCostOre && clayCount >= obsidianCostClay ->{
                   oreCount-= obsidianCostOre
                   clayCount -= obsidianCostClay
                   { clayRobs++ }
               }
               oreCount >= clayCost -> {
                   oreCount -= clayCost
                   { clayRobs++ }
               }
               oreCount >= oreCost -> {
                   oreCount-= oreCost
                   { oreRobs++ }
               }
               else -> {{}}
           }

                repeat(oreRobs){
                    oreCount++
                }
                repeat(clayRobs){
                    clayCount++
                }
                repeat(obsidianRobs){
                    obsidianCount++
                }
                repeat(geodeRobs){
                    geodeCount++
                }
                robFactory()
               val x = 0
            }

            id * geodeCount
        }
//        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day19_test")
    val input = readInput("Day19")

    part1(testInput).checkEquals(33)
    part1(input)
        .sendAnswer(part = 1, day = "19", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "19", year = 2022)
}
