package d17

import readInput

class Part2b {
    var WIDTH = 7
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

    fun part2(input: List<String>): Long {
        val column = mutableListOf<CharArray>()
        var rock = Rock.H4
        val charList = input[0].toList()
        var pushes = charList.listIterator()
        var idx = 0L
        val heights = mutableListOf<Int>()
        while (idx < 1_000_000L) {
            var x = 2
            var y = column.size + 4
            do {
                --y
                if (! pushes.hasNext())
                    pushes = charList.listIterator()
                val push = pushes.next()
                if (push == '<' && ! rock.collidesLeft(column, y, x)) x--
                if (push == '>' && ! rock.collidesRight(column, y, x)) x++
            } while (! rock.collidesDown(column, y, x))
            if (idx % 52 < 26)
                rock.draw(column, y, x, 'A'+((idx%52L).toInt()))
            else
                rock.draw(column, y, x, 'a'+((idx%52L).toInt() - 26))
            rock = rock.next()
            heights.add(column.size)
            idx++
            if (column.size>2000)
            for (period in 1 .. column.size/5) {
                var found = true
                for (y1 in column.size-1 downTo  column.size-1-5*period)
                    if (!column[y1].contentEquals(column[y1-period])) {
                        found = false
                        break
                    }
                if (found) {
                    println("idx = $idx, period = $period")
                    for (y1 in column.size-1-period downTo  0 )
                        if (!column[y1].contentEquals(column[y1+period])) {
                            println("Starting at $y1")
                            for (blkPeriod in 1 .. idx/5) {
                                found = true
                                var lines = 0
                                for (blk in idx - 1 downTo idx - blkPeriod * 5) {
                                    val h1 = heights[blk.toInt()]
                                    val h2 = heights[(blk - blkPeriod).toInt()]
                                    lines = h1 - h2
                                    if (h1 > h2 && h1 % period != h2 % period) {
                                        found = false
                                        break
                                    }
                                }
                                if (found) {
                                    println("Found BLOCK period at $blkPeriod for $lines lines")
                                    for (blk in idx-1-blkPeriod downTo 0) {
                                        val h1 = heights[blk.toInt()]
                                        val h2 = heights[(blk + blkPeriod).toInt()]
                                        if (h1 % period != h2 % period) {
                                            println("  Starting at ${blk+1} for ${heights[blk.toInt()+1]}")
                                            val nbPeriods = (1_000_000_000_000L - blk - 1L) / blkPeriod
                                            val inPeriod = (1_000_000_000_000L - blk - 1L) % blkPeriod
                                            val inPeriod2 = (1_000_000_000_000L) % blkPeriod
                                            val proj = heights[(blk + 1L + inPeriod).toInt()] + nbPeriods * lines -1
                                            val proj2 = heights[inPeriod2.toInt()] + nbPeriods * lines -1
                                            println("==> Projection at 1_000_000_000_000 is $proj or $proj2")
                                            return proj
                                        }
                                    }
                                    break
                                }
                            }
                            break
                        }
                    break
                }
            }
        }
        return 0L
    }

}
fun main() {
    //val input = readInput("d17/test")
    val input = readInput("d17/input1")

    println(Part2b().part2(input))
}
