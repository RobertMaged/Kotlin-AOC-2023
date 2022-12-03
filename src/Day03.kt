private val scale = (('a'..'z') + ('A'..'Z')).mapIndexed { index, c -> c to index+1 }
        .associate {
            it
        }

fun main() {
    fun part1(input: List<String>): Int {
var score = 0
        for (line in input){
            val (first, second) = line.toCharArray(0, line.length /2) to line.toCharArray(line.length /2)

            score += first.filter { it in second }
                    .distinct()
                    .let {
                        it
                    }
                    .sumOf { scale[it]!! } ?:0
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        for (i in input.indices step 3){
            val (first, second, third) = (i..i+2).map {
                input[it].toCharArray()
            }



            score += ((first intersect second.toSet()) intersect third.toSet())
                    .let {
                        scale[it.first()] ?: 0
                    }
//                    .sumOf { scale[it]!! })
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput).also(::println) == 157)
    check(part2(testInput).also(::println) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
