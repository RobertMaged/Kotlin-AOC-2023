import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>, y: Int): Int {

        val sToBMap = input.associate { line ->
            val (s, b) = line.split(" closest ").take(2).map {
                val x = it.substringAfter("x=").takeWhile { it.isDigit() || it == '-' }
                val y = it.substringAfter("y=").takeWhile { it.isDigit() || it == '-' }
                Vertex(x.toInt(), y = y.toInt())
            }
            s to b
        }

        val counted = mutableSetOf<Int>()
        val becons = mutableSetOf<Int>()
        // steps from s to its becon , then add s+steps see it is in y
        val score = sToBMap/*.filter { it.key == Vertex(8, 7) }*/.forEach { (s, b) ->
            val xSteps = abs(s.x - b.x)
            val ySteps = abs(s.y - b.y)
            if (y == b.y) {
                becons += b.x
                counted += (s.x - xSteps..s.x+xSteps)//.filter { it !in becons }
                return@forEach
            }

//           val steps = xSteps + ySteps
            if (/*ySteps == 0 && */s.y == y) {
                counted += (s.x-xSteps..s.x+xSteps)//.filter { it !in becons }
            }else if (/*xSteps == 0 &&*/ y in s.y - (ySteps+xSteps)..s.y + (ySteps+xSteps)) {
//                counted += (s.x - xSteps..s.x+ySteps).filterNot { it in becons }
                counted += (s.x-xSteps..s.x+xSteps)//.filter { it !in becons }
            } else{
                val x = 0
            }
        }

        return (counted -becons) .size
    }

    fun part1D(input: List<String>, y: Int): Int {
        var score = 0
        var yRange = (-100..100) // (-431081..4114070)
        val xrange = (-100..100)//(-138_318..4_000_000)

        var xR = (0..0)
        var yR = (0..0)

        val sVers = mutableListOf<Vertex>()
        val bVers = mutableListOf<Vertex>()


        val map = Array(yRange.count()) { IntArray(xrange.count()) { 0 } }
        for (line in input) {

            val (s, b) = line.split(" closest ").take(2).map {
                val x = it.substringAfter("x=").takeWhile { it.isDigit() || it == '-' }
                val y = it.substringAfter("y=").takeWhile { it.isDigit() || it == '-' }
                Vertex(x.toInt(), y = y.toInt())
            }
            if (min(s.x, b.x) < xR.first)
                xR = min(s.x, b.x)..xR.last
            else if (max(s.x, b.x) > xR.last)
                xR = xR.first..max(s.x, b.x)

            if (min(s.y, b.y) < yR.first)
                yR = min(s.y, b.y)..yR.last
            else if (max(s.y, b.y) > yR.last)
                yR = yR.first..max(s.y, b.y)
            sVers.add(s)
            bVers.add(b)
        }
        val sToBMap = sVers.zip(bVers).toMap()
        val sScannedVer = mutableMapOf<Vertex, Set<Vertex>>()

        var inBounds = { s: Vertex ->
//            listOf(
//                Vertex(xR.first, yR.first), Vertex(xR.last, yR.first),
//                Vertex(xR.first, yR.last), Vertex(xR.last, yR.last)
//            )
            s.y in yR && s.x in xR
        }

        sToBMap.filter { it.key == Vertex(8, 7) }.map { (s, b) ->
            val stepsTaken = abs(b.x - s.x) + abs(b.y - s.y)
            val list = mutableListOf<Vertex>()

            val que = ArrayDeque<Vertex>()
            while (que.any())

                t@ for (row in -stepsTaken..stepsTaken) {

                    for (col in -abs(stepsTaken % if (row == 0) 1 else row)..abs(stepsTaken % if (row == 0) 1 else row)) {
                        if (s.x + col == b.x && s.y + row == b.y)
                            break@t
                        list += Vertex(s.x + col, s.y + row)
                    }
                }
            val x = 0
        }

        /* sToBMap.forEach { s, b ->
             val que = ArrayDeque<Vertex>(listOf(s).filter(inBounds))
             val visted = mutableSetOf<Vertex>()

             while (que.any()) {
                 val v = que.removeFirst()
                 if (v in visted) continue
                 visted += v

                 if (v == b) {
                     visted
                     sScannedVer[s] = visted//.take(visted.size -1).toSet()
                     println("one s finish")
                     return@forEach
                 } else {
                     listOf(v.up(), v.down(), v.left(), v.right()).filter(inBounds).forEach(que::addLast)
                 }
             }
         }

         val s = (sScannedVer.keys.filter {
             it.y == y
         } +
                 sScannedVer.values.flatten().filter {
                     it.y == y
                 })
             .toSet()*/
//
//val s = visted.filter { it.y == y }
//    .filter { it !in sToBMap.values }
        return 0// s.size//  map[y].let { it.size - it.count{it != 1} }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    part1(testInput, 10).checkEquals(26)
//    part1D(input, 2_000_000).alsoPrintln()
//        .sendAnswer(part = 1, day = "15", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "15", year = 2022)
}

