package d20

import readInput

fun part1(input: List<String>): Int {
    val numbers = input.map { it.toInt() }.toList()
    val list = MutableList(numbers.size) { it }
    println(numbers)
    println(list)
    for (number in numbers.withIndex()) {
        val pos = list.indexOf(number.index)
        var newPos = (pos + number.value) % (numbers.size - 1 )
        while (newPos < 0)
            newPos += numbers.size-1 // before first and after last are the same
        if (pos != newPos) {
            list.removeAt(pos)
            list.add(newPos, number.index)
        }
        println(list)
    }

    var sum = 0
    val posZero = list.indexOf(numbers.indexOf(0))
    println("zero is at $posZero")
    var newPos = (posZero+1000) % (list.size)
    println("1000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    newPos = (posZero+2000) % (list.size)
    println("2000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    newPos = (posZero+3000) % (list.size)
    println("3000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    return sum
}

fun part2(input: List<String>): Long {
    val numbers = input.map { it.toLong() * 811589153 }.toList()
    val list = MutableList(numbers.size) { it }
    println(numbers)
    println(list)
    repeat(10) { round ->
        for (number in numbers.withIndex()) {
            val pos = list.indexOf(number.index)
            var newPos = (pos + number.value) % (numbers.size - 1)
            while (newPos < 0)
                newPos += numbers.size - 1 // before first and after last are the same
            if (pos != newPos.toInt()) {
                list.removeAt(pos)
                list.add(newPos.toInt(), number.index)
            }
//            println(list)
        }
        println("$round : ${list.map { numbers[it] }}")
    }
    var sum = 0L
    val posZero = list.indexOf(numbers.indexOf(0))
    println("zero is at $posZero")
    var newPos = (posZero+1000) % (list.size)
    println("1000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    newPos = (posZero+2000) % (list.size)
    println("2000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    newPos = (posZero+3000) % (list.size)
    println("3000th = ${numbers[list[newPos]]}")
    sum += numbers[list[newPos]]
    return sum
}

fun main() {
    //val input = readInput("d20/test")
    //val input = readInput("d20/test2")
    val input = readInput("d20/input1")

    //println(part1(input))
    println(part2(input))
}

