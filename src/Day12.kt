private typealias Ver = Pair<Int, Int>

private val Ver.y get() = first
private val Ver.x get() = second
fun main(args: Array<String>) {


    fun part1(input: List<String>): Int {

        val c2D = input.map { it.toCharArray() }.toTypedArray()

        val sIndex = listOf(0 to 0, 20 to 0).first {
            c2D[it.y][it.x] == 'S'
        }
        val eIndex = listOf(2 to 5, 20 to 120).first {
            c2D[it.y][it.x] == 'E'
        }
        c2D[sIndex.y][sIndex.x] = 'a'
        c2D[eIndex.y][eIndex.x] = 'z'

        val graph = buildMap<Ver, Set<Ver>> {
            for (i in c2D.indices)
                for (j in c2D[0].indices) {
                    put(i to j, listOf(
                        i - 1 to j, //top
                        i to j - 1, //left
                        i to j + 1, //right
                        i + 1 to j,//bottom
                    )
                        .filter { it.y in c2D.indices && it.x in c2D[0].indices }
                        .filter { c2D[it.y][it.x] - c2D[i][j] < 2 }
                        .toSet()
                    )
                }
        }


        val queue = ArrayDeque(listOf(sIndex))
        val searched = mutableSetOf<Ver>()
        val parents = mutableMapOf<Ver, Ver>()
        while (queue.any()) {
            val currPlaceVer = queue.removeFirst()
            if (currPlaceVer in searched) continue
            searched += currPlaceVer

            val adj = graph[currPlaceVer]!!
            adj.forEach {
                if (it !in searched)
                    parents[it] = currPlaceVer
            }

            if (eIndex in adj) {
                println("found by $currPlaceVer")
                val path = mutableSetOf<Ver>(currPlaceVer)
                var last: Ver? = currPlaceVer
                while (last != null && last != eIndex) {
                    path.add(last)
                    val n = parents[last]
                    last = n
                }

                return path.size
            } else {
                adj.forEach(queue::addLast)
            }
        }

        return error("not accepted")
    }

    fun part2Dummy(input: List<String>): Int {
        val map = mutableMapOf('S' to 'b')

        val c2D = input.map { it.toCharArray() }.toTypedArray()

        val SInd = listOf(0 to 0, 20 to 0).first {
            c2D[it.y][it.x] == 'S'
        }
        val eIndex = listOf(2 to 5, 20 to 120).first {
            c2D[it.y][it.x] == 'E'
        }
        c2D[SInd.y][SInd.x] = 'a'
        c2D[eIndex.y][eIndex.x] = 'z'

        val graph = buildMap<Ver, Set<Ver>> {
            for (i in c2D.indices)
                for (j in c2D[0].indices) {
//                    if (c2D[i][j] == 'E') continue
                    put(i to j, listOf(
                        i - 1 to j, //top
                        i to j - 1, //left
                        i to j + 1, //right
                        i + 1 to j,//bottom
                    )
                        .filter { it.y in c2D.indices && it.x in c2D[0].indices }
//                        .filter { c2D[it.y][it.x] in listOf('E', 'S') || c2D[it.y][it.x] - c2D[i][j] < 2 }
                        .filter { c2D[it.y][it.x] - c2D[i][j] < 2 }
                        .toSet()
                    )
                }
        }

        val allAs = graph.keys.filter { c2D[it.y][it.x] == 'a' }
        val sizes = mutableListOf<Int>()

        for (sIndex in allAs) {

            val queue = ArrayDeque(listOf(sIndex))
            val searched = mutableSetOf<Ver>()
            val parents = mutableMapOf<Ver, Ver>()
            while (queue.any()) {
                val currPlaceVer = queue.removeFirst()
                if (currPlaceVer in searched) continue
                searched += currPlaceVer

                val adj = graph[currPlaceVer]!!
                adj.forEach {
                    if (it !in searched)
                        parents[it] = currPlaceVer
                }

                if (eIndex in adj) {
                    val path = mutableSetOf<Ver>(currPlaceVer)
                    var last2: Ver? = currPlaceVer
                    while (last2 != null && last2 != eIndex) {
                        path.add(last2)
                        last2 = parents[last2]
                    }

                    sizes += path.size
                } else {
                    adj.forEach(queue::addLast)
                }
            }
        }


        return sizes.min()
    }


    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    check(part1(testInput).also(::println) == 31)
    val p1 = part1(input)
    println(p1)
//    sendResult(answer = p1.toString(), part = 1, day = "12", year = 2022)

    check(part2Dummy(testInput).also(::println) == 29)
    val p2 = part2Dummy(input)
    println(p2)
    check(part2Dummy(input).also(::println) == 451)
//    sendResult(answer = p2.toString(), part = 2, day = "12", year = 2022)

}

private fun part2(input: List<String>): Int {
    val map = mutableMapOf('S' to 'b')

    val c2D = input.map { it.toCharArray() }.toTypedArray()

    val sIndex = listOf(0 to 0, 20 to 0).first {
        c2D[it.y][it.x] == 'S'
    }
    val eIndex = listOf(2 to 5, 20 to 120).first {
        c2D[it.y][it.x] == 'E'
    }
    c2D[sIndex.y][sIndex.x] = 'a'
    c2D[eIndex.y][eIndex.x] = 'z'

    val graph = buildMap<Ver, Set<Ver>> {
        for (i in c2D.indices)
            for (j in c2D[0].indices) {
//                    if (c2D[i][j] == 'E') continue
                put(i to j, listOf(
                    i - 1 to j, //top
                    i to j - 1, //left
                    i to j + 1, //right
                    i + 1 to j,//bottom
                )
                    .filter { it.y in c2D.indices && it.x in c2D[0].indices }
//                        .filter { c2D[it.y][it.x] in listOf('E', 'S') || c2D[it.y][it.x] - c2D[i][j] < 2 }
//                        .filter { c2D[it.y][it.x] - c2D[i][j] < 2 }
                    .filter { c2D[i][j] - c2D[it.y][it.x] > 1 }
                    .toSet()
                )
            }
    }


    val queue = ArrayDeque(listOf(eIndex))

    val searched = mutableSetOf<Ver>()
    val parents = mutableMapOf<Ver, Ver>()
    while (queue.any()) {
        val currPlaceVer = queue.removeFirst()
        if (currPlaceVer in searched) continue
        searched += currPlaceVer

        val adj = graph[currPlaceVer]!!
        adj.forEach {
            if (it !in searched)
                parents[it] = currPlaceVer
        }

        if ('a' in adj.map { c2D[it.y][it.x] }) {
            val aIndex = adj.single { c2D[it.y][it.x] == 'a' }
            println("found by $currPlaceVer")
            val path = mutableSetOf<Ver>(currPlaceVer)
            var last2: Ver? = currPlaceVer
            while (last2 != null && last2 != aIndex) {
                path.add(last2)
                val n = parents[last2]
                last2 = n
            }
            return path.size
//                return 0
        } else {
            adj.forEach(queue::addLast)
        }
    }

    return error("not accepted")
}

