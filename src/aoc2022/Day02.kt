package aoc2022



private enum class Game(val initScore: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    val whatDefeat: Game
        get() = when (this) {
            ROCK -> SCISSORS
            SCISSORS -> PAPER
            PAPER -> ROCK
        }

}

private fun Game.canDefeat(other: Game): Boolean = this.whatDefeat == other

private fun Game.playWith(other: Game): Int = when {
    this.canDefeat(other) -> 6
    this == other -> 3
    else -> 0
}

fun main() {

    fun List<String>.scoreOfRounds(decideRespondGame: (Game, String) -> Game): Int = sumOf { round ->
        val opponentGameMap = mapOf(
                "A" to Game.ROCK, "B" to Game.PAPER, "C" to Game.SCISSORS,
        )

        val (opponentGame, myGame) = round.split(" ").let { (elfRound, myStrategy) ->

            val opponentElfGame = opponentGameMap.getValue(elfRound)
            return@let opponentElfGame to decideRespondGame(opponentElfGame, myStrategy)
        }

        return@sumOf myGame.initScore + myGame.playWith(opponentGame)
    }

    fun part1(input: List<String>): Int = input.scoreOfRounds { _, encryptedResponse ->

        return@scoreOfRounds when (encryptedResponse) {
            "X" -> Game.ROCK
            "Y" -> Game.PAPER
            else -> Game.SCISSORS
        }
    }

    fun part2(input: List<String>): Int = input.scoreOfRounds { opponentGame, encryptedStrategy ->

       return@scoreOfRounds when (encryptedStrategy) {
            // should lose
            "X" -> opponentGame.whatDefeat
            // draw
            "Y" -> opponentGame
            // win
            else -> Game.values().first { it != opponentGame && it != opponentGame.whatDefeat }
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput).also(::println) == 15)
    check(part2(testInput).also(::println) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