/*

import kotlin.math.abs

fun main() {

    fun findDistressedBeacon(sensorToDistance: List<Day15Input>, maxSize: Int): YPoint {
        (0..maxSize).forEach { y ->
            val ranges = sensorToDistance.toReverseRanges(y, maxSize)
            if (ranges.isNotEmpty()) {
                return ranges.first().start
            }
        }
        error("Can't find distressed beacon")
    }

    fun readInput(input: List<String>): List<Day15Input> {
        val sensorToDistance = input.map { s ->
            val (sensor, beacon) = "(x=|y=)(-?\\d+)"
                .toRegex().findAll(s)
                .map { it.groups[2]!!.value.toInt() }
                .chunked(2)
                .map { (x, y) -> YPoint(x, y) }
                .toList()
            val distance = sensor.dist(beacon)
            Day15Input(sensor, distance, beacon)
        }
        return sensorToDistance
    }

    fun part1(input: List<String>, row: Int): Int {
        val sensorToBeacon = readInput(input)
        val sensors = sensorToBeacon.map { it.sensor }.toSet()
        val beacons = sensorToBeacon.map { it.beacon }.toSet()
        val ranges = sensorToBeacon.toRanges(row)
        val minX = ranges.map { it.start.x }.min()
        val maxX = ranges.map { it.endInclusive.x }.max()

        return (minX..maxX).filter { x ->
            val point = YPoint(x, row)
            !sensors.contains(point) && !beacons.contains(point)
        }.size
    }

    fun part2(input: List<String>, maxSize: Int): Long {
        val sensorToDistance = readInput(input)
        val (x, y) = findDistressedBeacon(sensorToDistance, maxSize)
        return x * 4000000L + y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    println(part1(testInput, 10))
//    println(part2(testInput, 20))
    check(part1(testInput, 10) == 26)
//    check(part2(testInput, 20) == 56000011L)

    val input = readInput("Day15")
    println(part1(input, 2000000))
//    println(part2(input, 4000000))
}

private fun YPoint.dist(other: YPoint): Int {
    return abs(x - other.x) + abs(y - other.y)
}

private data class Day15Input(val sensor: YPoint, val distance: Int, val beacon: YPoint)

private data class YPoint(val x: Int, val y: Int): Comparable<YPoint> {
    override fun compareTo(other: YPoint): Int {
        return x - other.x
    }
}

private fun YPoint.prev(): YPoint {
    return YPoint(x - 1, y)
}

private fun YPoint.next(): YPoint {
    return YPoint(x + 1, y)
}

private fun List<Day15Input>.toRanges(row: Int): List<ClosedRange<YPoint>> {
    return mapNotNull { (sensor, distance) ->
        val delta = distance - abs(sensor.y - row)
        if (delta >= 0) {
            YPoint(sensor.x - delta, row)..YPoint(sensor.x + delta, row )
        } else {
            null
        }
    }
}

private fun List<Day15Input>.toReverseRanges(row: Int, maxSize: Int): List<ClosedRange<YPoint>> {
    val ranges = toRanges(row)
    return ranges.fold(listOf(YPoint(0, row)..YPoint(maxSize, row))) { acc, range ->
        acc.subtract(range)
    }
}

private fun List<ClosedRange<YPoint>>.subtract(range: ClosedRange<YPoint>): List<ClosedRange<YPoint>> {
    return filter {
        !(range.contains(it.start) && range.contains(it.endInclusive))
    }.flatMap {
        if (range.contains(it.start)) {
            listOf(range.endInclusive.next()..it.endInclusive)
        } else if (range.contains(it.endInclusive)) {
            listOf(it.start..range.start.prev())
        } else if (it.contains(range.start)) {
            listOf(
                it.start..range.start.prev(),
                range.endInclusive.next()..it.endInclusive
            )
        } else {
            listOf(it)
        }
    }
}
*/
