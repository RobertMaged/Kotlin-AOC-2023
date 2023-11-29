import kotlin.math.abs

fun main() {

    class Rope() {
        var headX: Int = 0
            set(value) {

                if (abs(tailX - value) > 1)
                    when {
                        headY == tailY -> tailX += (value - field) // if (value > 0) tailX += value else tailX -= value
                        else -> {
                            tailY = headY
                            tailX += (value - field);
//                        tailY = field; tailX = tailY - 1
                        }
                    }
                field = value
                vistedTail.add(tailX to tailY)
                /*
                  while (abs(tailX - field) > 1)
                                    when {
                                        headY == tailY -> tailX += if (value > 0) 1 else -1 // if (value > 0) tailX += value else tailX -= value
                                        else -> {
                                            tailY = headY
                                            tailX += if (value > 0) 1 else -1;
                //                        tailY = field; tailX = tailY - 1
                                        }
                                    }
                */


            }
        var headY: Int = 0
            set(value) {


                if (abs(tailY - value) > 1)
                    when {
                        //same row or column
                        headX == tailX -> tailY += (value - field)
                        //adjecent
                        else -> {
                            tailX = headX
                            tailY += (value - field);
                        }
                    }
                field = value
                vistedTail.add(tailX to tailY)

                /*
                    while (abs(tailY - field) > 1)
                        when {
                            headX == tailX -> tailY += if (value > 0) 1 else -1; // if (value > 0) tailY += value else tailY -= value
                            else -> {tailX = headX
                                tailX = headX
                                tailY += if (value > 0) 1 else -1;
                            }
                        }*/

            }
        private var tailX: Int = 0
        private var tailY: Int = 0

        val vistedTail = hashSetOf(0 to 0) //x, y


    }

    /*
    If the head is ever two steps directly up, down, left, or right from the tail,
     the tail must also move one step in that direction so it remains close enough:

     Otherwise, if the head and tail aren't touching and aren't in the same row or column,
      the tail always moves one step diagonally to keep up:
     */
    fun part1(input: List<String>): Int {

        val moves = input.map { line ->
            val (direc, count) = line.split(" ")
            direc to count.toInt()
        }


        val tracker = Rope()

//        for (m in moves)
//            when (m.first) {
//                "U" -> tracker.headY = m.second
//                "D" -> tracker.headY = -m.second
//                "R" -> tracker.headX = m.second
//                "L" -> tracker.headX = -m.second
//            }
        for (m in moves)
            repeat(m.second) {
                when (m.first) {
                    "U" -> tracker.headY++
                    "D" -> tracker.headY--
                    "R" -> tracker.headX++
                    "L" -> tracker.headX--
                }
            }


        return tracker.vistedTail.size
    }

    data class Rope3(val n: Int = 9) {
        var headX: Int = 0

                    set(value) {
                                if (child == null) {
                                    field = value
                                    vistedTail.add(field to headY)
                                    return
                                }
                if (child != null && abs(child.headX - value) > 1)
                    when {
                        headY == child.headY -> {
                            child.headX += (value - field)
                            //                            child.headX.plus((value - field))
                        }

                        else -> {
                            child.headY = headY
                            child.headX += (value - field);
                            //
                            //                            child.headY = headY
                            //                            child.headX.plus((value - field))
                        }
                    }
                field = value
                vistedTail.add(headX to headY)

                //                child?.let { vistedTail.add(it.headX to it.headY)}


            }
        var headY: Int = 0
            set(value) {

                                if (child == null){
                                    field = value
                                    vistedTail.add(headX to field)
                                    return
                                }

                if (child != null && abs(child.headY - value) > 1)
                    when {
                        //same column
                        headX == child.headX -> child.headY += (value - field)
                        //adjecent
                        else -> {
                            child.headX = headX
                            child.headY += (value - field);
                        }
                    }
                field = value
                vistedTail.add(headX to headY)
                //                    vistedTail.add(child.headX to child.headY)
                //                child?.let { vistedTail.add(it.headX to it.headY)}


//                while (abs(tailY - field) > 1)
//                    when {
//                        headX == tailX -> tailY += if (value > 0) 1 else -1; // if (value > 0) tailY += value else tailY -= value
//                        else -> {
//                            tailX = headX
//                            tailX = headX
//                            tailY += if (value > 0) 1 else -1;
//                        }
//                    }


            }

  /*      fun moveVertical(value: Int) {
            headY += value

            if (child != null && abs(child.headY - headY) > 1)
                when {
                    //same column
                    headX == child.headX ->
                        //                        child.headY += (headY + value)
                        child.moveVertical(value)
                    //adjecent
                    abs(headX - child.headX) > 1 -> {
                        //                        child.headX = headX
                        //                        child.headY += (headY + value);
                        //                        child.headX = headX + value
                        child.moveHorizontal(headX - child.headX)
                        child.moveVertical(value)
                    }

                    else -> child.moveVertical(value)

                }
            vistedTail.add(headX to headY)
        }

        fun moveHorizontal(value: Int) {
            headX += value
            if (child != null && abs(child.headX - headX) > 1)
                when {
                    headY == child.headY -> {
                        //                        child.headX += (headX + value)
                        child.moveHorizontal(value)
                    }

                    abs(headY - child.headY) > 1 -> {
                        //                        child.headY = headY
                        //                        child.headX += (headX + value);

                        //                        child.headY = headY + value
                        child.moveVertical(headY - child.headY)
                        child.moveHorizontal(value)
                        //                            child.headY = headY
                        //                            child.headX.plus((value - field))
                    }

                    else -> child.moveVertical(value)
                }
            vistedTail.add(headX to headY)
        }*/

        //        private var tailX: Int = 0
//        private var tailY: Int = 0
        val child: Rope3? = if (n - 1 > -1)
            Rope3(n - 1)
        else
            null

        val vistedTail = hashSetOf(0 to 0) //x, y

        operator fun get(n: Int): Rope3 {
            var tail = this
            repeat(n) {
                tail = tail.child!!
            }
            return tail
        }

        fun last(): Rope3 {
            var x = this
            while (x.child != null)
                x = x.child!!
            return x
        }
    }


    fun part2(input: List<String>, tails: Int): Int {

        val moves = input.map { line ->
            val (direc, count) = line.split(" ")
            direc to count.toInt()
        }


//        val tails = 1
        val tracker = Rope()
        val tracker3 = Rope3(tails)

        val tail = tracker3.last()
        val visted = mutableSetOf(0 to 0)
        for (m in moves) {
            repeat(m.second) {
                when (m.first) {
                    "U" -> tracker3.headY++
                    "D" -> tracker3.headY--
                    "R" -> tracker3.headX++
                    "L" -> tracker3.headX--
                }
/*                when (m.first) {
                    "U" -> tracker3.moveVertical(1)
                    "D" -> tracker3.moveVertical(-1)
                    "R" -> tracker3.moveHorizontal(1)
                    "L" -> tracker3.moveHorizontal(-1)
                }*/
                when (m.first) {
                    "U" -> tracker.headY++
                    "D" -> tracker.headY--
                    "R" -> tracker.headX++
                    "L" -> tracker.headX--
                }
                visted.add(tail.headX to tail.headY)
            }
        }
//        var child: Rope2?= tracker.child
//
//        while (child?.child != null) {
//            child = child.child
//        }
//
//        return child?.vistedTail?.size ?: 0// tracker.vistedTail.size
        return visted.size // tracker3[tails].vistedTail.size
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
//    check(part1(testInput).also(::println) == 13)
//    check(part2(testInput, 1).also(::println) == 13)
//    check(part2(testInput, 9).also(::println) == 1)
    val testInput2 = readInput("Day09_test_part2")
    check(part2(testInput2, 9).also(::println) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input, 9))
}

