package d21

import readInput
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.util.Scanner

open class Monkey {
    open fun res(): Long = 0
}

val monkeys = mutableMapOf<String, Monkey>()

data class MonkeyNumber(val nb: Long) : Monkey() {
    override fun res(): Long = nb
}

data class MonkeyMath(val nb1: String, val op: String, val nb2: String) : Monkey() {
    override fun res(): Long = when (op) {
        "+" -> monkeys[nb1]!!.res() + monkeys[nb2]!!.res()
        "*" -> monkeys[nb1]!!.res() * monkeys[nb2]!!.res()
        "/" -> monkeys[nb1]!!.res() / monkeys[nb2]!!.res()
        "-" -> monkeys[nb1]!!.res() - monkeys[nb2]!!.res()
        else ->
            throw IllegalArgumentException("unknown op $op")
    }

}

fun part1(input: List<String>): Long {
    for (ligne in input) {
        val scan = Scanner(ligne)
        scan.useDelimiter("[: ]+")
        val name = scan.next()
        if (scan.hasNextLong())
            monkeys[name] = MonkeyNumber(scan.nextLong())
        else {
            val name1 = scan.next()
            val op = scan.next()
            val name2 = scan.next()
            monkeys[name] = MonkeyMath(name1, op, name2)
        }
    }
    return monkeys["root"]!!.res()
}

fun part2(input: List<String>): Long {
    val unused = part1(input)

    val root = monkeys["root"] as MonkeyMath
    val left = monkeys[root.nb1]!!
    val right = monkeys[root.nb2]!!
    monkeys.remove("humn")
    try {
        val valueLeft = left.res()
        return findMath(root.nb2, valueLeft)
    } catch (_: NullPointerException) {
        val target = monkeys[root.nb2]!!.res()
        return findMath(root.nb1, target)
    }
}

fun findMath(name: String, target: Long): Long {
    if (name == "humn")
        return target
    if (monkeys[name] is MonkeyNumber) {
        throw IllegalStateException("Oops")
    }
    val monkey = monkeys[name] as MonkeyMath
    val left = monkeys[monkey.nb1]
    try {
        if (left == null) throw NullPointerException()
        val value = left.res()
        when (monkey.op) {
            "+" -> return findMath(monkey.nb2, target - value) //target = value + ?
            "-" -> return findMath(monkey.nb2, value - target) //target = value - ?
            "*" -> return findMath(monkey.nb2, target / value)  //target = value * ?
            "/" -> return findMath(monkey.nb2, value / target) //target = value / ?
            else -> throw IllegalArgumentException("unk op ${monkey.op}")
        }
    } catch (_: NullPointerException) {
        val value = monkeys[monkey.nb2]!!.res()
        when (monkey.op) {
            "+" -> return findMath(monkey.nb1, target - value) //target = ? + value
            "-" -> return findMath(monkey.nb1, value + target) //target = ? - value
            "*" -> return findMath(monkey.nb1, target / value)  //target = ? * value
            "/" -> return findMath(monkey.nb1, value * target) //target = ? / value
            else -> throw IllegalArgumentException("unk op ${monkey.op}")
        }
    }
}

fun main() {
    //val input = readInput("d21/test")
    val input = readInput("d21/input1")

    //println(part1(input))
    println(part2(input))
}

