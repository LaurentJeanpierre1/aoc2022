package d13

import readInput
import java.lang.IllegalStateException

sealed class Item : Comparable<Item>
class ItemList(val list: List<Item>) : Item() {
    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Item): Int {
        val right = if (other is ItemList)
                other.list.listIterator()
            else
                listOf(other).listIterator()
        val left = list.listIterator()
        do {
            if (left.hasNext()) {
                if (right.hasNext()) {
                    val res = left.next().compareTo(right.next())
                    if (res != 0) return res
                } else return 1
            } else {
                return if (right.hasNext()) -1 else 0
            }
        } while (true)
    }

    override fun toString(): String {
        return list.toString()
    }
}
class ItemInt(val int : Int) : Item() {
    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Item): Int {
        if (other is ItemList)
            return ItemList(listOf(this)).compareTo(other)
        if (other is ItemInt)
            return int.compareTo(other.int)
        throw IllegalStateException()
    }

    override fun toString(): String {
        return int.toString()
    }
}

fun parse(line : String) : Item {
    return parse(line.toCharArray().toList().listIterator())!!
}
fun parse(line: ListIterator<Char>) : Item? {
    when (val first = line.next()) {
        '[' -> {
            val res = mutableListOf<Item>()
            while (line.next() != ']') {
                line.previous() // unconsume char
                val item = parse(line)
                if (item != null)
                    res.add(item)
                if (line.next() != ',')
                    line.previous() // unconsume char
            }
            return ItemList(res)
        }
        in '0'..'9' -> {
            var int = first - '0'
            var next = line.next()
            while (next in '0'..'9') {
                int = 10 * int + (next - '0')
                next = line.next()
            }
            line.previous()
            return ItemInt(int)
        }
        else -> return null
    }
}
fun part1(input: List<String>): Int {
    val ite = input.listIterator()
    var idx = 1
    var score = 0
    while (ite.hasNext()) {
        val left = parse(ite.next())
        val right = parse(ite.next())
        println("Comparing $left with $right")
        assert(!ite.hasNext() || ite.next().isBlank())
        if (left < right)
            score += idx
        ++idx
    }
    return score
}

fun part2(input: List<String>): Int {
    val packets = mutableListOf<Item>()
    val ite = input.listIterator()
    while (ite.hasNext()) {
        val left = parse(ite.next())
        val right = parse(ite.next())
        assert(!ite.hasNext() || ite.next().isBlank())
        packets.add(left)
        packets.add(right)
    }
    val divider1 = parse("[[2]]")
    val divider2 = parse("[[6]]")
    packets.add(divider1)
    packets.add(divider2)
    packets.sort()
    val idx1 = packets.indexOf(divider1) + 1
    val idx2 = packets.indexOf(divider2) + 1
    val res = idx1*idx2
    println("idx = $idx1 * $idx2 = $res")
    return res
}

fun main() {
    //val input = readInput("d13/test")
    val input = readInput("d13/input1")

    println(part1(input))
    println(part2(input))
}

