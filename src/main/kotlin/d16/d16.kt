package d16

import readInput

data class Valve(val name: String, val flow: Int, var next: List<String>) {
    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Valve && name == other.name
    }
}
val valves = mutableMapOf<String,Valve>()
val visited = mutableSetOf<Valve>()
var nbClosed = 0

data class Memory(val name: String, val time: Int)
val best = mutableMapOf<Memory, Int>()
data class Memory2(val nameM: String,val nameE: String, val time: Int)
val best2 = mutableMapOf<Memory2, Int>()
fun part1(input: List<String>): Int {
    readFile(input)
    for (valve in valves) {
        valve.value.next = valve.value.next.sortedWith { s1, s2 -> -valves[s1]!!.flow.compareTo(valves[s2]!!.flow) }
    }
    val start = valves["AA"]!!
    return maxFlow(start, 1, 0, 0)
}

fun maxFlow(start: Valve, time: Int, currentFlow: Int, totalReleased: Int): Int {
    if (time > 30)
        return totalReleased
    if (visited.size >= nbClosed)
        return totalReleased + (31-time)*currentFlow // All valves open
    if ((best[Memory(start.name, time)]?:0) > totalReleased)
        return totalReleased
    best[Memory(start.name, time)] = totalReleased
    var open=0
    if (start.flow > 0 && start !in visited && time < 30) {
        visited.add(start)
        for (next in start.next) {
            val res = maxFlow(valves[next]!!, time + 2, currentFlow + start.flow, totalReleased+currentFlow*2+start.flow)
            if (res > open)
                open = res
        }
        //open = start.next.map{ maxFlow(valves[it]!!, time + 2, currentFlow + start.flow, totalReleased+currentFlow*2+start.flow)}.max()
        visited.remove(start)
    }
    //val closed = start.next.map{ maxFlow(valves[it]!!, time + 1, currentFlow, totalReleased+currentFlow)}.max()
    var closed = 0
    for (next in start.next) {
        val res = maxFlow(valves[next]!!, time + 1, currentFlow, totalReleased+currentFlow)
        if (res > closed)
            closed = res
    }


    return Math.max(open, closed)
}
fun maxFlowElephant(start: Valve, elephant: Valve, time: Int, currentFlow: Int, totalReleased: Int, opening: Boolean, elephantOpening:Boolean): Int {
    var open=0
    if (elephantOpening) {
        for (next in elephant.next) {
            var next1 = next
            val res = maxFlowMe(start, valves[next1]!!, time + 1, currentFlow, totalReleased+currentFlow, opening, false)
            if (res > open)
                open = res
        }
        return open
    } else {
        var shouldOpen = elephant.flow > 0

        if (elephant.flow > 0 && elephant !in visited && time < 26)
            if (shouldOpen){
            visited.add(elephant)
            open = maxFlowMe(start, elephant, time + 1, currentFlow + elephant.flow, totalReleased+currentFlow+ elephant.flow, opening, true)
            visited.remove(elephant)
        }
        var closed = 0
        for (next in elephant.next) {
            var next1 = next
            val res = maxFlowMe(start, valves[next1]!!, time + 1, currentFlow, totalReleased+currentFlow, opening, false)
            if (res > closed)
                closed = res
        }
        return Math.max(open, closed)
    }
}

var topValue = 0
fun maxFlowMe(start: Valve, elephant: Valve, time: Int, currentFlow: Int, totalReleased: Int, opening: Boolean, elephantOpening:Boolean): Int {
    if (time > 26) {
        if (totalReleased > topValue) {
            topValue = totalReleased
            println("Current top is $topValue")
        }
        return totalReleased
    }
    if (visited.size >= nbClosed) {
        val value = totalReleased + (26 - time) * currentFlow
        if (value > topValue) {
            topValue = value
            println("Current top is $topValue")
        }
        return value // All valves open
    }
    if ((best2[Memory2(start.name, elephant.name, time)]?:0) > totalReleased)
        return totalReleased
    best2[Memory2(start.name, elephant.name, time)] = totalReleased
    best2[Memory2(elephant.name, start.name, time)] = totalReleased
    var open=0
    if (opening) {
        for (next in start.next) {
            var next1 = next
            val res = maxFlowElephant(valves[next1]!!, elephant, time, currentFlow, totalReleased, false, elephantOpening)
            if (res > open)
                open = res
        }
        return open
    } else {
        var shouldOpen = start.flow > 0

        if (start.flow > 0 && start !in visited && time < 26)
            if (shouldOpen) {
            visited.add(start)
            open = maxFlowElephant(start, elephant, time, currentFlow + start.flow, totalReleased, true, elephantOpening)
            visited.remove(start)
        }
        var closed = 0
        for (next in start.next) {
            var next1 = next
            val res = maxFlowElephant(valves[next1]!!, elephant, time, currentFlow, totalReleased, false, elephantOpening)
            if (res > closed)
                closed = res
        }
        return Math.max(open, closed)
    }
}

fun readFile(input: List<String>) {
    val match = Regex("Valve ([A-Z]{2}) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z, ]+)")
    for (line in input) {
        val res = match.matchEntire(line)!!.groupValues
        val name = res[1]
        val flow = res[2].toInt()
        val next = res[3]
        println("$name ($flow) -> $next")
        val next2 = next.split(", ")
        val valve = Valve(name, flow, next2)
        valves[name] = valve
        if (flow>0) nbClosed++
    }
}

fun part2(input: List<String>): Int {
    readFile(input)
    for (valve in valves) {
        valve.value.next = valve.value.next.sortedWith { s1, s2 -> -valves[s1]!!.flow.compareTo(valves[s2]!!.flow) }
    }
    val start = valves["AA"]!!
    return maxFlowMe(start, start, 1, 0, 0, false, false)
}

fun main() {
    //val input = readInput("d16/test")
    val input = readInput("d16/input1")

    //println(part1(input))
    println(part2(input))

}

