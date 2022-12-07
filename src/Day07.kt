data class Dir(val name  : String, val child: List<Dir>?, val size: Int)




fun main() {
    fun ArrayDeque<String>.prefixes(): List<String> = runningReduce { acc, s -> acc + s }
    fun part1(input: List<String>): Int {
val filesStack = ArrayDeque<String>(listOf("/"))
val sizes = mutableMapOf<String, Long>()
//        val filesStack = ArrayDeque<String>(listOf("/"))
//        val dirMap = mutableMapOf<String, Set<String>>("/" to emptySet<String>())
//        for (line in input){
/*
            when{
                line.equals("$ cd /") -> run { while (filesStack.last() != "/") filesStack.removeLast() }
                line.contains("cd")-> { line.substringAfter("cd ").also{dirMap[filesStack.last()] = (dirMap[filesStack.last()] ?: emptySet()) + setOf(it)}.also(filesStack::addLast)}
                line.contains("cd ..") -> filesStack.removeLast()
                line.contains("ls") ->
            }
*/
//        }
        for (line in input){

            when{
                line.startsWith("\$ cd /") -> run {filesStack.clear(); filesStack.addLast("/") }
                line .startsWith ("\$ cd ..") -> {
                    filesStack.removeLast();
                }
                line.startsWith("\$ cd")-> { line.substringAfter("cd ").also { filesStack.addLast("$it/")}}//; filesStack.addLast("/")}}
                line.startsWith("\$ ls") || line.startsWith("dir") -> Unit
                line.first().isDigit() -> line.split(" ").let {
                for (d in filesStack.prefixes()) {
//                    sizes[d + it[1]] = sizes.getOrDefault(d + it[1], 0) + it[0].toLong()
                    sizes[d] = sizes.getOrDefault(d, 0) + it[0].toLong()
                }
                }

            }

        }

        return sizes.values.filter { it <= 100000L }.sum()
            .toInt()
    }

    fun part2(input: List<String>): Int {

        val filesStack = ArrayDeque<String>(listOf("/"))
        val sizes = mutableMapOf<String, Long>()
//        val filesStack = ArrayDeque<String>(listOf("/"))
//        val dirMap = mutableMapOf<String, Set<String>>("/" to emptySet<String>())
//        for (line in input){
        /*
                    when{
                        line.equals("$ cd /") -> run { while (filesStack.last() != "/") filesStack.removeLast() }
                        line.contains("cd")-> { line.substringAfter("cd ").also{dirMap[filesStack.last()] = (dirMap[filesStack.last()] ?: emptySet()) + setOf(it)}.also(filesStack::addLast)}
                        line.contains("cd ..") -> filesStack.removeLast()
                        line.contains("ls") ->
                    }
        */
//        }
        for (line in input){

            when{
                line.startsWith("\$ cd /") -> run {filesStack.clear(); filesStack.addLast("/") }
                line .startsWith ("\$ cd ..") -> {
                    filesStack.removeLast();
                }
                line.startsWith("\$ cd")-> { line.substringAfter("cd ").also { filesStack.addLast("$it/")}}//; filesStack.addLast("/")}}
                line.startsWith("\$ ls") || line.startsWith("dir") -> Unit
                line.first().isDigit() -> line.split(" ").let {
                    for (d in filesStack.prefixes()) {
//                    sizes[d + it[1]] = sizes.getOrDefault(d + it[1], 0) + it[0].toLong()
                        sizes[d] = sizes.getOrDefault(d, 0) + it[0].toLong()
                    }
                }

            }

        }

        return sizes.values.filter{ it >= 30_000_000L - (70_000_000L - sizes[filesStack.first()]!!) }
            .minOf {
                it
            }
            ?.toInt()!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also(::println) == 95437)
    check(part2(testInput).also(::println) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

