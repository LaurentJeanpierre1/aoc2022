package d19
// Does not work

import readInput
import java.util.Scanner

data class Blueprint(
    val idx: Int,
    val oreCostOre: Int,
    val clayCostOre: Int,
    val obsCostOre: Int,
    val obsCostClay: Int,
    val geoCostOre: Int,
    val geoCostObs: Int
)

enum class Built {
    Ore {
        override fun newOre() = 1
    },
    Clay {
        override fun newClay() = 1
    },
    Obs {
        override fun newObs() = 1
    },
    Geode {
        override fun newGeode() = 1
    },
    None;

    open fun newOre() = 0
    open fun newClay() = 0
    open fun newObs() = 0
    open fun newGeode() = 0
}

fun readBluePrint(line: String): Pair<Int, Blueprint> {
    val scan = Scanner(line)
    scan.useDelimiter("[ :]+")
    assert(scan.next() == "Blueprint")
    val idx = scan.nextInt()
    scan.next()
    assert(scan.next() == "ore")
    scan.next()
    scan.next()
    val oreCostOre = scan.nextInt()
    assert(scan.next() == "ore")

    scan.next()
    assert(scan.next() == "clay")
    scan.next()
    scan.next()
    val clayCostOre = scan.nextInt()
    assert(scan.next() == "ore")

    scan.next()
    assert(scan.next() == "obsidian")
    scan.next()
    scan.next()
    val obsCostOre = scan.nextInt()
    assert(scan.next() == "ore")
    scan.next()
    val obsCostClay = scan.nextInt()
    assert(scan.next() == "clay")

    scan.next()
    assert(scan.next() == "geode")
    scan.next()
    scan.next()
    val geoCostOre = scan.nextInt()
    assert(scan.next() == "ore")
    scan.next()
    val geoCostObs = scan.nextInt()
    assert(scan.next() == "obsidian")

    val bp = Blueprint(idx, oreCostOre, clayCostOre, obsCostOre, obsCostClay, geoCostOre, geoCostObs)
    return Pair(idx, bp)
}


fun part1(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        val (idx, bp) = readBluePrint(line)
        val score = trail(bp, 0, 0, 0, 1, 0, 0, 0, 24, 0)
        println("Blueprint $idx -> $score geodes : score = ${idx * score}")
        sum += idx * score
    }
    return sum
}

fun trail(
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
    if (timeLeft < 0) return geodes
    var max = trailFin(
        bp,
        ore,
        clay,
        obs,
        oreRobots,
        clayRobots,
        obsRobots,
        geoRobots,
        timeLeft,
        geodes,
        Built.None
    )
    if (timeLeft == 0) return max // no need to build
    if (ore >= bp.oreCostOre)
        max = maxOf(max, trailFin(
            bp,
            ore - bp.oreCostOre,
            clay,
            obs,
            oreRobots,
            clayRobots,
            obsRobots,
            geoRobots,
            timeLeft,
            geodes,
            Built.Ore
        ))
    if (ore >= bp.clayCostOre)
        max = maxOf(max,trailFin(
            bp,
            ore - bp.clayCostOre,
            clay,
            obs,
            oreRobots,
            clayRobots,
            obsRobots,
            geoRobots,
            timeLeft,
            geodes,
            Built.Clay
        ))
    if (ore >= bp.obsCostOre && clay >= bp.obsCostClay)
        max = maxOf(max, trailFin(
            bp,
            ore - bp.obsCostOre,
            clay - bp.obsCostClay,
            obs,
            oreRobots,
            clayRobots,
            obsRobots,
            geoRobots,
            timeLeft,
            geodes,
            Built.Obs
        ))
    if (ore >= bp.geoCostOre && obs >= bp.geoCostObs)
        max = maxOf(max, trailFin(
            bp,
            ore - bp.geoCostOre,
            clay,
            obs,
            oreRobots,
            clayRobots,
            obsRobots - bp.geoCostObs,
            geoRobots,
            timeLeft,
            geodes,
            Built.Geode
        ))

    return max
}

fun trailFin(
    bp: Blueprint,
    ore: Int,
    clay: Int,
    obs: Int,
    oreRobots: Int,
    clayRobots: Int,
    obsRobots: Int,
    geoRobots: Int,
    timeLeft: Int,
    geodes: Int,
    built : Built
): Int {
    return trail(
        bp,
        ore + oreRobots,
        clay + clayRobots,
        obs + obsRobots,
        oreRobots + built.newOre(),
        clayRobots + built.newClay(),
        obsRobots + built.newObs(),
        geoRobots + built.newGeode(),
        timeLeft - 1,
        geodes + geoRobots
    )
}

fun main() {
    val input = readInput("d19/test")
    //val input = readInput("d19/input1")

    println(part1(input))
}

