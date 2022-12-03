/*
    is the score for the shape you selected (1 for Rock, 2 for Paper, and 3 for Scissors)
    plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw,
     and 6 if you won).
 */

//      op-me  score
// Rock  A-X    1
// Paper B-Y    2
// Cisso C-Z    3

//   lose       0
//   draw       3
//   win        6


enum class E(val initScore: Int){ROCK(1), PAPER(2),SCISSORS(3) }
val E.whatDefeat :E
    get() = when(this){
    E.ROCK -> E.SCISSORS
    E.SCISSORS -> E.PAPER
    E.PAPER -> E.ROCK
}
fun E.canDefeat(other: E): Boolean = this.whatDefeat == other
fun E.playWith(other: E): Int = when{
    this.canDefeat(other) -> 6
    this == other -> 3
    else -> 0
}
fun main() {
    val opponentRepresentation = mapOf(
            "A" to E.ROCK, "B" to E.PAPER, "C" to E.SCISSORS,
    )
    fun part1(input: List<String>): Int {
    val myRepresentation = mapOf("X" to E.ROCK, "Y" to E.PAPER, "Z" to E.SCISSORS )

        var score = 0

        for (round in input){
            val(opponent, me) = round.split(" ").let {

                opponentRepresentation.getValue(it.first()) to myRepresentation.getValue(it[1])
            }

           score += me.initScore + me.playWith(opponent)
        }

        return score
    }



    fun part2(input: List<String>): Int {
        val myRepresentation = mapOf("X" to -1, "Y" to 0, "Z" to 1 )

        var score = 0

        for (round in input){
            val(opponent, myStrategy) = round.split(" ").let {

                    opponentRepresentation.getValue(it[0]) to myRepresentation.getValue(it[1])
            }
            val me = when(myStrategy){
                -1 -> opponent.whatDefeat
                0 -> opponent
                else -> E.values().first { it != opponent && it != opponent.whatDefeat }
            }

            score += me.initScore + me.playWith(opponent)
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput).also(::println) == 15)
    check(part2(testInput).also(::println) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
