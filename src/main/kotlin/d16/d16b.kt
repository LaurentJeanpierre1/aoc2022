package d16

import readInput
import java.util.PriorityQueue

data class Node(val valve: Valve, val time: Int, val flow: Int, val opened: Set<Valve>, val pression: Int, val opening : Boolean) : Comparable<Node> {
    private fun score(): Float = (pression-(31-time)*flow)/time.toFloat()
    override fun compareTo(other: Node): Int = score().compareTo(other.score())

    override fun toString(): String {
        return "Node(${valve.name}, time=$time, flow=$flow, pression=$pression, opening=$opening), opened=${opened.map { it.name }}"
    }
}

fun part1b(input: List<String>): Int {
    readFile(input)
    val queue = PriorityQueue<Node>()
    queue.add(Node(valves["AA"]!!, 1, 0, setOf(), 0, false))
    while (queue.isNotEmpty()) {
        val start = queue.poll()
        if (start.time > 30) return start.pression
        if (start.opening) {
            queue.add(Node(
                start.valve,
                start.time + 1,
                start.flow,
                start.opened,
                start.pression - start.flow,
                false
            ))
        } else {
            if (start.time < 30 && start.valve.flow > 0 && start.valve !in start.opened) {
                queue.add(
                    Node(
                        start.valve,
                        start.time + 1,
                        start.flow + start.valve.flow,
                        start.opened.plus(start.valve),
                        start.pression - start.flow,
                        true
                    )
                )
            }
            for (next in start.valve.next) {
                queue.add(
                    Node(
                        valves[next]!!,
                        start.time + 1,
                        start.flow,
                        start.opened,
                        start.pression - start.flow,
                        false
                    )
                )
            }
        }
    }
    return 0
}

fun main() {
    val input = readInput("d16/test")
    //val input = readInput("d16/input1")

    println(part1b(input))
    //println(part2(input))
}
