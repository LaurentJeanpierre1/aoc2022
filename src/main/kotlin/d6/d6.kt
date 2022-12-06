package d6

import readInput


fun part1(input: String): Int {
    val list = input.toList().windowed(4)
    for ((no,elt) in list.withIndex()) {
        if (elt[0] != elt[1] && elt[0] != elt[2] && elt[0]!=elt[3])
            if (elt[1] != elt[2] && elt[1] != elt[3] && elt[2] != elt[3])
                return no + 4
    }

    return -1
}

fun part2(input: String): Int {
    val list = input.toList().windowed(14)
    for ((no,elt) in list.withIndex()) {
        val set = mutableSetOf<Char>()
        set.addAll(elt)
        if (set.size == 14) return no+14
    }
    return 0
}

fun main() {
    //val input = readInput("d6/test")
    val input = readInput("d6/input1")

    println(part1(input[0]))
    println(part2(input[0]))
}

