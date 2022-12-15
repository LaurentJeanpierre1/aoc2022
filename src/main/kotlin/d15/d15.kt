package d15

import readInput
import java.util.Scanner
import kotlin.math.abs


fun part1(input: List<String>, row : Long): Int {
        val ranges = mutableListOf<LongRange>()
        val beacons = mutableSetOf<LongRange>()
        for (line in input) {
            val scan = Scanner(line)
            scan.useDelimiter("[=,:]")
            assert(scan.next() == "Sensor at x")
            val xS = scan.nextLong()
            assert(scan.next() == " y")
            val yS = scan.nextLong()
            assert(scan.next() == " closest beacon is at x")
            val xB = scan.nextLong()
            assert(scan.next() == " y")
            val yB = scan.nextLong()
            if (yB == row) {
                beacons.add(xB..xB)
            }
            val dist = abs(xS - xB) + abs(yS - yB)
            //println("($xS, $yS) -> ($xB, $yB) : dist = $dist")

            val dy = abs(row - yS)
            if (dy <= dist) {
                val minX = xS - (dist - dy)
                val maxX = xS + (dist - dy)
                //println(“Sensor covers row $row from $minX to $maxX")
                val rowRange = mutableListOf(minX..maxX)
                for (range in ranges) {
                    val ite = rowRange.listIterator()
                    while (ite.hasNext()) {
                        val r = ite.next()
                        if (r.start < range.start) {
                            if (r.endInclusive < range.first) {
                                // no intersect
                            } else {
                                val nr = r.start until range.first
                                ite.set(nr)
                            }
                            if (r.endInclusive > range.endInclusive) {
                                ite.add(range.endInclusive + 1..r.endInclusive)
                            }
                        } else if (r.start <= range.endInclusive) {
                            val nr = range.endInclusive + 1..r.endInclusive
                            ite.set(nr)
                        } else {
                            // no intersect
                        }
                    }
                }
                for (range in rowRange)
                    if (!range.isEmpty())
                        ranges.add(range)
            }
        }
        println(ranges)
        println(beacons)
    return ranges.sumOf { it.count() } - beacons.size
}

fun part2(input: List<String>): Long {
    for (row in 0L..4000000L) {
        val ranges = mutableListOf<LongRange>()
        val beacons = mutableSetOf<LongRange>()
        for (line in input) {
            val scan = Scanner(line)
            scan.useDelimiter("[=,:]")
            assert(scan.next() == "Sensor at x")
            val xS = scan.nextLong()
            assert(scan.next() == " y")
            val yS = scan.nextLong()
            assert(scan.next() == " closest beacon is at x")
            val xB = scan.nextLong()
            assert(scan.next() == " y")
            val yB = scan.nextLong()
            if (yB == row) {
                beacons.add(xB..xB)
            }
            val dist = abs(xS - xB) + abs(yS - yB)

            val dy = abs(row - yS)
            if (dy <= dist) {
                var minX = xS - (dist - dy)
                var maxX = xS + (dist - dy)
                if (minX < 0) minX = 0
                if (maxX > 4000000) maxX = 4000000
                val rowRange = mutableListOf(minX..maxX)
                for (range in ranges) {
                    val ite = rowRange.listIterator()
                    while (ite.hasNext()) {
                        val r = ite.next()
                        if (r.start < range.start) {
                            if (r.endInclusive < range.first) {
                                // no intersect
                            } else {
                                val nr = r.start until range.first
                                ite.set(nr)
                            }
                            if (r.endInclusive > range.endInclusive) {
                                ite.add(range.endInclusive + 1..r.endInclusive)
                            }
                        } else if (r.start <= range.endInclusive) {
                            val nr = range.endInclusive + 1..r.endInclusive
                            ite.set(nr)
                        } else {
                            // no intersect
                        }
                    }
                }
                for (range in rowRange)
                    if (!range.isEmpty())
                        ranges.add(range)
            }
        }
        ranges.sortWith { i1, i2 -> i1.start.compareTo(i2.start) }
        for ((r1, r2) in ranges.windowed(2))
            if (r1.endInclusive != r2.start-1)
                return row + 4000000 * (r1.endInclusive+1)
        println("not in $row")
    }
    return -1
}

fun main() {
    //val input = readInput("d15/test"); val row = 10L
    val input = readInput("d15/input1"); val row = 2000000L

    //println(part1(input, row))
    println(part2(input))
}

