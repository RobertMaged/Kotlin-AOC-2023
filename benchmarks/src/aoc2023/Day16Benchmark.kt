package src.aoc2023

import aoc2023.Day16
import kotlinx.benchmark.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.Fork
import utils.Vertex
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day16Benchmark {
    val today = Day16

    private val data = with(today) {
        val startPoints = buildList<Day16.VertexToDir> {
            val cols = input[0].size
            val rows = input.size
            for (i in 0..<rows) {
                for (j in 0..<cols) {
                    if (i in 1..rows - 2 && j in 1..cols - 2)
                        continue

                    if (i == 0)
                        add(Vertex(j, i) to Day16.Dir.down)
                    else if (i == rows - 1)
                        add(Vertex(j, i) to Day16.Dir.up)

                    if (j == 0)
                        add(Vertex(j, i) to Day16.Dir.right)
                    else if (j == cols - 1)
                        add(Vertex(j, i) to Day16.Dir.left)
                }
            }
        }

        return@with startPoints + startPoints
    }

    @Benchmark
    fun normalIteration(): Unit = with(today) {

        data.forEach { input.countEnergized(it) }

    }

    @Benchmark
    fun coroutinesParallel(): Unit = with(today) {

        runBlocking {
            data.map { launch(Dispatchers.Default) { input.countEnergized(it) } }.joinAll()

        }

    }

    @Benchmark
    fun streamsParallel(): Unit = with(today) {

        data.parallelStream().forEach { input.countEnergized(it) }

    }


}