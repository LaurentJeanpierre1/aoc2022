package d9

import readInput
import java.lang.IllegalArgumentException
import java.util.Scanner


data class  Point(val x : Int, val y: Int) {}

fun moveHead(pt : Point, dir: String) : Point = when(dir) {
    "U" -> Point(pt.x, pt.y+1)
    "D" -> Point(pt.x, pt.y-1)
    "L" -> Point(pt.x-1, pt.y)
    "R" -> Point(pt.x+1, pt.y)
    else -> throw IllegalArgumentException(dir)
}


fun moveTail(head: Point, tail: Point): Point {
    var dx = head.x - tail.x
    var dy = head.y - tail.y
    if (dx in -1..+1 && dy in -1..+1)
        return tail
    if (dx==-2 || dx==+2)
        if (dy in -1.. +1)
            return Point(tail.x + dx/2, head.y)
    if (dy==-2 || dy==+2)
        if (dx in -1.. +1)
            return Point(head.x , tail.y + dy/2)

    if (dx<=-2) dx = -1
    if (dy<=-2) dy = -1
    if (dx>=+2) dx = +1
    if (dy>=+2) dy = +1
    return Point(tail.x + dx, tail.y + dy)
}

fun part1(input: List<String>): Int {
    var head = Point(0,0)
    var tail = Point(0,0,)

    val positions = mutableSetOf<Point>(tail)

    for (line in input) {
        val scan = Scanner(line)
        val dir = scan.next()
        val count = scan.nextInt()
        repeat(count) {
            println(dir)
            head = moveHead(head, dir)
            tail = moveTail(head, tail)
            println("$head - $tail")
            positions.add(tail)
        }
    }
    return positions.size
}


fun part2(input: List<String>): Int {
    var head = Point(0,0)
    val tail = mutableListOf<Point>()
    tail.addAll(List(9) {Point(0,0,)} )

    val positions = mutableSetOf<Point>()

    for (line in input) {
        val scan = Scanner(line)
        val dir = scan.next()
        val count = scan.nextInt()
        repeat(count) {
            println(dir)
            head = moveHead(head, dir)
            var prec = head
            for ((idx,node) in tail.withIndex()) {
                val next = moveTail(prec, node)
                tail[idx] = next
                prec = next
            }
            print("$head")
            tail.forEach{print(" - $it")}
            println()
            positions.add(prec)
        }
    }
    return positions.size
}

fun main() {
    //val input = readInput("d9/test")
    //val input = readInput("d9/test2")
    val input = readInput("d9/input1")

    //println(part1(input))
    println(part2(input))
}

