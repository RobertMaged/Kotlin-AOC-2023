import kotlin.math.max

private sealed class Robot3(val name: String) {
    data class Ore(val oreCost: Int) : Robot3("Ore")
    data class Clay(val oreCost: Int) : Robot3("Clay")
    data class Obsidian(val oreCost: Int, val clayCost: Int) : Robot3("Obsidian")
    data class Geode(val oreCost: Int, val obsidianCost: Int) : Robot3("Geode")
}

private sealed class Robot(val name: String, val costs: Costs, val inServiceCount:Int, val inventory: Int){
    class Ore( oreCost: Int, inServiceCount: Int, inventory: Int): Robot("Ore",Costs(oreCost,0,0) , inServiceCount, inventory)
    class Clay( oreCost: Int, inServiceCount: Int, inventory: Int): Robot("Clay",Costs(oreCost,0, 0) , inServiceCount, inventory)
    class Obsidian( oreCost: Int,  clayCost: Int, inServiceCount: Int, inventory: Int): Robot("Obsidian",Costs(oreCost,clayCost, 0) , inServiceCount, inventory)
    class Geode( oreCost: Int,  obsidianCost: Int, inServiceCount: Int, inventory: Int): Robot("Geode",Costs(oreCost,0, obsidianCost) , inServiceCount, inventory)

    fun copy(inServiceCount:Int= this.inServiceCount, inventory: Int= this.inventory) = when(this){
        is Ore -> Ore(costs.oreCost, inServiceCount, inventory)
        is Clay -> Clay(costs.oreCost, inServiceCount, inventory)
        is Obsidian -> Obsidian(costs.oreCost,costs.clayCost, inServiceCount, inventory)
        is Geode -> Geode(costs.oreCost, costs.obsidianCost, inServiceCount, inventory)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Robot) return false

        if (name != other.name) return false
        if (costs != other.costs) return false
        if (inServiceCount != other.inServiceCount) return false
        if (inventory != other.inventory) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + costs.hashCode()
        result = 31 * result + inServiceCount
        result = 31 * result + inventory
        return result
    }

    override fun toString(): String {
        return "Robot(name=$name, inServiceCount=$inServiceCount, inventory=$inventory)"
    }


}
private data class Costs(val oreCost: Int, val clayCost: Int, val obsidianCost: Int)

private data class Factory(val robot: Robot3, var inServiceCount: Int = 0, var inventory: Int = 0){
    fun collect(){
        inventory+= inServiceCount
    }
}

private data class Costs2(val oreCost: Int, val clayCost: Int, val obsOreCost: Int, val obsClayCost: Int, val geoOreCost: Int, val geoObsCost: Int){
    val maxOre = listOf(oreCost, clayCost, obsOreCost, geoOreCost).max()
}
private data class FactoryState2(val time: Int,
                                 val oreInventory: Int,val clayInventory: Int,val obsInventory: Int,val geoInventory: Int,
                                 val oreRobots : Int, val clayRobots : Int, val obsRobots : Int, val geoRobots : Int){


}

//private data class FactoryState(val time : Int, val map: Map<String, Robot>){
private data class FactoryState(val time : Int, val map: List<Robot>){

    companion object{
        var maxOre = 0
        var maxClay=0
        var maxObs = 0
    }

