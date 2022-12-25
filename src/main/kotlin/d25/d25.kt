package d25

import readInput
import java.lang.IllegalStateException
import java.util.*


val units = mapOf('0' to 0L, '1' to 1L, '2' to 2L, '-' to -1L, '=' to -2L)

fun snafuToLong(snafu : String) : Long {
    var pow = 1L
    var sum = 0L
    for (unit in snafu.reversed()) {
        sum += pow * units[unit]!!
        pow *= 5L
    }
    return sum
}

fun longToSnafu(value : Long) : String {
    var deconstr = value
    val res = LinkedList<Char>()
    while (deconstr>0) {
        val digit = deconstr % 5L
        deconstr /= 5
        res.addFirst(when(digit){
            0L->'0'
            1L->'1'
            2L->'2'
            3L->'='.also {  deconstr++ }
            4L->'-'.also {  deconstr++ }
            else->throw IllegalStateException()
        })
    }
    return res.joinToString(separator = "")
}

fun part1(input: List<String>): String {
    var sum = 0L
    for (line in input) {
        val snafu = snafuToLong(line)
        sum += snafu
    }
    return longToSnafu(sum)
}


fun main() {
    //val input = readInput("d25/test")
    val input = readInput("d25/input1")

    println(part1(input))
}

