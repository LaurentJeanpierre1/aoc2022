package d2

import readInput

data class Res( val ABC : Char, val XYZ: Char)

fun part1(input: List<String>): Int {
    var score = 0
    val rules = mapOf(
        Res('A', 'X') to 3,
        Res('B', 'X') to 0,
        Res('C', 'X') to 6,
        Res('A', 'Y') to 6,
        Res('B', 'Y') to 3,
        Res('C', 'Y') to 0,
        Res('A', 'Z') to 0,
        Res('B', 'Z') to 6,
        Res('C', 'Z') to 3,
    )
    val value = mapOf( 'X' to 1, 'Y' to 2, 'Z' to 3)
    for(line in input) {
        if (line.isNotBlank()) {
            val match = rules.get(Res(line[0], line[2])) ?: -99999
            val bet = value.get(line[2]) ?: -99999
            score += match + bet
        }
    }
    return score
}

fun part2(input: List<String>): Int {
    var score = 0
    val rules = mapOf(
        Res('A', 'X') to 3,
        Res('B', 'X') to 0,
        Res('C', 'X') to 6,
        Res('A', 'Y') to 6,
        Res('B', 'Y') to 3,
        Res('C', 'Y') to 0,
        Res('A', 'Z') to 0,
        Res('B', 'Z') to 6,
        Res('C', 'Z') to 3,
    )
    val expect = mapOf( 'X' to 0, 'Y' to 3, 'Z' to 6)
    val value = mapOf( 'X' to 1, 'Y' to 2, 'Z' to 3)
    for(line in input) {
        if (line.isNotBlank()) {
            var item : Char = ' '
            for ((K,V) in rules) {
                if (K.ABC == line[0])
                    if (V == expect[line[2]])
                        item = K.XYZ
            }
            val match = rules.get(Res(line[0], item)) ?: -99999
            val bet = value.get(item) ?: -99999
            score += match + bet
        }
    }
    return score
}

fun main() {
    val input = readInput("d2/input1")

    println(part2(input))
}

