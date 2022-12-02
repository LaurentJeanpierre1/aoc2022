package d1

import readInput

fun part1(input: List<String>): Int {
    val elves = ArrayList<Int>()
    var sum = 0
    for (line in input) {
        if (line.isBlank()) {
            if (sum > 0)
                elves += sum
            sum = 0
        } else {
            sum += line.toInt()
        }
    }
    if (sum > 0)
        elves += sum
    return elves.max()
}

fun part2(input: List<String>): Int {
    val elves = ArrayList<Int>()
    var sum = 0
    for (line in input) {
        if (line.isBlank()) {
            if (sum > 0)
                elves += sum
            sum = 0
        } else {
            sum += line.toInt()
        }
    }
    if (sum > 0)
        elves += sum
    elves.sortDescending()
    return elves[0] + elves[1] + elves[2]
}

fun main() {
    val input = readInput("d1/input1")

    println(part2(input))
}

