package d11

import readInput
import java.lang.IllegalArgumentException
import java.util.*

data class Monkey(
    val idx: Int,
    val starting: MutableList<Long>,
    val operation1: Long?,
    val operation2: String,
    val operation3: Long?,
    val test: Long,
    val targetTrue: Int,
    val targetFalse: Int
) {}

fun part1(input: List<String>): Int {
    val ite = input.listIterator()
    val monkeys = readMonkeys(ite)

    val inspected = Array<Int>(monkeys.size, { 0 })
    repeat(20) {
        monkeys.forEach {
            it.apply {
                //println("Monkey $idx:")
                starting.forEach {
                    //println("  Monkey inspects an item with a worry level of $it.")
                    inspected[idx]++
                    val op1 = operation1 ?: it
                    val op2 = operation3 ?: it
                    var new = when (operation2) {
                        "+" -> op1 + op2
                        "*" -> op1 * op2
                        else -> throw IllegalArgumentException()
                    }
                    //println("    $op1 $operation2 $op2 => $new")
                    new /= 3
                    //println("    Monkey gets bored with item. Worry level is divided by 3 to $new.")
                    var word = ""
                    var toMonkey = -1
                    if ((new % test) == 0L) {
                        toMonkey = targetTrue
                    } else {
                        word = "not "
                        toMonkey = targetFalse
                    }
                    //println("    Current worry level is $word divisible by $test.")
                    //println("    Item with worry level $new is thrown to monkey $toMonkey.")
                    monkeys[toMonkey].starting.add(new)
                }
                starting.clear()
            }
        }
        println("After round $it:")
        monkeys.forEach {
            print("Monkey ${it.idx}: ")
            println(it.starting.toString())
        }
    }
    println(Arrays.toString(inspected))
    return inspected.sortedArray().run {
        this[this.size - 1] * this[this.size - 2]
    }
}

private fun readMonkeys(ite: ListIterator<String>): MutableList<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    while (ite.hasNext()) {
        var line = ite.next()
        var scan = Scanner(line)
        assert(scan.next() == "Monkey")
        val idx = scan.next().removeSuffix(":").toInt()

        line = ite.next()
        scan = Scanner(line)
        assert(scan.next() == "Starting")
        assert(scan.next() == "Items:")
        val items = mutableListOf<Long>()
        while (scan.hasNext()) {
            items.add(scan.next().removeSuffix(",").toLong())
        }

        line = ite.next()
        scan = Scanner(line)
        assert(scan.next() == "Operation:")
        assert(scan.next() == "new")
        assert(scan.next() == "=")
        val op1 = scan.next()
        val operation2 = scan.next()
        val op3 = scan.next()
        var operation1: Long? = null
        var operation3: Long? = null
        if (op1 != "old")
            operation1 = op1.toLong()
        if (op3 != "old")
            operation3 = op3.toLong()

        line = ite.next()
        scan = Scanner(line)
        assert(scan.next() == "Test:")
        assert(scan.next() == "divisible")
        assert(scan.next() == "by")
        val test = scan.nextLong()

        line = ite.next().trim()
        assert(line.startsWith("If true: throw to monkey "))
        val ifTrue = line.removePrefix("If true: throw to monkey ").toInt()

        line = ite.next().trim()
        assert(line.startsWith("If false: throw to monkey "))
        val ifFalse = line.removePrefix("If false: throw to monkey ").toInt()

        monkeys.add(Monkey(idx, items, operation1, operation2, operation3, test, ifTrue, ifFalse))

        if (ite.hasNext())
            assert(ite.next().isBlank())
    }
    return monkeys
}

fun part2(input: List<String>): Long {
    val ite = input.listIterator()
    val monkeys = readMonkeys(ite)

    val inspected = Array<Long>(monkeys.size, { 0L })
    val modulus = monkeys.map { it.test }.reduce { acc, l -> acc*l }
    repeat(10000) {
        monkeys.forEach {
            it.apply {
                //println("Monkey $idx:")
                starting.forEach {
                    //println("  Monkey inspects an item with a worry level of $it.")
                    inspected[idx]++
                    val op1 = operation1 ?: it
                    val op2 = operation3 ?: it
                    var new = when (operation2) {
                        "+" -> op1 + op2
                        "*" -> op1 * op2
                        else -> throw IllegalArgumentException()
                    }
                    //println("    $op1 $operation2 $op2 => $new")
                    new = new % modulus
                    var word = ""
                    var toMonkey = -1
                    if ((new % test) == 0L) {
                        toMonkey = targetTrue
                    } else {
                        word = "not "
                        toMonkey = targetFalse
                    }
                    //println("    Current worry level is $word divisible by $test.")
                    //println("    Item with worry level $new is thrown to monkey $toMonkey.")
                    monkeys[toMonkey].starting.add(new)
                }
                starting.clear()
            }
        }
        println("After round $it:")
        monkeys.forEach {
            print("Monkey ${it.idx}: ")
            println(it.starting.toString())
        }
    }
    println(Arrays.toString(inspected))
    return inspected.sortedArray().run {
        this[this.size - 1] * this[this.size - 2]
    }
}

fun main() {
    //val input = readInput("d11/test")
    val input = readInput("d11/input1")

    println(part1(input))
    println(part2(input))
}