/*
data class Rope2(val n: Int = 9) {
    var headX: Int = 0
        set(value) {
            if (child == null) {
                field = value
//                    vistedTail.add(headX to headY)
                return
            }
            if (abs((child?.headX ?: value) - value) > 1)
                when {
                    headY == child!!.headY -> {
                        child.headX += (value - field)
//                            child.headX.plus((value - field))
                    }

                    else -> {
                        child.headY = headY
                        child.headX += (value - field);
//
//                            child.headY = headY
//                            child.headX.plus((value - field))
                    }
                }
            field = value
            child?.let { vistedTail.add(it.headX to it.headY) }
//                    vistedTail.add(headX to headY)


        }
    var headY: Int = 0
        set(value) {

            if (child == null) {
                field = value
//                    vistedTail.add(headX to headY)
                return
            }

            if (abs((child?.headY ?: value) - value) > 1)
                when {
                    //same column
                    headX == child!!.headX -> child.headY += (value - field)
                    //adjecent
                    else -> {
                        child.headX = headX
                        child.headY += (value - field);
                    }
                }
            field = value
            vistedTail.add(child.headX to child.headY)
//                    vistedTail.add(headX to headY)
//                child?.let { vistedTail.add(it.headX to it.headY)}


            */
/*
                while (abs(tailY - field) > 1)
                    when {
                        headX == tailX -> tailY += if (value > 0) 1 else -1; // if (value > 0) tailY += value else tailY -= value
                        else -> {tailX = headX
                            tailX = headX
                            tailY += if (value > 0) 1 else -1;
                        }
                    }*//*


        }

    //        private var tailX: Int = 0
//        private var tailY: Int = 0
    val child: Rope2? = if (n - 1 > -1)
        Rope2(n - 1)
    else
        null

    val vistedTail = hashSetOf(0 to 0) //x, y

    operator fun get(n: Int): Rope2 {
        var tail = this
        repeat(n) {
            tail = tail.child!!
        }
        return tail
    }
}
*/