    fun canBuy(robot: Robot) : Boolean = when(robot){
        is Robot.Ore, is Robot.Clay -> oreRobot.inventory >= robot.costs.oreCost
        is Robot.Obsidian -> oreRobot.inventory >= robot.costs.oreCost && clayRobot.inventory >= robot.costs.clayCost
        is Robot.Geode -> oreRobot.inventory >= robot.costs.oreCost && obsidianRobot.inventory >= robot.costs.obsidianCost
    }
    fun shouldBuy(robot: Robot) : Boolean = when(robot){
        is Robot.Ore ->  map.maxOf { it.costs.oreCost } > maxOre
        is Robot.Clay -> obsidianRobot.costs.clayCost > maxClay
        is Robot.Obsidian -> geodeRobot.costs.obsidianCost > maxObs
        is Robot.Geode -> true
    }
    fun buyCopy(robot: Robot, time: Int = this.time): FactoryState {
        val x =map.toMutableList()
        when(robot){
            is Robot.Ore -> {
                x[0] = oreRobot.copy(oreRobot.inServiceCount+1, oreRobot.inventory - oreRobot.costs.oreCost)
                maxOre++
            }
            is Robot.Clay -> {
                x[0] = oreRobot.copy(oreRobot.inventory - clayRobot.costs.oreCost)
                x[1] = clayRobot.copy(clayRobot.inServiceCount +1)
                maxClay++
            }
            is Robot.Obsidian -> {
                x[0] = oreRobot.copy(oreRobot.inventory - obsidianRobot.costs.oreCost)
                x[1] = clayRobot.copy(clayRobot.inventory - obsidianRobot.costs.clayCost)
                x[2] = obsidianRobot.copy(obsidianRobot.inServiceCount +1)
                maxObs++
            }
            is Robot.Geode -> {
                x[0] = oreRobot.copy(oreRobot.inventory - geodeRobot.costs.oreCost)
                x[2] = obsidianRobot.copy(obsidianRobot.inventory - geodeRobot.costs.obsidianCost)
                x[3] = geodeRobot.copy(geodeRobot.inServiceCount +1)
            }

        }

        return FactoryState(time, x)
    }
    /*
        fun buyCopy(robot: Robot, time: Int = this.time): FactoryState {
            val x =map.toMutableMap()
             when(robot){
                is Robot.Ore -> {
                    x["Ore"] = oreRobot.copy(oreRobot.inServiceCount+1, oreRobot.inventory - oreRobot.costs.oreCost)
                }
                is Robot.Clay -> {
                    x["Ore"] = oreRobot.copy(oreRobot.inventory - clayRobot.costs.oreCost)
                    x["Clay"] = clayRobot.copy(clayRobot.inServiceCount +1)
                }
                is Robot.Obsidian -> {
                    x["Ore"] = oreRobot.copy(oreRobot.inventory - obsidianRobot.costs.oreCost)
                    x["Clay"] = clayRobot.copy(clayRobot.inventory - obsidianRobot.costs.clayCost)
                    x["Obsidian"] = obsidianRobot.copy(obsidianRobot.inServiceCount +1)
                }
                is Robot.Geode -> {
                    x["Ore"] = oreRobot.copy(oreRobot.inventory - geodeRobot.costs.oreCost)
                    x["Obsidian"] = obsidianRobot.copy(obsidianRobot.inventory - geodeRobot.costs.obsidianCost)
                    x["Geode"] = geodeRobot.copy(geodeRobot.inServiceCount +1)
                }

            }

            return FactoryState(time, x.toMap())
        }
    */
//
//    fun produceCopy(): FactoryState{
//       return FactoryState(
//         time+1,
////        map.mapValues { (_, robot) -> robot.copy(inventory = robot.inventory + robot.inServiceCount) }
//        map.map {  robot -> robot.copy(inventory = robot.inventory + robot.inServiceCount) }
//        )
    fun produceCopy(): FactoryState{
        var state = FactoryState(
            time+1,
            map.map {  robot -> robot.copy(inventory = robot.inventory + robot.inServiceCount) }
        )
        while (state.map.none { state.canBuy(it)} ){
            state = FactoryState(
                state.time+1,
                state.map.map {  robot -> robot.copy(inventory = robot.inventory + robot.inServiceCount) }
            )
        }
        return state
    }

}
private data class Blueprint(val id: Int, val factory: Map<String, Factory>) {
    var minutes = 0

    companion object {
        fun of(blueprintLine: String): Blueprint {
            val (id, oreRobCost, clayRobCost, obsRobOreCost, obsRobClayCost, geoRobOreCost, geoRobObsCost) =
                Regex("\\d+").findAll(blueprintLine).map { it.value.toInt() }.toList()

            val factoryMap = listOf(
                Factory(robot = Robot3.Ore(oreCost = oreRobCost), inServiceCount = 1),
                Factory(robot = Robot3.Clay(oreCost = clayRobCost)),
                Factory(robot = Robot3.Obsidian(oreCost = obsRobOreCost, clayCost = obsRobClayCost)),
                Factory(robot = Robot3.Geode(oreCost = geoRobOreCost, obsidianCost = geoRobObsCost))
            ).associateBy { it.robot.name }

            return Blueprint(id = id, factoryMap)
        }
    }
}
private data class Blueprint2(val id: Int, val factory: FactoryState) {

    companion object {
        fun of(blueprintLine: String): Blueprint2 {
            val (id, oreRobCost, clayRobCost, obsRobOreCost, obsRobClayCost, geoRobOreCost, geoRobObsCost) =
                Regex("\\d+").findAll(blueprintLine).map { it.value.toInt() }.toList()

            val factoryState = FactoryState(1,  listOf(
                Robot.Ore(oreCost = oreRobCost, 1, 0),
                Robot.Clay(oreCost = clayRobCost,0,0),
                Robot.Obsidian(oreCost = obsRobOreCost, obsRobClayCost, 0, 0),
                Robot.Geode(oreCost = geoRobOreCost, obsidianCost = geoRobObsCost, 0,0 )
            )
//                .associateBy { it.name }
            )
            return Blueprint2(id = id, factoryState)
        }
    }
}
private data class Blueprint3(val id: Int, val costs: Costs2, val factory: FactoryState2) {

