package d17

import readInput

var WIDTH = 7

enum class Rock {
    H4, Cross, L, V4, Cube;

    fun next(): Rock = values()[(ordinal + 1) % values().size]
}

fun Rock.collidesLeft(column: List<CharArray>, height: Int, x: Int): Boolean {
    if (x == 0) return true
    if (column.size <= height) return false
    return when (this) {
        Rock.H4 -> column[height][x - 1] != '.'
        Rock.Cross -> column[height][x] != '.'
                || (column.size > height + 1 && column[height + 1][x - 1] != '.')
                || (column.size > height + 2 && column[height + 2][x] != '.')
        Rock.L -> column[height][x - 1] != '.'
                || (column.size > height + 1 && column[height + 1][x + 1] != '.')
                || (column.size > height + 2 && column[height + 2][x + 1] != '.')
        Rock.V4 -> column[height][x - 1] != '.'
                || (column.size > height + 1 && column[height + 1][x - 1] != '.')
                || (column.size > height + 2 && column[height + 2][x - 1] != '.')
                || (column.size > height + 3 && column[height + 3][x - 1] != '.')
        Rock.Cube -> column[height][x - 1] != '.'
                || (column.size > height + 1 && column[height + 1][x - 1] != '.')
    }
}

fun Rock.collidesRight(column: List<CharArray>, height: Int, x: Int): Boolean {
    if (x >= WIDTH - when(this) {
            Rock.H4 -> 4
            Rock.Cross -> 3
            Rock.L -> 3
            Rock.V4 -> 1
            Rock.Cube -> 2
        }) return true
    if (column.size <= height) return false
    return when (this) {
        Rock.H4 -> x >= WIDTH - 4 || column[height][x + 4] != '.'
        Rock.Cross -> x >= WIDTH - 3 || column[height][x + 2] != '.'
                || (column.size > height + 1 && column[height + 1][x + 3] != '.')
                || (column.size > height + 2 && column[height + 2][x + 2] != '.')
        Rock.L -> x >= WIDTH - 3 || column[height][x + 3] != '.'
                || (column.size > height + 1 && column[height + 1][x + 3] != '.')
                || (column.size > height + 2 && column[height + 2][x + 3] != '.')
        Rock.V4 -> x >= WIDTH - 1 ||column[height][x + 1] != '.'
                || (column.size > height + 1 && column[height + 1][x + 1] != '.')
                || (column.size > height + 2 && column[height + 2][x + 1] != '.')
                || (column.size > height + 3 && column[height + 3][x + 1] != '.')
        Rock.Cube -> x >= WIDTH - 2 || column[height][x + 2] != '.'
                || (column.size > height + 1 && column[height + 1][x + 2] != '.')
    }
}
fun Rock.collidesDown(column: List<CharArray>, height: Int, x: Int): Boolean {
    if (column.size < height) return false
    if (height == 0) return true
    return when (this) {
        Rock.H4 -> column[height-1][x + 0] != '.'
                || column[height-1][x + 1] != '.'
                || column[height-1][x + 2] != '.'
                || column[height-1][x + 3] != '.'
        Rock.Cross -> ((column.size>height) && column[height][x + 0] != '.')
                || column[height-1][x + 1] != '.'
                || (column.size>height && column[height][x + 2] != '.')
        Rock.L -> column[height-1][x + 0] != '.'
                || column[height-1][x + 1] != '.'
                || column[height-1][x + 2] != '.'
        Rock.V4 -> column[height-1][x + 0] != '.'
        Rock.Cube -> column[height-1][x + 0] != '.'
                || column[height-1][x + 1] != '.'
    }
}
fun Rock.draw(column: MutableList<CharArray>, height: Int, x: Int, letter: Char) {
    val h = when (this) {
        Rock.H4 -> 1
        Rock.Cross -> 3
        Rock.L -> 3
        Rock.V4 -> 4
        Rock.Cube -> 2
    }
    while (column.size < height+h)
        column.add(CharArray(WIDTH, {'.'}))
    when (this) {
        Rock.H4 -> {
            column[height][x] = letter
            column[height][x + 1] = letter
            column[height][x + 2] = letter
            column[height][x + 3] = letter
        }
        Rock.Cross -> {
            column[height + 1][x] = letter
            column[height + 1][x + 1] = letter
            column[height + 1][x + 2] = letter
            column[height][x + 1] = letter
            column[height + 2][x + 1] = letter
        }
        Rock.L -> {
            column[height][x] = letter
            column[height][x + 1] = letter
            column[height][x + 2] = letter
            column[height + 1][x + 2] = letter
            column[height + 2][x + 2] = letter
        }
        Rock.V4 -> {
            column[height][x] = letter
            column[height + 1][x] = letter
            column[height + 2][x] = letter
            column[height + 3][x] = letter
        }
        Rock.Cube -> {
            column[height][x] = letter
            column[height][x + 1] = letter
            column[height + 1][x] = letter
            column[height + 1][x + 1] = letter
        }
    }
}

fun part2(input: List<String>): Int {
    val column = mutableListOf<CharArray>()
    var rock = Rock.H4
    var pushes = input[0].toList().listIterator()
    var idx = 0
    while (idx < 2022) {
        var x = 2
        var y = column.size + 4
        do {
            --y
            if (! pushes.hasNext())
                pushes = input[0].toList().listIterator()
            val push = pushes.next()
            if (push == '<' && ! rock.collidesLeft(column, y, x)) x--
            if (push == '>' && ! rock.collidesRight(column, y, x)) x++
        } while (! rock.collidesDown(column, y, x))
        if (idx % 52 < 26)
            rock.draw(column, y, x, 'A'+(idx%52))
        else
            rock.draw(column, y, x, 'a'+(idx%52 - 26))

        rock = rock.next()
        idx++
        for (line in column.reversed()) {
            println(String(line))
        }
        println()
        println("********************************************** new = ${column.size}")
        println()
    }

    return column.size
}

fun main() {
    //val input = readInput("d17/test")
    val input = readInput("d17/input1")

    println(part2(input))
}

