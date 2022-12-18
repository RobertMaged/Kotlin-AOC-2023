private data class Coor3D(val x: Int, val y: Int, val z: Int)

fun main() {
    fun part1(input: List<String>): Int {


        val cubes = input.map {
            val cube = it.split(',').map { it.toInt() }
            Coor3D(cube[0], cube[1], cube[2])
        }
        return cubes.sumOf {
            val l = listOf(
                it.copy(x = it.x - 1),
                it.copy(x = it.x + 1),
                it.copy(y = it.y - 1),
                it.copy(y = it.y + 1),
                it.copy(z = it.z - 1),
                it.copy(z = it.z + 1),
            )
            return@sumOf 6 - (l intersect cubes).size
        }

    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day18_test")
    val input = readInput("Day18")

    part1(testInput).checkEquals(64)
    part1(input)
        .sendAnswer(part = 1, day = "18", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "18", year = 2022)
}