    companion object {
        fun of(blueprintLine: String): Blueprint3 {
            val (id, oreRobCost, clayRobCost, obsRobOreCost, obsRobClayCost, geoRobOreCost, geoRobObsCost) =
                Regex("\\d+").findAll(blueprintLine).map { it.value.toInt() }.toList()

            val factoryState = FactoryState2(24,0,0,0,0,1,0,0,0)
            val costs = Costs2(oreRobCost, clayRobCost, obsRobOreCost, obsRobClayCost, geoRobOreCost, geoRobObsCost)
            return Blueprint3(id = id, costs, factoryState)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {


        return input.map(Blueprint3::of).sumOf { it ->
//            val queue =ArrayDeque(listOf(it.factory))
            val queue =ArrayDeque(listOf(it.factory))
            val seen = hashSetOf<FactoryState2>()
            var maxGeode = 0
//val flyWeight = hashMapOf<FactoryState, FactoryState>()
//            val flyWeight = object {
//                fun getOrPut(f: FactoryState, s: ()-> FactoryState) = s()
//            }

            while (queue.any()){
                val state = queue.removeFirst()

                var (t, ore, clay, obs, geo, oreRob, clayRob, obsRob, geoRob) = state

                maxGeode = max(maxGeode, state.geoInventory)
                if (state.time <1) continue

                if (oreRob >= it.costs.maxOre)
                oreRob = it.costs.maxOre
                if (clayRob >= it.costs.clayCost)
                clayRob = it.costs.clayCost
                if (obsRob >= it.costs.geoObsCost)
                obsRob = it.costs.geoObsCost
                if (ore >= t * it.costs.maxOre - oreRob * (t - 1))
                ore = t * it.costs.maxOre - oreRob * (t - 1)
                if (clay >= t * it.costs.clayCost - clayRob * (t - 1))
                clay = t * it.costs.clayCost - clayRob * (t - 1)
                if (obs >= t * it.costs.geoObsCost - obsRob * (t - 1))
                obs = t * it.costs.geoObsCost - obsRob * (t - 1)

                FactoryState2(t, ore, clay, obs, geo, oreRob, clayRob, obsRob, geoRob).takeIf { it !in seen  }?.also(seen::add) ?: continue


                queue.addLast(FactoryState2(t-1, ore + oreRob, clay + clayRob, obs + obsRob, geo + geoRob, oreRob, clayRob, obsRob, geoRob))
                if (ore >= it.costs.oreCost)  // buy ore
                queue.addLast(FactoryState2(t-1, ore - it.costs.oreCost + oreRob, clay + clayRob, obs + obsRob, geo + geoRob, oreRob + 1, clayRob, obsRob,
                    geoRob))
                if (ore >= it.costs.clayCost)
                queue.addLast(FactoryState2(t-1, ore - it.costs.clayCost + oreRob, clay + clayRob, obs + obsRob, geo + geoRob, oreRob, clayRob + 1, obsRob,
                    geoRob))
                if (ore >= it.costs.obsOreCost && clay >= it.costs.obsClayCost)
                queue.addLast(FactoryState2(t-1, ore - it.costs.obsOreCost + oreRob, clay - it.costs.obsClayCost + clayRob, obs + obsRob, geo + geoRob, oreRob,
                    clayRob, obsRob + 1, geoRob))
                if (ore >= it.costs.geoOreCost && obs >= it.costs.geoObsCost)
                queue.addLast(FactoryState2(t-1, ore - it.costs.geoOreCost + oreRob, clay + clayRob, obs - it.costs.geoObsCost + obsRob, geo + geoRob, oreRob,
                    clayRob, obsRob, geoRob + 1))

                seen.size.takeIf { it % 1000 == 0 }?.also{println("s: $it, q: ${queue.size}, b: $maxGeode, $state")}
            }

            it.id * maxGeode
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // parts execution
    val testInput = readInput("Day19_test")
    val input = readInput("Day19")

//    part1(testInput).checkEquals(33)
    part1(input)
        .alsoPrintln()
//        .sendAnswer(part = 1, day = "19", year = 2022)

    part2(testInput).checkEquals(TODO())
    part2(input)
        .sendAnswer(part = 2, day = "19", year = 2022)
}

private operator fun List<Int>.component6() = this[5]
private operator fun List<Int>.component7() = this[6]

private val FactoryState.oreRobot get() = map[0] as Robot.Ore
private val FactoryState.clayRobot get() = map[1] as Robot.Clay
private val FactoryState.obsidianRobot get() = map[2] as Robot.Obsidian
private val FactoryState.geodeRobot get() = map[3] as Robot.Geode
/*
private val FactoryState.oreRobot get() = map.getValue("Ore") as Robot.Ore
private val FactoryState.clayRobot get() = map.getValue("Clay") as Robot.Clay
private val FactoryState.obsidianRobot get() = map.getValue("Obsidian") as Robot.Obsidian
private val FactoryState.geodeRobot get() = map.getValue("Geode") as Robot.Geode
*/