package d24

import readInput
import java.lang.IllegalArgumentException

data class State(val x:Int, val y:Int, val time:Int) {
    var prev : State? = null
}

fun printValley(valley : List<Array<Square>>) {
    for (line in valley) {
        for (sq in line)
            print(sq.char)
        println()
    }
}
fun printValley(valley : List<Array<Square>>, x:Int, y:Int) {
    for (line in valley.withIndex()) {
        for (sq in line.value.withIndex()) {
            if (x == sq.index && y==line.index)
                if (sq.value.isFree)
                    print('E')
                else
                    print('ø')
            else
                print(sq.value.char)
        }
        println()
    }
}
fun part1(input: List<String>): Int {
    val valley = readPuzzle(input)
    blizzards.add(valley)
    val x = valley[0].withIndex().first { it.value.isFree }.index
    val y = 0
    return dijkstra(x, y, 0, valley.size - 1)?.time ?: -1
}
fun testEvolve (input: List<String>) {
    var valley = readPuzzle(input)
    blizzards.add(valley)
    repeat(7) {
        println("round $it")
        for (line in valley) {
            for (sq in line)
                print(sq.char)
            println()
        }
        valley = evolve(it + 1)
    }
}
private fun dijkstra(x: Int, y: Int, t0: Int, targetY: Int): State? {
    val queue = ArrayDeque<State>()
    val visited = mutableSetOf<State>()
    queue.add(State(x, y, t0))
    while (queue.isNotEmpty()) {
        val state = queue.removeFirst()
        if (state in visited) continue
        visited.add(state)
        val (xp, yp, t) = state
        if (yp == targetY) {
            println("***********************************************************************")
            var st: State? = state
            while (st != null) {
                printValley(evolve(st.time), st.x, st.y)
                st = st.prev
            }
            return state
        }

        val valley = evolve(t + 1)
        if (valley[yp][xp - 1].isFree) queue.add(State(xp - 1, yp, t + 1).apply { prev = state })
        if (valley[yp][xp + 1].isFree) queue.add(State(xp + 1, yp, t + 1).apply { prev = state })
        if (yp > 0 && valley[yp - 1][xp].isFree) queue.add(State(xp, yp - 1, t + 1).apply { prev = state })
        if (yp < valley.size - 1 && valley[yp + 1][xp].isFree) queue.add(State(xp, yp + 1, t + 1).apply {
            prev = state
        })
        if (valley[yp][xp].isFree) queue.add(State(xp, yp, t + 1).apply { prev = state })
    }
    return null
}

fun part2(input: List<String>): Int {
    val valley = readPuzzle(input)
    blizzards.add(valley)
    val x = valley[0].withIndex().first { it.value.isFree }.index
    val y = 0
    val down = dijkstra(x, y, 0, valley.size - 1)!!
    val up = dijkstra(down.x, down.y, down.time, 0)!!
    val goal = dijkstra(up.x, up.y, up.time, valley.size - 1)!!
    return goal.time
}

val blizzards = mutableListOf<List<Array<Square>>>()
val wall = Square(wall = true)
fun evolve(time: Int): List<Array<Square>> {
    if (blizzards.size > time)
        return blizzards[time]
    assert(time == blizzards.size)
    val valley = blizzards[time-1]
    val res = ArrayList<Array<Square>>(valley.size)
    val lastY = valley.size - 2
    val lastX = valley[0].size - 2
    res.add(valley[0])
    for (y in 1..lastY) {
        val line = Array(valley[y].size) { Square() }
        line[0] = wall
        line[lastX + 1] = wall
        for (x in 1..lastX) {
            line[x].down = if (y != 1)
                valley[y - 1][x].down
            else
                valley[lastY][x].down
            line[x].up = if (y != lastY)
                valley[y + 1][x].up
            else
                valley[1][x].up
            line[x].left = if (x != lastX)
                valley[y][x + 1].left
            else
                valley[y][1].left
            line[x].right = if (x != 1)
                valley[y][x - 1].right
            else
                valley[y][lastX].right
        }
        res.add(line)
    }
    res.add(valley.last())
    blizzards.add(res)
    println("Time=$time")
    printValley(res)
    return res
}

data class Square(
    var left: Boolean = false,
    var down: Boolean = false,
    var right: Boolean = false,
    var up: Boolean = false,
    var wall: Boolean = false
) {
    val isFree
        get() = !(left || down || right || up || wall)
    private val count
        get() = (if (left) 1 else 0) +
                (if (right) 1 else 0) +
                (if (down) 1 else 0) +
                (if (up) 1 else 0)
    val char
        get() = if (wall) '#' else if (count == 0) '.' else if (count > 1) '0' + count else
            if (left) '<' else
                if (right) '>' else
                    if (up) '^' else 'v'
}

fun readPuzzle(input: List<String>): List<Array<Square>> {
    val res = mutableListOf<Array<Square>>()
    for (line in input) {
        if (line.isBlank()) continue
        res.add(line.map {
            when (it) {
                '#' -> Square(wall = true)
                '>' -> Square(right = true)
                '<' -> Square(left = true)
                '^' -> Square(up = true)
                'v' -> Square(down = true)
                '.' -> Square()
                else -> throw IllegalArgumentException("unknown square $it")
            }
        }.toTypedArray())
    }
    return res
}

fun main() {
    //val input = readInput("d24/test")
    //val input = readInput("d24/test1")
    val input = readInput("d24/input1")

    //println(part1(input))
    println(part2(input))
}

