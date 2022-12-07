fun main() {
    fun ArrayDeque<String>.toPWDs(): List<String> = runningReduce { pwdAcc, curDir -> pwdAcc + curDir }

    fun Map<String, Int>.getDeviceFreeSpace(): Int = (70_000_000 - this.getValue("/"))

    fun List<String>.readDirsSizes(): Map<String, Int> {
        val filesStack = ArrayDeque(listOf("/"))
        val sizes = mutableMapOf<String, Int>()

        for (line in this) when {
            // accumulate dirs hierarchy sizes
            line.first().isDigit() ->
                for (pwd in filesStack.toPWDs())
                    sizes[pwd] = sizes.getOrDefault(pwd, 0) + line.split(" ").first().toInt()


            line.startsWith("\$ cd ").not() -> continue

            // from here we sure cmd in cd, index after "$ cd " is 5
            line[5] == '/' -> run { filesStack.clear(); filesStack.addLast("/") }
            line[5] == '.' -> filesStack.removeLast()
            else -> filesStack.addLast("${line.substringAfter("cd ")}/")
        }

        return sizes.toMap()
    }

    fun part1(input: List<String>): Int = input.readDirsSizes().values.filter { it <= 100_000 }.sum()

    fun part2(input: List<String>): Int {
        val sizes = input.readDirsSizes()

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

