package d3

import readInput


fun part1(input: List<String>): Int {
    var sum = 0
    for (line in input) {
        val comp1 = line.substring(0, line.length/2)
        val comp2 = line.substring(line.length/2)
        val set1 = comp1.toSet()
        for (elt in comp2)
            if (elt in set1) {
                println("${elt} - ${prio(elt)}")
                sum += prio(elt)
                break
            }
    }
    return sum
}

fun prio(elt: Char): Int {
    if (elt in 'a'..'z')
        return 1 + (elt - 'a')
    return 27 + (elt -'A')
}

fun part2(input: List<String>): Int {
    var sum = 0
    for (group in 0 until input.size step 3) {
        val set1 = input[group].toSet()
        val set2 = input[group+1].toSet()
        val set3 = input[group+2].toSet()
        val common = set1.intersect(set2).intersect(set3)
        println("${common} - ${prio(common.first())}")
        sum += prio(common.first())
    }
    return sum
}

fun main() {
    //val input = readInput("d3/test")
    val input = readInput("d3/input1")

    println(part2(input))
}

