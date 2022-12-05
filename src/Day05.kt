

fun main() {
    data class RearrangementProcedure(val movesCount: Int, val fromStackAt: Int, val toStackAt: Int)

    fun List<String>.splitStacksFromProcedures() : Pair<List<String>, List<String>>{
        val stacksCrates = this.takeWhile { it.isNotBlank() }
        val procedures = this.dropWhile { !it.contains("move") }

        return stacksCrates to procedures
    }

    fun List<String>.readProcedures() : List<RearrangementProcedure> = map { instruction ->

        val count = instruction.substringAfter("move ").takeWhile { it.isDigit() }.toInt()
        val from = instruction.substringAfter("from ").takeWhile { it.isDigit() }.toInt()
        val to = instruction.substringAfter("to ").takeWhile { it.isDigit() }.toInt()

        return@map RearrangementProcedure(count, from, to)
    }

    fun List<String>.loadStacksCrates(): Map<Int, ArrayDeque<Char>>{
        val map = mutableMapOf<Int, ArrayDeque<Char>>()
        this.forEach {line ->
            line.chunked(4).map { it[1] }
                .mapIndexed{ index, c ->
                    if (c.isLetter().not()) return@mapIndexed

                    val q = map[index+1]
                    if (q == null)
                        map[index+1] = ArrayDeque(listOf(c))
                    else
                        q.addFirst(c)
                }
        }
        return map
    }

    fun Map<Int, ArrayDeque<Char>>.topCratesAsMsg(): String {
        var s = ""
        for (i in 1..this.size){
            s += this[i]!!.last()!!
        }
        return s
    }

    fun List<String>.loadStacksAndProcedures(): Pair<Map<Int, ArrayDeque<Char>>, List<RearrangementProcedure>> {
        val (stacks, procedures) = this.splitStacksFromProcedures()

        return stacks.loadStacksCrates() to procedures.readProcedures()
    }

    fun part1(input: List<String>): String {

        val (stacksMap, procedures) = input.loadStacksAndProcedures()

        for ( p in procedures){
            val source = stacksMap[p.fromStackAt]!!
            val des = stacksMap[p.toStackAt]!!

             repeat(p.movesCount){ _ ->
                 des.addLast(source.removeLast())
            }

        }

        return stacksMap.topCratesAsMsg()
    }

    fun part2(input: List<String>): String {

        val (stacksMap, procedures) = input.loadStacksAndProcedures()

        for ( p in procedures){
            val source = stacksMap[p.fromStackAt]!!
            val des = stacksMap[p.toStackAt]!!

            val list = mutableListOf<Char>()
            (1..p.movesCount).forEach{ _ ->

                list.add(source.removeLast())
            }
            for (c in list.reversed())
                des.addLast(c)

        }

        return stacksMap.topCratesAsMsg()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == "CMZ")
    check(part2(testInput).also(::println) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
