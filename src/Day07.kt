fun main() {
    fun ArrayDeque<String>.toPWDs(): List<String> = runningReduce { pwdAcc, curDir -> pwdAcc + curDir }

    fun Map<String, Int>.getDeviceFreeSpace(): Int = ( 70_000_000 - this.getValue("/") )

    fun List<String>.dirsSize(): Map<String, Int> {
        val filesStack = ArrayDeque(listOf("/"))
        val sizes = mutableMapOf<String, Int>()

        for (line in this) when {
            line.startsWith("\$ cd /") -> run { filesStack.clear(); filesStack.addLast("/") }

            line.startsWith("\$ cd ..") -> filesStack.removeLast()

            line.startsWith("\$ cd") -> filesStack.addLast("${line.substringAfter("cd ")}/")

            line.startsWith("\$ ls") || line.startsWith("dir") -> Unit // no need to track files themselves

            // accumulate dirs hierarchy sizes
            line.first().isDigit() -> filesStack.toPWDs().forEach { pwd ->
                val fileSize = line.split(" ").first().toInt()
                sizes[pwd] = sizes.getOrDefault(pwd, 0) + fileSize
            }
        }
        return sizes.toMap()
    }

    fun part1(input: List<String>): Int {
        val filesStack = ArrayDeque(listOf("/"))
        val sizes = mutableMapOf<String, Int>()

        for (line in input) when {
            line.startsWith("\$ cd /") -> run { filesStack.clear(); filesStack.addLast("/") }

            line.startsWith("\$ cd ..") -> filesStack.removeLast()

            line.startsWith("\$ cd") -> filesStack.addLast("${line.substringAfter("cd ")}/")

            line.startsWith("\$ ls") || line.startsWith("dir") -> Unit // no need to track files themselves

            // accumulate dirs hierarchy sizes
            line.first().isDigit() -> filesStack.toPWDs().forEach { pwd ->
                val fileSize = line.split(" ").first().toInt()
                sizes[pwd] = sizes.getOrDefault(pwd, 0) + fileSize
            }
        }


        return sizes.values.filter { it <= 100_000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val sizes = input.dirsSize()

        val neededFreeSpace = 30_000_000L - sizes.getDeviceFreeSpace()
        return sizes.values.filter { it >= neededFreeSpace }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput).also(::println) == 95437)
    check(part2(testInput).also(::println) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

