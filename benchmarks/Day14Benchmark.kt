import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
class Day14Benchmark {
    lateinit var input: List<String>
    lateinit var today: Day14

    @Setup
    fun setup() {
        input = Day14.input
        today = Day14
    }

    @Benchmark
    fun part1test(): Int{
        return today.part1(input)
    }
}