private typealias Ver = Pair<Int, Int>

private val Ver.y get() = first
private val Ver.x get() = second

private fun Array<CharArray>.findEdgesIndices(): Pair<Ver, Ver> {
    val c2D = this@findEdgesIndices
    val edgs = Array(2) { -1 to -1 }
    var requiredEdgesFound = 0
    edgesFinder@ for (i in c2D.indices) {
        for (j in c2D[0].indices) {
            if (c2D[i][j] != 'S' && c2D[i][j] != 'E') continue

            if (c2D[i][j] == 'S') {
                c2D[i][j] = 'a'
                edgs[0] = i to j
            } else {
                c2D[i][j] = 'z'
                edgs[1] = i to j
            }

            requiredEdgesFound++
            if (requiredEdgesFound == 2) break@edgesFinder
        }
    }
    return edgs[0] to edgs[1]
}

private fun Array<CharArray>.drawHeightsGraph(onAdjacentFilter: (adjH: Ver, curH: Ver) -> Boolean) =
    buildMap<Ver, Set<Ver>> {
        val chars2D = this@drawHeightsGraph
        for (i in chars2D.indices)
            for (j in chars2D[0].indices) {
                put(i to j, listOf(
                    i - 1 to j, //top
                    i to j - 1, //left
                    i to j + 1, //right
                    i + 1 to j,//bottom
                )
                    .filter { it.y in indices && it.x in chars2D[0].indices }
                    .filter { onAdjacentFilter(it, i to j) }
                    .toSet()
                )
            }
    }

private fun <T : Ver> Map<T, T>.buildWalkedPath(endNode: T, endNodeParent: T) = buildSet<T> {
    val parents = this@buildWalkedPath

    add(endNodeParent)

    var last: T? = endNodeParent
    while (last != null && last != endNode) {
        add(last)
        last = parents[last]
    }
}


fun main(args: Array<String>) {

    fun part1(chars2D: Array<CharArray>): Int {

        val (sIndex, eIndex) = chars2D.findEdgesIndices()

        val graph = chars2D.drawHeightsGraph(onAdjacentFilter = { adjH, curH ->
            chars2D[adjH.y][adjH.x] - chars2D[curH.y][curH.x] < 2
        })

        val queue = ArrayDeque(listOf(sIndex))
        val searched = mutableSetOf<Ver>()
        val parents = mutableMapOf<Ver, Ver>()

        var lastAdjacentBeforeE: Ver? = null

        while (queue.any()) {
            val currPositionVer = queue.removeFirst()
            if (currPositionVer in searched) continue
            searched += currPositionVer

            val adjacentVertices: Set<Ver> = graph[currPositionVer]!!

            adjacentVertices
                .filterNot { it in searched }
                .forEach { parents[it] = currPositionVer }

            if (eIndex in adjacentVertices) {
                lastAdjacentBeforeE = currPositionVer
                break
            } else
                adjacentVertices.forEach(queue::addLast)
        }

        return parents
            .buildWalkedPath(endNode = eIndex, endNodeParent = lastAdjacentBeforeE!!)
            .size
    }

    fun part2(chars2D: Array<CharArray>): Int {
        val (_, eIndex) = chars2D.findEdgesIndices()

        val graph = chars2D.drawHeightsGraph(onAdjacentFilter = { adjH, curH ->
            chars2D[curH.y][curH.x] - chars2D[adjH.y][adjH.x] < 2
        })

        val queue = ArrayDeque(listOf(eIndex))
        val searched = mutableSetOf<Ver>()
        val parents = mutableMapOf<Ver, Ver>()

        var lastAdjacentBeforeA: Ver? = null
        var fastestFoundA: Ver? = null

        while (queue.any()) {
            val currPositionVer = queue.removeFirst()
            if (currPositionVer in searched) continue
            searched += currPositionVer

            val adjacentVertices: Set<Ver> = graph[currPositionVer]!!

            adjacentVertices
                .filterNot { it in searched }
                .forEach { parents[it] = currPositionVer }

            if (adjacentVertices.any { chars2D[it.y][it.x] == 'a' }) {
                lastAdjacentBeforeA = currPositionVer
                fastestFoundA = adjacentVertices.single { chars2D[it.y][it.x] == 'a' }
                break
            } else
                adjacentVertices.forEach(queue::addLast)
        }

        return parents
            .buildWalkedPath(endNode = fastestFoundA!!, endNodeParent = lastAdjacentBeforeA!!)
            .size
    }


    val testInput = readInputAs2DCharArray("Day12_test")
    val input = readInputAs2DCharArray("Day12")


    part1(testInput.cloneOf2D()).checkEquals(31)
    part1(input.cloneOf2D())
        .checkEquals(462)
//        .sendAnswer(part = 1, day = "12", year = 2022)


    part2(testInput.cloneOf2D()).checkEquals(29)
    part2(input.cloneOf2D())
        .checkEquals(451)
//        .sendAnswer(part = 2, day = "12", year = 2022)

}
