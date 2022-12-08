fun main() {
    fun part1(input: List<String>): Int {

        val array = mutableListOf<IntArray>()

        var score = 0

        for ((i, line) in input.withIndex()) {
            array.add(i, line.toCharArray().map { it.digitToInt() }.toIntArray())
        }


        for (i in array.indices) {

            for (j in array[i].indices) {
                score += when {
                    i == 0 -> 1
                    i == array.lastIndex -> 1
                    j == 0 -> 1
                    j == array[j].lastIndex -> 1


                    else -> {
                        val curr = array[i][j]
                        if (array[i].take(j).all { curr > it } || array[i].drop(j + 1).all { curr > it })
                            1
                        else if (array.take(i).all { curr > it[j] } || array.drop(i + 1).all { curr > it[j] })
                            1
                        else
                            0
                    }
                }
            }

        }


        return score
    }

    fun part2(input: List<String>): Int {

        val array = mutableListOf<IntArray>()

        var score = 0

        for ((i, line) in input.withIndex()) {
            array.add(i, line.toCharArray().map { it.digitToInt() }.toIntArray())
        }


        for (i in array.indices) {

            for (j in array[i].indices) {
                when {
                    i == 0 -> continue
                    i == array.lastIndex -> continue
                    j == 0 -> continue
                    j == array[j].lastIndex -> continue
                }


                var cou = 1
                val curr = array[i][j]

                var lastBoundIndex = 0
                // left right

                var right = 0
                for ((k, c) in array[i].drop (j+1).withIndex()){
                        right++
                    if (c >= curr)
                        break
                }

                var left = 0
                for (c in array[i].take(j).asReversed()){
                    left++
                    if (c>= curr) break
                }

                var bottom = 0
                for (r in array.drop(i+1)){
                    bottom++
                    if (r[j]>= curr) break
                }

                var top = 0
                for (r in array.take(i).asReversed()){
                    top++
                    if (r[j]>= curr) break
                }

                score = maxOf(score, (top * left * right * bottom))
//
//                cou *= array[i].take(j).asReversed().let { maxOf(it.indexOfFirst { curr <= it }, 1) } + 1
//                cou *= array[i].drop(j + 1).let { maxOf(it.indexOfFirst { curr <= it }, 1) } + 1
//
//                cou *= array.take(i).asReversed().let { maxOf(it.indexOfFirst { curr <= it[j] }, 1) } + 1
//                cou *= array.drop(i + 1).let { maxOf(it.indexOfFirst { curr <= it[j] }, 1) } + 1
//
//                log += cou
            }
        }




        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput).also(::println) == 21)
    check(part2(testInput).also(::println) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}