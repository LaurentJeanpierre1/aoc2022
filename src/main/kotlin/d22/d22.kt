package d22

import readInput

enum class Direction(val x: Int, val y:Int) {Right(1,0), Down(0,1), Left(-1,0), Up(0,-1);

    fun turnRight() : Direction = values()[(ordinal+1) % 4]
    fun turnLeft() : Direction = values()[(ordinal+3) % 4]
}

fun part1(input: List<String>): Int {
    val (board, instructions) = readPuzzle(input)
    var lg = 0
    var col = board[0].indexOf('.')
    var facing = Direction.Right

    val forward = Regex("\\d+").findAll(instructions).map { it.value.toInt() }.iterator()
    val turn = Regex("[RL]").findAll(instructions).map { it.value }.iterator()

    while (forward.hasNext()) {
        val distance = forward.next()
        print("${lg+1},${col+1} : $distance to $facing")
        println()
        for(count in 1..distance) {
            var nexCol = col + facing.x
            var nextLg = lg + facing.y
            if (facing==Direction.Left && (nexCol<0 || board[nextLg][nexCol] == ' ')) nexCol = board[lg].size-1 // white spaces not included

            if (facing==Direction.Right && (nexCol>=board[nextLg].size)) nexCol = board[lg].indexOfFirst { it=='.' || it=='#' }

            if (facing == Direction.Up && (nextLg < 0 || board[nextLg][col]==' ')) {
                nextLg = lg
                while (nextLg<board.size-1 && col<board[nextLg+1].size && board[nextLg+1][col] != ' ') {
                    nextLg++
                }
            }

            if (facing == Direction.Down && (nextLg>=board.size || col>=board[nextLg].size || board[nextLg][nexCol] == ' ')) {
                nextLg = lg
                while (nextLg>0 && col<board[nextLg-1].size && board[nextLg-1][col] != ' ') {
                    nextLg--
                }
            }
            if (board[nextLg][nexCol] == '#') break
            // else
            col = nexCol
            lg = nextLg
        }
        if (turn.hasNext())
            facing = if (turn.next() == "R")
                facing.turnRight()
            else
                facing.turnLeft()
    } // while forward
    return 1000*(lg+1) + 4*(col+1) + facing.ordinal
}
// < 17384

data class WormHole(val col: Int, val lg: Int, val facing: Direction)
fun part2(input: List<String>, wormHoles: Map<WormHole,WormHole>): Int {
    val (board, instructions) = readPuzzle(input)
    var lg = 0
    var col = board[0].indexOf('.')
    var facing = Direction.Right

    val forward = Regex("\\d+").findAll(instructions).map { it.value.toInt() }.iterator()
    val turn = Regex("[RL]").findAll(instructions).map { it.value }.iterator()

    while (forward.hasNext()) {
        val distance = forward.next()
        print("${lg+1},${col+1} : $distance to $facing")
        println()
        for(count in 1..distance) {
            var nexCol = col + facing.x
            var nextLg = lg + facing.y
            val exit = wormHoles[WormHole(nexCol, nextLg, facing)]
            if (exit != null) {
                nextLg = exit.lg
                nexCol = exit.col
            }
            if (board[nextLg][nexCol] == '#') break
            // else
            col = nexCol
            lg = nextLg
            if (exit != null)
                facing = exit.facing
        }
        if (turn.hasNext())
            facing = if (turn.next() == "R")
                facing.turnRight()
            else
                facing.turnLeft()
    } // while forward
    return 1000*(lg+1) + 4*(col+1) + facing.ordinal
}

fun readPuzzle(input : List<String>) : Pair<List<CharArray>, String> {
    val board = mutableListOf<CharArray>()
    var instructions = ""
    for (line in input) {
        if (line.isBlank()) continue
        if (line[0] in '0'..'9')
            instructions = line
        else
            board.add(line.toCharArray())
    }
    return Pair(board, instructions)
}
fun main() {
    //val input = readInput("d22/test"); val wormHoles = testWormholes()
    val input = readInput("d22/input1"); val wormHoles = puzzleWormholes()

    //println(part1(input))
    println(part2(input, wormHoles))
}

