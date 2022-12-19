package d19

import readInput
import java.util.*

// > 1175
// > 1183

fun part1b(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        if (line.isBlank()) continue
        val (idx, bp) = readBluePrint(line)
        println(bp)
        val score = trailB(bp, 0, 0, 0, 1, 0, 0, 0, 24, 0)
        println("Blueprint $idx -> $score geodes : score = ${idx * score}")
        sum += idx * score
    }
    return sum
}
fun part2b(input: List<String>): Int {
    var prod = 1
    for (line in input) {
        if (line.isBlank()) continue
        val (idx, bp) = readBluePrint(line)
        if (bp.idx > 3) break
        println(bp)
        val score = trailB(bp, 0, 0, 0, 1, 0, 0, 0, 32, 0)
        println("Blueprint $idx -> $score geodes : score = ${idx * score}")
        prod *= score
    }
    return prod
}

var orePrice = 1
var clayPrice = 1
var obsPrice = 1
var geodePrice = 1000
var oreRobotPrice = 1
var clayRobotPrice = 1
var obsRobotPrice = 1
var geoRobotPrice = 1
data class State(
    val ore: Int,
    val clay: Int,
    val obs: Int,
    val geodes: Int,
    val oreRobots: Int,
    val clayRobots: Int,
    val obsRobots: Int,
    val geoRobots: Int,
    val timeLeft: Int,
) : Comparable<State> {
    private val score = timeLeft + orePrice*ore + clayPrice*clay + obsPrice*obs + geodePrice*geodes +
            oreRobotPrice*oreRobots + clayRobotPrice*clayRobots + obsRobotPrice*obsRobots + geoRobotPrice*geoRobots

    override fun compareTo(other: State): Int = other.score.compareTo(score)
}

fun trailB(
    bp: Blueprint,
    ore: Int,
    clay: Int,
    obs: Int,
    oreRobots: Int,
    clayRobots: Int,
    obsRobots: Int,
    geoRobots: Int,
    timeLeft: Int,
    geodes: Int
): Int {

    oreRobotPrice = bp.oreCostOre * orePrice
    clayRobotPrice = bp.clayCostOre * orePrice
    clayPrice = clayRobotPrice
    obsRobotPrice = bp.obsCostOre * orePrice + bp.obsCostClay * clayPrice
    obsPrice = obsRobotPrice
    geoRobotPrice = bp.geoCostOre * orePrice + bp.geoCostObs * obsPrice

    val queue = PriorityQueue<State>()
    queue.add(State(ore, clay, obs, geodes, oreRobots, clayRobots, obsRobots, geoRobots, timeLeft))
    var max = 0
    val visited = mutableSetOf<State>()
    while (queue.isNotEmpty()) {
        val state = queue.poll()
        if (state in visited)
            continue
        else
            visited.add(state)
        if (state.timeLeft == 0) {
            if (state.geodes > max) {
                println("Max geodes = ${state.geodes}")
                max = state.geodes
            }
            continue
        }
        if (state.ore >= bp.geoCostOre && state.obs >= bp.geoCostObs) // if geodeRobot possible, go
            queue.add(
                State(
                    state.ore + state.oreRobots - bp.geoCostOre,
                    state.clay + state.clayRobots,
                    state.obs + state.obsRobots - bp.geoCostObs,
                    state.geodes + state.geoRobots,
                    state.oreRobots,
                    state.clayRobots,
                    state.obsRobots,
                    state.geoRobots + 1,
                    state.timeLeft - 1,
                )
            )
        else if (state.ore >= bp.obsCostOre && state.clay >= bp.obsCostClay) // hypothesis : if obsidianRobot possible, go
            queue.add(
                State(
                    state.ore + state.oreRobots - bp.obsCostOre,
                    state.clay + state.clayRobots - bp.obsCostClay,
                    state.obs + state.obsRobots,
                    state.geodes + state.geoRobots,
                    state.oreRobots,
                    state.clayRobots,
                    state.obsRobots + 1,
                    state.geoRobots,
                    state.timeLeft - 1,
                )
            )
        else {
            if (state.ore >= bp.oreCostOre)
                queue.add(
                    State(
                        state.ore + state.oreRobots - bp.oreCostOre,
                        state.clay + state.clayRobots,
                        state.obs + state.obsRobots,
                        state.geodes + state.geoRobots,
                        state.oreRobots + 1,
                        state.clayRobots,
                        state.obsRobots,
                        state.geoRobots,
                        state.timeLeft - 1,
                    )
                )
            if (state.ore >= bp.clayCostOre)
                queue.add(
                    State(
                        state.ore + state.oreRobots - bp.clayCostOre,
                        state.clay + state.clayRobots,
                        state.obs + state.obsRobots,
                        state.geodes + state.geoRobots,
                        state.oreRobots,
                        state.clayRobots + 1,
                        state.obsRobots,
                        state.geoRobots,
                        state.timeLeft - 1,
                    )
                )
            queue.add(
                State(
                    state.ore + state.oreRobots,
                    state.clay + state.clayRobots,
                    state.obs + state.obsRobots,
                    state.geodes + state.geoRobots,
                    state.oreRobots,
                    state.clayRobots,
                    state.obsRobots,
                    state.geoRobots,
                    state.timeLeft - 1
                )
            )
        }
    }
    return max
}

fun main() {
    //val input = readInput("d19/test")
    val input = readInput("d19/input1")

    //println(part1b(input))
    println(part2b(input))
}

