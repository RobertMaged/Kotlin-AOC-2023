fun main() {

    fun part1(input: Array<IntArray>): Int {
        var score = 0

        for (rowIndex in input.indices) {
            for (colIndex in input[rowIndex].indices) {

                // edge trees
                if (rowIndex == 0 || rowIndex == input.lastIndex
                    ||
                    colIndex == 0 || colIndex == input[colIndex].lastIndex
                ) {
                    score++
                    continue
                }


                val currTree = input[rowIndex][colIndex]
                when {
                    //left
                    input[rowIndex].take(colIndex).all { currTree > it } -> score++
                    //right
                    input[rowIndex].drop(colIndex + 1).all { currTree > it } -> score++
                    //top
                    input.take(rowIndex).all { currTree > it[colIndex] } -> score++
                    //bottom
                    input.drop(rowIndex + 1).all { currTree > it[colIndex] } -> score++
                }

            }
        }

        return score
    }


    fun part2(input: Array<IntArray>): Int {
        var score = 0

        for (rowIndex in input.indices) {

            for (colIndex in input[rowIndex].indices) {

                // edge trees
                if (
                    rowIndex == 0 || rowIndex == input.lastIndex
                    ||
                    colIndex == 0 || colIndex == input[colIndex].lastIndex
                ) continue


                val currTree = input[rowIndex][colIndex]

                val right = input[rowIndex].drop(colIndex + 1).takeWhileInclusive { currTree > it }.size
                val left = input[rowIndex].take(colIndex).asReversed().takeWhileInclusive { currTree > it }.size
                val bottom = input.drop(rowIndex + 1).takeWhileInclusive { currTree > it[colIndex] }.size
                val top = input.take(rowIndex).asReversed().takeWhileInclusive { currTree > it[colIndex] }.size

                score = maxOf(score, (top * left * right * bottom))
            }
        }

        return score
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInputAs2DIntArray("Day08_test")
    check(part1(testInput).also(::println) == 21)
    check(part2(testInput).also(::println) == 8)

    val input = readInputAs2DIntArray("Day08")
    println(part1(input))
    println(part2(input))
}

private fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        if (!predicate(item))
            break
    }
    return list
}
