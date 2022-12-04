package d4

import readInput
import java.util.*


fun part1(input: List<String>): Int {
    var nb=0
    for (ligne in input) {
        val reader = Scanner(ligne)
        reader.useDelimiter("[-,]")
        val l1 = reader.nextInt()
        val u1 = reader.nextInt()
        val l2 = reader.nextInt()
        val u2 = reader.nextInt()
        if (l1>=l2 && u1<=u2) nb++
        else if (l2>=l1 && u2<=u1) nb++
    }
    return nb
}

fun part2(input: List<String>): Int {
    var nb=0
    for (ligne in input) {
        val reader = Scanner(ligne)
        reader.useDelimiter("[-,]")
        val l1 = reader.nextInt()
        val u1 = reader.nextInt()
        val l2 = reader.nextInt()
        val u2 = reader.nextInt()
        val r1 = l1..u1
        val r2 = l2..u2
        if (r1.intersect(r2).isNotEmpty()) nb++
    }
    return nb
}

fun main() {
    val input = readInput("d4/test")
    //val input = readInput("d4/input1")

    println(part1(input))
    println(part2(input))
}