fun puzzleWormholes(): Map<WormHole, WormHole> {
    /*
       FFEE
       FFEE
       DD
       DD
     CCBB
     CCBB
     AA
     AA
     */
    val res = mutableMapOf<WormHole, WormHole>()
    repeat(50) {
        //F,U -> A,R
        res[WormHole(50 + it, -1, Direction.Up)] = WormHole(0, 150 + it, Direction.Right)
        //E,U -> A,U
        res[WormHole(100 + it, -1, Direction.Up)] = WormHole(0 + it, 199, Direction.Up)
        //E,R -> B,L
        res[WormHole(150, it, Direction.Right)] = WormHole(99, 149 - it, Direction.Left)
        //E,D -> D,L
        res[WormHole(100 + it, 50, Direction.Down)] = WormHole(99, 50 + it, Direction.Left)
        //D,R -> E,U
        res[WormHole(100, 50 + it, Direction.Right)] = WormHole(100 + it, 49, Direction.Up)
        //B,R -> E,L
        res[WormHole(100, 149 - it, Direction.Right)] = WormHole(149, it, Direction.Left)
        //B,D -> A,L
        res[WormHole(50 + it, 150, Direction.Down)] = WormHole(49, 150 + it, Direction.Left)
        //A,R -> B,U
        res[WormHole(50, 150 + it, Direction.Right)] = WormHole(50 + it, 149, Direction.Up)
        //A,D -> E,D
        res[WormHole(0 + it, 200, Direction.Down)] = WormHole(100 + it, 0, Direction.Down)
        //A,L -> F,D
        res[WormHole(-1, 150 + it, Direction.Left)] = WormHole(50 + it, 0, Direction.Down)
        //C,L -> F,R
        res[WormHole(-1, 100 + it, Direction.Left)] = WormHole(50, 49 - it, Direction.Right)
        //C,U -> D,R
        res[WormHole(0 + it, 99, Direction.Up)] = WormHole(50, 50 + it, Direction.Right)
        //D,L -> C,D
        res[WormHole(49, 50 + it, Direction.Left)] = WormHole(0 + it, 100, Direction.Down)
        //F,L -> C,R
        res[WormHole(49, 49 - it, Direction.Left)] = WormHole(0, 100 + it, Direction.Right)
    }
    return res
}

fun testWormholes(): Map<WormHole, WormHole> {
    val res = mutableMapOf<WormHole, WormHole>()
    repeat(4) {
        // 1->2
        res[WormHole(8+it,-1, Direction.Up)] = WormHole(3-it, 4, Direction.Down)
        // 2->1
        res[WormHole(3-it, 3, Direction.Up)] = WormHole(8+it,0, Direction.Down)
        // 1->3
        res[WormHole(7, it, Direction.Left)] = WormHole(4+it, 4, Direction.Down)
        // 3->1
        res[WormHole(4+it, 3, Direction.Up)] = WormHole(8, it, Direction.Right)
        // 1->6
        res[WormHole(12, it, Direction.Right)] = WormHole(15, 11-it, Direction.Left)
        // 6->1
        res[WormHole(16, 11-it, Direction.Right)] = WormHole(11, it, Direction.Left)
        // 4->6
        res[WormHole(12, 4+it, Direction.Right)] = WormHole(15 - it, 8, Direction.Down)
        // 6->4
        res[WormHole(15 - it, 7, Direction.Up)] = WormHole(11, 4+it, Direction.Left)
        // 3->5
        res[WormHole(4+it, 8, Direction.Down)] = WormHole(8, 11-it, Direction.Right)
        // 5->3
        res[WormHole(7, 11-it, Direction.Left)] = WormHole(4+it, 7, Direction.Up)
        // 2->6 L-> U
        res[WormHole(-1, 4+it, Direction.Left)] = WormHole(15 - it, 11, Direction.Up)
        // 6->2
        res[WormHole(15 - it, 12, Direction.Up)] = WormHole(0, 4+it, Direction.Right)
        // 2->5 D->U
        res[WormHole(it,8, Direction.Down)] = WormHole(11-it, 11, Direction.Up)
        // 5->2
        res[WormHole(11-it, 12, Direction.Down)] = WormHole(it,7, Direction.Up)
    }
    return res
}

