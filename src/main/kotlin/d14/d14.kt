package d14

import readInput
import java.util.*


fun part1(input: List<String>): Int {
    var minX = 500
    var maxX = 500
    var maxY = 0
    for (line in input) {
        if (line.isBlank()) continue
        val scan = Scanner(line)
        scan.useDelimiter("[,> -]+")
        val x0 = scan.nextInt()
        val y0 = scan.nextInt()
        if (x0<minX) minX = x0
        if (x0>maxX) maxX = x0
        if (y0>maxY) maxY = y0
        while (scan.hasNext()) {
            val x1 = scan.nextInt()
            val y1 = scan.nextInt()
            if (x1<minX) minX = x1
            if (x1>maxX) maxX = x1
            if (y1>maxY) maxY = y1
        }
    }
    val tableau = Array(maxY+2) { CharArray(maxX-minX + 1) {'.'} }
    tableau[0][500-minX]='+'
    for (line in input) {
        val scan = Scanner(line)
        scan.useDelimiter("[,> -]+")
        var x0 = scan.nextInt()
        var y0 = scan.nextInt()
        while (scan.hasNext()) {
            val x1 = scan.nextInt()
            val y1 = scan.nextInt()
            if (x1 == x0)
                if (y0<=y1)
                    for (y in y0..y1 )
                        tableau[y][x0-minX] = '#'
                else
                    for (y in y1..y0 )
                        tableau[y][x0-minX] = '#'
            else if (x1 < x0)
                for (x in x1..x0)
                    tableau[y0][x-minX]='#'
            else
                for (x in x0..x1)
                    tableau[y0][x-minX]='#'
            x0 = x1
            y0 = y1
        }
    }
    for (line in tableau)
        println(line)
    var xs=500
    var ys=0
    var count = 1
    while (xs in minX..maxX && ys<=maxY) {
        if (tableau[ys+1][xs-minX] == '.')
            ys++
        else if (xs==minX || tableau[ys+1][xs-minX-1] == '.') {
            xs--
            ys++
        } else if (xs==maxX || tableau[ys+1][xs-minX+1] == '.') {
            xs++
            ys++
        } else {
            tableau[ys][xs-minX] = 'O'
            count++
            xs=500
            ys=0
        }
    }
    return count-1
}

fun part2(input: List<String>): Int {
    var minX = 500
    var maxX = 500
    var maxY = 0
    for (line in input) {
        if (line.isBlank()) continue
        val scan = Scanner(line)
        scan.useDelimiter("[,> -]+")
        val x0 = scan.nextInt()
        val y0 = scan.nextInt()
        if (x0<minX) minX = x0
        if (x0>maxX) maxX = x0
        if (y0>maxY) maxY = y0
        while (scan.hasNext()) {
            val x1 = scan.nextInt()
            val y1 = scan.nextInt()
            if (x1<minX) minX = x1
            if (x1>maxX) maxX = x1
            if (y1>maxY) maxY = y1
        }
    }
    maxY += 2
    if (minX + maxY >= 500)
        minX = 500 - maxY - 1
    if (maxX - maxY <= 500)
        maxX = 500 + maxY + 1
    val tableau = Array(maxY+2) { CharArray(maxX-minX + 1) {'.'} }
    tableau[0][500-minX]='+'
    for (line in input) {
        val scan = Scanner(line)
        scan.useDelimiter("[,> -]+")
        var x0 = scan.nextInt()
        var y0 = scan.nextInt()
        while (scan.hasNext()) {
            val x1 = scan.nextInt()
            val y1 = scan.nextInt()
            if (x1 == x0)
                if (y0<=y1)
                    for (y in y0..y1 )
                        tableau[y][x0-minX] = '#'
                else
                    for (y in y1..y0 )
                        tableau[y][x0-minX] = '#'
            else if (x1 < x0)
                for (x in x1..x0)
                    tableau[y0][x-minX]='#'
            else
                for (x in x0..x1)
                    tableau[y0][x-minX]='#'
            x0 = x1
            y0 = y1
        }
    }
    for (x in 0 until tableau[maxY].size)
        tableau[maxY][x] = '#'

    for (line in tableau)
        println(line)
    var xs=500
    var ys=0
    var count = 1
    while (xs in minX..maxX && ys<=maxY) {
        if (tableau[ys+1][xs-minX] == '.')
            ys++
        else if (xs==minX || tableau[ys+1][xs-minX-1] == '.') {
            xs--
            ys++
        } else if (xs==maxX || tableau[ys+1][xs-minX+1] == '.') {
            xs++
            ys++
        } else {
            if (xs==500 && ys==0) break
            tableau[ys][xs-minX] = 'O'
            count++
            xs=500
            ys=0
        }
    }
    for (line in tableau)
        println(line)
    return count
}

fun main() {
    //val input = readInput("d14/test")
    val input = readInput("d14/input1")

    println(part1(input))
    println(part2(input))
}

