fun main() {

    fun part1(input: Array<IntArray>): Int {
        val verticalEdgesCount = input.size * 2
        val betweenHorizontalEdgesCount = (input.first().size - 2) * 2

        var visibleTreesCount = verticalEdgesCount + betweenHorizontalEdgesCount

        for (rowIndex in 1 until input.lastIndex) {
            for (colIndex in 1 until input.first().lastIndex) {

                val currTree = input[rowIndex][colIndex]
                when {
                    //left
                    input[rowIndex].take(colIndex).all { currTree > it } -> visibleTreesCount++
                    //right
                    input[rowIndex].drop(colIndex + 1).all { currTree > it } -> visibleTreesCount++
                    //top
                    input.take(rowIndex).all { currTree > it[colIndex] } -> visibleTreesCount++
                    //bottom
                    input.drop(rowIndex + 1).all { currTree > it[colIndex] } -> visibleTreesCount++
                }
            }
        }
        return visibleTreesCount
    }

    fun part2(input: Array<IntArray>): Int {
        var viewingDistanceScore = 0

        for (rowIndex in 1 until input.lastIndex) {
            for (colIndex in 1 until input.first().lastIndex) {

                val currTree = input[rowIndex][colIndex]

                val right = input[rowIndex].drop(colIndex + 1).takeWhileInclusive { currTree > it }.size
                val left = input[rowIndex].take(colIndex).asReversed().takeWhileInclusive { currTree > it }.size
                val bottom = input.drop(rowIndex + 1).takeWhileInclusive { currTree > it[colIndex] }.size
                val top = input.take(rowIndex).asReversed().takeWhileInclusive { currTree > it[colIndex] }.size

                viewingDistanceScore = maxOf(viewingDistanceScore, (top * left * right * bottom))
            }
        }

        return viewingDistanceScore
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
