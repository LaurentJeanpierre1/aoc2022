package d8

import readInput


fun part1(input: List<String>): Int {
    val trees = mutableListOf<List<Int>>()
    for (line in input) {
        if (line.isNotBlank())
            trees.add(line.toList().map { v-> v-'0' })
    }
    var nbVis = 0
    val lines = trees.size
    for ((idxL, line) in trees.withIndex()) {
        if (idxL in 1 until lines-1) {
            val cols = line.size
            for ((idxC, col) in line.withIndex()) {
                if (idxC !in 1 until cols-1)
                    nbVis++
                else {
                    var visible = true
                    val height = trees[idxL][idxC]
                    var c = idxC - 1
                    while (visible && c >= 0) {
                        visible = trees[idxL][c] < height
                        --c
                    }
                    if (! visible) {
                        visible = true
                        c = idxC + 1
                        while (visible && c < cols) {
                            visible = trees[idxL][c] < height
                            ++c
                        }
                    }
                    if (! visible) {
                        visible = true
                        var l = idxL - 1
                        while (visible && l >= 0) {
                            visible = trees[l][idxC] < height
                            --l
                        }
                    }
                    if (! visible) {
                        visible = true
                        var l = idxL + 1
                        while (visible && l < lines) {
                            visible = trees[l][idxC] < height
                            ++l
                        }
                    }
                    if (visible)
                        nbVis++
                }
            }
        } else {
            // whole line visible
            nbVis += line.size
        }
    }
    return nbVis
}

fun part2(input: List<String>): Int {
    val trees = mutableListOf<List<Int>>()
    for (line in input) {
        if (line.isNotBlank())
            trees.add(line.toList().map { v-> v-'0' })
    }
    var maxScore = 0
    val lines = trees.size
    for ((idxL, line) in trees.withIndex()) {
        if (idxL in 1 until lines-1) {
            val cols = line.size
            for ((idxC, col) in line.withIndex()) {
                if (idxC in 1 until cols-1) {
                    var score = 1
                    var visible = 0
                    val height = trees[idxL][idxC]
                    var c = idxC - 1
                    while (c >= 0 && trees[idxL][c] < height) {
                        --c
                        ++visible
                    }
                    if (c>0)
                        ++visible
                    if (visible>0)
                        score *= visible
                    visible = 0
                    c = idxC + 1
                    while (c < cols && trees[idxL][c] < height) {
                        ++c
                        ++visible
                    }
                    if (c<cols)
                        ++visible
                    if (visible>0)
                        score *= visible
                    visible = 0
                    var l = idxL - 1
                    while (l >= 0 && trees[l][idxC] < height) {
                        --l
                        ++visible
                    }
                    if (l>0)
                        ++visible
                    if (visible>0)
                        score *= visible
                    visible = 0
                    l = idxL + 1
                    while (l < lines && trees[l][idxC] < height) {
                        ++l
                        ++visible
                    }
                    if (l<lines)
                        ++visible
                    if (visible>0)
                        score *= visible
                    if (score > maxScore)
                        maxScore = score
                }
            }
        }
    }
    return maxScore
}

fun main() {
    //val input = readInput("d8/test")
    val input = readInput("d8/input1")

    println(part1(input))
    println(part2(input))
}

