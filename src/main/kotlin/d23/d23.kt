package d23

import readInput

enum class Directions {
    North, South, West, East
}
data class Elf (var x: Int, var y: Int) {
    var direction : Directions? = null
    var arrival : Elf? = null
}

fun part1(input: List<String>): Int {
    val elves = readPuzzle(input)

    repeat(10) {round ->
        //First Half
        val arrival = mutableMapOf<Elf, Int>()
        for (elf in elves) {
            elf.direction = null
            var free = 0
            for (dir in 0..3) {
                var full : Boolean
                val direction = Directions.values()[(round + dir) % 4]
                full = when(direction) {
                    Directions.North -> elves.any { it.y == elf.y - 1 && (it.x - elf.x) in -1..+1 }
                    Directions.South -> elves.any { it.y == elf.y + 1 && (it.x - elf.x) in -1..+1 }
                    Directions.West -> elves.any { it.x == elf.x - 1 && (it.y - elf.y) in -1..+1 }
                    Directions.East -> elves.any { it.x == elf.x + 1 && (it.y - elf.y) in -1..+1 }
                }
//                for (d in -1..1) {
//                    when (direction) {
//                        Directions.North -> if (Elf(elf.x+d, elf.y-1) in elves) { full=true; break}
//                        Directions.South -> if (Elf(elf.x+d, elf.y+1) in elves) { full=true; break}
//                        Directions.West -> if (Elf(elf.x-1, elf.y+d) in elves) { full=true; break}
//                        Directions.East -> if (Elf(elf.x+1, elf.y+d) in elves) { full=true; break}
//                    }
//                }
                if (!full) {
                    if (elf.direction == null) {
                        elf.direction = direction
                        val dest = when (direction) {
                            Directions.North -> Elf(elf.x, elf.y - 1)
                            Directions.South -> Elf(elf.x, elf.y + 1)
                            Directions.West -> Elf(elf.x - 1, elf.y)
                            Directions.East -> Elf(elf.x + 1, elf.y)
                        }
                        elf.arrival = dest
                    }
                    free++
                }
            }
            if (free == 4) {
                elf.direction = null // No move !
            } else if (elf.direction != null){
                val dest = elf.arrival!!
                if (dest in arrival)
                    arrival[dest] = arrival[dest]!! + 1
                else
                    arrival[dest] = 1
            }
        }
        // sencond half
        for (elf in elves) {
            if (elf.direction != null && arrival[elf.arrival] == 1) {
                elf.x = elf.arrival!!.x
                elf.y = elf.arrival!!.y
            }
        }
    }
    val minX = elves.minOf { it.x }
    val maxX = elves.maxOf { it.x }
    val minY = elves.minOf { it.y }
    val maxY = elves.maxOf { it.y }

    return (maxX-minX+1)*(maxY-minY+1)-elves.size
}

private fun readPuzzle(input: List<String>): MutableSet<Elf> {
    val elves = mutableSetOf<Elf>()
    for ((noLine, line) in input.withIndex()) {
        line.toCharArray().withIndex().filter { (_, char) -> char == '#' }.forEach { (col, _) ->
            elves.add(Elf(col, noLine))
        }
    }
    return elves
}

fun part2(input: List<String>): Int {
    val elves = readPuzzle(input)

    repeat(10000) { round ->
        //First Half
        val arrival = mutableMapOf<Elf, Int>()
        for (elf in elves) {
            elf.direction = null
            var free = 0
            for (dir in 0..3) {
                var full : Boolean
                val direction = Directions.values()[(round + dir) % 4]
                full = when (direction) {
                    Directions.North -> elves.any { it.y == elf.y - 1 && (it.x - elf.x) in -1..+1 }
                    Directions.South -> elves.any { it.y == elf.y + 1 && (it.x - elf.x) in -1..+1 }
                    Directions.West -> elves.any { it.x == elf.x - 1 && (it.y - elf.y) in -1..+1 }
                    Directions.East -> elves.any { it.x == elf.x + 1 && (it.y - elf.y) in -1..+1 }
                }
                if (!full) {
                    if (elf.direction == null) {
                        elf.direction = direction
                        val dest = when (direction) {
                            Directions.North -> Elf(elf.x, elf.y - 1)
                            Directions.South -> Elf(elf.x, elf.y + 1)
                            Directions.West -> Elf(elf.x - 1, elf.y)
                            Directions.East -> Elf(elf.x + 1, elf.y)
                        }
                        elf.arrival = dest
                    }
                    free++
                }
            }
            if (free == 4) {
                elf.direction = null // No move !
            } else if (elf.direction != null) {
                val dest = elf.arrival!!
                if (dest in arrival)
                    arrival[dest] = arrival[dest]!! + 1
                else
                    arrival[dest] = 1
            }
        }
        // sencond half
        var noMove = true
        for (elf in elves) {
            if (elf.direction != null && arrival[elf.arrival] == 1) {
                elf.x = elf.arrival!!.x
                elf.y = elf.arrival!!.y
                noMove = false
            }
        }
        if (noMove) return round+1
    }
    return -1
}

fun main() {
    //val input = readInput("d23/test0")
    //val input = readInput("d23/test")
    val input = readInput("d23/input1")

    //println(part1(input))
    println(part2(input))
}

