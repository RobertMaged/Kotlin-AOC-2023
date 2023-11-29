package src.aoc2022

import aoc2022.Day21
import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork
import java.util.concurrent.TimeUnit


@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day21Benchmark {
    val today = Day21

    @Benchmark
    fun normalRecursive(): Unit = with(today) {
        input.graphYelling().yell("root")
    }

    @Benchmark
    fun heapRecursive(): Unit = with(today) {
        input.graphYelling().yell()
    }
    @Benchmark
    fun part2StackRecursive(): Unit = with(today){
            val original = input.graphYelling()
            original["root"] = original.getValue("root").replace('+', '=')

            var minHumn = 1L
            var maxHumn = 100000000000000
            var humn: Long

            do {
                val graph = HashMap(original)
                humn = (maxHumn + minHumn) / 2

                graph["humn"] = "$humn"

                when (graph.yell("root")) {
                    -1L -> maxHumn = humn - 1
                    1L -> minHumn = humn + 1
//                0L -> break
                }

            } while (graph["root"]!!.toInt() != 0)


    }
    @Benchmark
    fun part2HeapRecursive(): Unit = with(today){
            val original = input.graphYelling()
            original["root"] = original.getValue("root").replace('+', '=')

            var minHumn = 1L
            var maxHumn = 100000000000000
            var humn: Long

            do {
                val graph = HashMap(original)
                humn = (maxHumn + minHumn) / 2

                graph["humn"] = "$humn"

                when (graph.yell()) {
                    -1L -> maxHumn = humn - 1
                    1L -> minHumn = humn + 1
//                0L -> break
                }

            } while (graph["root"]!!.toInt() != 0)


    }

}