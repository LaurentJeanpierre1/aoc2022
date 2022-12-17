package d16

import readInput
import java.lang.IllegalStateException
import java.util.PriorityQueue

val openable = mutableListOf<Valve>()

data class Path(val valve: Valve, val len: Int) : Comparable<Path> {
    override fun compareTo(other: Path): Int {
        return len.compareTo(other.len)
    }
}

fun dijkstra(from : Valve, to:Valve) :Int {
    val queue = PriorityQueue<Path>()
    queue.add(Path(from, 0))
    while (queue.isNotEmpty()) {
        val pos = queue.poll()
        if (pos.valve == to) return pos.len
        for (next in pos.valve.next)
            queue.add(Path(valves[next]!!, pos.len+1))
    }
    throw IllegalStateException("No path from $from to $to")
}

var bestT = 0
data class Pair(val v1: Valve, val v2:Valve)
var cache = mutableMapOf<Pair, Int>()
fun open1(here: Valve, openable: List<Valve>, pression: Int, flow: Int, time: Int) : Int {
    if (time > 30) {
        if (pression> bestT) {
            bestT = pression
            println("time $time : pression $pression, left ${openable.map { v -> v.name }.toString()}")
        }
        return pression
    }
    var max = pression + flow*(30-time)
    for (valve in openable) {
        val delta = cache.computeIfAbsent( Pair(here, valve)) { dijkstra(here, valve)+1}
        if (time+delta > 30) continue
        val value = open1(valve, openable.minus(valve), pression+flow*delta+valve.flow, flow+valve.flow, time+delta)
        if (value > max)
            max = value
    }
    return max
}
fun part1c(input: List<String>): Int {
    readFile(input)
    for (valve in valves)
        if (valve.value.flow > 0)
            openable.add(valve.value)
    openable.sortWith {v1, v2 -> v2.flow.compareTo(v1.flow)}
    return open1(valves["AA"]!!, openable,0,0, 1)
}
fun part2c(input: List<String>): Int {
    readFile(input)
    for (valve in valves)
        if (valve.value.flow > 0)
            openable.add(valve.value)
    openable.sortWith {v1, v2 -> v2.flow.compareTo(v1.flow)}
    return open2(valves["AA"]!!,valves["AA"]!!, openable,0,0, 1,1,1,1,1)
}
fun open2(me: Valve, him: Valve, openable: List<Valve>, pression: Int, flow: Int, time: Int, timeMe: Int, timeHim : Int, fromMe : Int, fromHim: Int) : Int {
    if (time > 26) {
        if (pression> bestT) {
            bestT = pression
            println("time $time : pression $pression, left ${openable.map { v -> v.name }.toString()}")
        }
        return pression
    }
    var max = pression + flow*(26-time)
    if (timeMe == time) { // decision for me
        val curFlow = flow + me.flow
        val curPression = pression + flow * (time-fromMe) + me.flow
        max = curPression + curFlow*(26-time)
        for (valve in openable) {
            val valve1 = valve
            val delta = cache.computeIfAbsent( Pair(me, valve1)) { dijkstra(me, valve1)+1}
            if (time+delta > 26) continue
            val value = open2(valve1, him, openable.minus(valve1), curPression, curFlow, time, time+delta, timeHim, time, fromHim)
            if (value > max)
                max = value
        }
        if (timeHim > time) {
            val value = open2(me, him, openable, curPression, curFlow, timeHim, 0, timeHim, 0, time) // flow accounted till now
            if (value > max)
                max = value
        }
    } else if (timeHim == time) { // decision for elephant
        val curFlow = flow + him.flow
        val curPression = pression + flow * (time-fromHim) + him.flow
        max = curPression + curFlow*(26-time)
        for (valve in openable) {
            val valve1 = valve
            val delta = cache.computeIfAbsent( Pair(him, valve1)) { dijkstra(him, valve1)+1}
            if (time+delta > 26) continue
            val value = open2(me,
                valve1, openable.minus(valve1), curPression, curFlow, time, timeMe, timeHim+delta, fromMe, time)
            if (value > max)
                max = value
        }
        if (timeMe > time) {
            val value = open2(me, him, openable, curPression, curFlow, timeMe, timeMe, 0, time, 0) // flow accounted till now
            if (value > max)
                max = value
        }
    } else {
        if (timeMe <= timeHim && timeMe != 0) {
            val value = open2(me, him, openable, pression, flow, timeMe, timeMe, timeHim, fromMe, timeMe) // flow accounted till now
            if (value > max)
                max = value
        } else if (timeHim != 0){
            val value = open2(me, him, openable, pression, flow, timeHim, timeMe, timeHim, timeHim, fromHim) // flow accounted till now
            if (value > max)
                max = value
        }
    }
    return max
}
// <2436
// >2248
// <2400
// !=2300
// !=2282

// Answer is  2261 - why?
fun main() {
    //val input = readInput("d16/test")
    //val input = readInput("d16/input1")
    val input = readInput("d16/liquifun")

    //println(part1c(input))
    println(part2c(input))
}
