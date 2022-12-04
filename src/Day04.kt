fun main() {
    fun part1(input: List<String>): Int {
var count = 0
        for (line in input){
           val (a, b) = line.split(',').map { val split = it.split('-')
               split.first().toInt() to split[1].toInt()
           }
//                when{
//                    a[0] <= b[0] && a[2] >= b[2] -> count++
//                    b[0] <= a[0] && b[2] >= a[2] -> count++
//                    else -> Unit
//                }
        when{
                    a.first <= b.first && a.second >= b.second -> count++
                    b.first <= a.first&& b.second >= a.second -> count++
                    else -> Unit
                }


        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (line in input){
            val (a, b) = line.split(',').map { val split = it.split('-')
                split.first().toInt() to split[1].toInt()
            }

            if (
                ((b.first.. b.second) intersect (a.first..a.second)).any()
                )
                count++



        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput).also(::println) == 2)
    check(part2(testInput).also(::println) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
