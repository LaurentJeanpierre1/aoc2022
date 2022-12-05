package d5

import readInput
import java.util.Scanner
import java.util.Stack


fun part1(input: List<String>): String {
    val (stacks, lineIte) = readStacks(input)

    while (lineIte.hasNext()) {
        val scanner = Scanner(lineIte.next())
        scanner.next() // move
        val qte = scanner.nextInt()
        scanner.next() // from
        val start = scanner.nextInt() - 1
        scanner.next() // to
        val end  = scanner.nextInt() - 1
        repeat (qte) {
            stacks[end].push(stacks[start].pop())
        }
    }

    val res = StringBuffer()
    for (stack in stacks)
        res.append(stack.peek())
    return res.toString()
}

private fun readStacks(input: List<String>): Pair<MutableList<Stack<Char>>, Iterator<String>> {
    val stacks = mutableListOf<Stack<Char>>()
    val lineIte = input.iterator()
    do {
        val line = lineIte.next()
        if (line.isNotBlank()) {
            for (varIdx in 1 until line.length step 4) { // ^] [^
                while (stacks.size <= varIdx / 4)
                    stacks.add(Stack())
                if (line[varIdx] != ' ')
                    stacks[varIdx / 4].push(line[varIdx])
            }
        }
    } while (line.isNotBlank())
    for (stack in stacks) {
        stack.pop() // remove stack index
        stack.reverse() // upside down
    }
    return Pair(stacks, lineIte)
}

fun part2(input: List<String>): String {
    val (stacks, lineIte) = readStacks(input)

    while (lineIte.hasNext()) {
        val scanner = Scanner(lineIte.next())
        scanner.next() // move
        val qte = scanner.nextInt()
        scanner.next() // from
        val start = scanner.nextInt() - 1
        scanner.next() // to
        val end  = scanner.nextInt() - 1
        val tmp = Stack<Char>()
        repeat (qte) {
            tmp.push(stacks[start].pop())
        }
        repeat (qte) {
            stacks[end].push(tmp.pop())
        }
    }

    val res = StringBuffer()
    for (stack in stacks)
        res.append(stack.peek())
    return res.toString()
}

fun main() {
    //val input = readInput("d5/test")
    val input = readInput("d5/input1")

    println(part1(input))
    println(part2(input))
}

