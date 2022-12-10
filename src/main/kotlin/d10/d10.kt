package d10

import readInput


fun part1(input: List<String>): Int {
    var X = 1
    var cycle = 0
    var sum = 0
    var counted = 0
    for (line in input) {
        if (cycle> counted && ((cycle-20) % 40) == 0) {
            sum += cycle * X
            println("Signal $cycle = ${cycle * X}" )
            counted = cycle
        }
        if (line == "noop") {
            cycle += 1
        } else if (line.startsWith("addx")) {
            val delta = line.substring(5).toInt()
            cycle += 2
            val rem = (cycle - 20) % 40
            if ((rem) in 0 .. 2) {
                val trueCycle = cycle - rem
                if (trueCycle> counted) {
                    sum += trueCycle * X
                    println("Signal $trueCycle = ${trueCycle * X}")
                    counted = trueCycle
                }
            }
            X += delta
        }
        println("Cycle $cycle - X=$X")
        if (cycle>221) break
    }
    return sum
}

fun part2(input: List<String>): Int {
    var X = 1
    var cycle = 0
    for (line in input) {
        print(X, cycle)
        if (line == "noop") {
            cycle += 1
        } else if (line.startsWith("addx")) {
            val delta = line.substring(5).toInt()
            cycle += 1
            print(X, cycle)
            cycle += 1
            X += delta
        }
        cycle = (cycle%40)
    }
    return 0
}

private fun print(X: Int, cycle: Int) {
    val trueCycle = (cycle) % 40
    if (X in trueCycle - 1..trueCycle + 1) {
        print("#")
    } else
        print(".")
    if (cycle==39) {
        println()
    }
}

fun main() {
    //val input = readInput("d10/test")
    val input = readInput("d10/input1")

    println(part1(input))
    println(part2(input))
}

