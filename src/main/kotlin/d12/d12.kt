package d12

import readInput
import java.util.PriorityQueue

data class Pos(val x: Int, val y: Int) {
    var prev: Pos? = null
    var length: Int = -1
    constructor(x: Int, y: Int, prev: Pos?, length: Int) : this(x,y){
        this.prev = prev
        this.length = length
    }
}

fun part1(input: List<String>): Int {
    var target : Pos? = null
    var start : Pos? = null
    val ite = input.listIterator()
    val elev = Array<CharArray>(input.size) {
        var line = ite.next()
        var idx = line.indexOf('E')
        if (idx>-1) {
            line = line.replace('E', 'z')
            target = Pos(idx, it)
        }
        idx = line.indexOf('S')
        if (idx>-1) {
            line = line.replace('S', 'a')
            start = Pos(idx, it, null, 0)
        }
        line.toCharArray()
    }
    assert(start != null)
    assert(target != null)
    val queue = PriorityQueue<Pos>() {p1, p2 -> p1.length.compareTo(p2.length)}
    queue.add(start)
    val visited = mutableSetOf<Pos>()
    while (queue.isNotEmpty()) {
        val place = queue.poll()
        if (place in visited) continue
        if (place == target) return place.length
        visited.add(place)
        val (x,y) = place
        val limit = elev[y][x] + 1
        if (x>0 && elev[y][x-1]<= limit) {
            val next = Pos(x-1, y, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (x<elev[0].size-1 && elev[y][x+1]<= limit) {
            val next = Pos(x+1, y, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (y>0 && elev[y-1][x]<= limit) {
            val next = Pos(x, y-1, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (y<elev.size-1 && elev[y+1][x]<= limit) {
            val next = Pos(x, y+1, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
    }
    return 0
}

fun part2(input: List<String>): Int {
    val queue = PriorityQueue<Pos>() {p1, p2 -> p1.length.compareTo(p2.length)}
    var target : Pos? = null
    val ite = input.listIterator()
    val elev = Array<CharArray>(input.size) {
        var line = ite.next()
        var idx = line.indexOf('E')
        if (idx>-1) {
            line = line.replace('E', 'z')
            target = Pos(idx, it)
        }
        idx = line.indexOf('S')
        if (idx>-1) {
            line = line.replace('S', 'a')
        }
        line.toCharArray()
    }
    assert(target != null)
    for ((y,line) in elev.withIndex()) {
        for ((x,col) in line.withIndex()) {
            if (col == 'a')
                queue.add(Pos(x,y,null,0))
        }
    }
    val visited = mutableSetOf<Pos>()
    while (queue.isNotEmpty()) {
        val place = queue.poll()
        if (place in visited) continue
        if (place == target) return place.length
        visited.add(place)
        val (x,y) = place
        val limit = elev[y][x] + 1
        if (x>0 && elev[y][x-1]<= limit) {
            val next = Pos(x-1, y, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (x<elev[0].size-1 && elev[y][x+1]<= limit) {
            val next = Pos(x+1, y, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (y>0 && elev[y-1][x]<= limit) {
            val next = Pos(x, y-1, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
        if (y<elev.size-1 && elev[y+1][x]<= limit) {
            val next = Pos(x, y+1, place, place.length+1)
            if (next !in visited) queue.add(next)
        }
    }
    return 0
}

fun main() {
    //val input = readInput("d12/test")
    val input = readInput("d12/input1")

    println(part1(input))
    println(part2(input))
}

