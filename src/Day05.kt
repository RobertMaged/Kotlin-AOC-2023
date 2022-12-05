

fun main() {
    fun List<String>.splitIt(n: Int): Pair<List<String>, List<Triple<Int, Int,Int>>>{
        return this.take(n-1) to this.drop(n).map {
            val count = it.substringAfter("move ").takeWhile { it.isDigit() }.toInt()
            val from = it.substringAfter("from ").takeWhile { it.isDigit() }.toInt()
            val to = it.substringAfter("to ").takeWhile { it.isDigit() }.toInt()
            return@map Triple(count, from, to)
        }
    }

    fun List<String>.getStakes(): Map<Int, ArrayDeque<Char>>{
        val map = mutableMapOf<Int, ArrayDeque<Char>>()
        this.forEach {line ->
//            line.chunked(4).map { it[1] }.filter { it.isLetter() }
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


    fun part1(input: List<String>): String {

        val (top, structures) = input.splitIt(10)

        val map = top.getStakes()

        var score = 0

        for ( (count, from, to) in structures){

            val source = map[from]!!
            val des = map[to]!!

             (1..count).forEach{ _ ->
                 des.addLast(source.removeLast())
            }

        }
        var s = ""
        for (i in 1..map.size){
            s += map[i]!!.last()!!
        }
        return s
    }

    fun part2(input: List<String>): String {

        val (top, structures) = input.splitIt(10)

        val map = top.getStakes()


        for ( (count, from, to) in structures){

            val source = map[from]!!
            val des = map[to]!!

            var list = mutableListOf<Char>()
            (1..count).forEach{ _ ->

                list.add(source.removeLast())
            }
            for (c in list.reversed())
                des.addLast(c)

        }
        var s = ""
        for (i in 1..map.size){
            s += map[i]!!.last()!!
        }
        return s
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput).also(::println) == "CMZ")
    check(part2(testInput).also(::println) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
