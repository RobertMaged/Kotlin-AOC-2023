fun main() {
    fun part1(input: String): Int {


           return input.windowed(4).indexOfFirst {
               it.toSet().size == it.length
           } + 4

    }
    fun part2(input: String): Int {

           return input.windowed(14).indexOfFirst {
               it.toSet().size == it.length
           } + 14

    }

//    fun part2(input: List<String>): Int {
//        return input.size
//    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput1("Day06_test")
//    check(part1(testInput).also(::println) == 5)
    check(part2(testInput).also(::println) == 19)

    val input = readInput1("Day06")
    println(part1(input))
    println(part2(input))
}
