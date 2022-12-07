package d7

import readInput

data class File(val name: String, val content: MutableMap<String, File>, var size: Int) {
    var parent: File? = null

    constructor(name: String, content: MutableMap<String, File>, size: Int, parent: File?) : this(name, content, size) {
        this.parent = parent
    }

    fun print(prefix: String) {
        if (content.isEmpty()) println( "${prefix}${size} ${name}")
        else {
            println("${prefix}${name}:")
            content.forEach{k,v->v.print(prefix+" ")}
        }
    }
}

fun part1(input: List<String>): Int {
    val root = parseFileSystem(input)
    root.print("")
    globalSum = 0
    sizeLess100000(root)
    return globalSum
}

private fun parseFileSystem(input: List<String>): File {
    val root = File("/", mutableMapOf(), 0, null)
    var curDir = root
    var curDirTemp = curDir
    for (line in input) if (line.isNotEmpty()) {
        var words = line.split(" ")
        if (words[0] == "$") {
            val newDir = if (words.size > 2) words[2] else ""
            when (words[1]) {
                "cd" -> when (newDir) {
                    "/" -> curDir = root
                    ".." -> curDir = curDir.parent ?: root
                    "." -> curDir = curDir
                    else -> {
                        if (!(newDir in curDir.content))
                            curDir.content[newDir] = File(newDir, mutableMapOf(), 0, curDir)
                        curDir = curDir.content[newDir]!!
                    }
                }

                "ls" -> if (words.size > 2) {
                    if (!(newDir in curDir.content))
                        curDir.content[newDir] = File(newDir, mutableMapOf(), 0, curDir)
                    curDirTemp = curDir.content[newDir]!!
                } else
                    curDirTemp = curDir
            }
        } else {
            // this is a file
            val name = words[1]
            if (words[0] == "dir") {
                if (!(name in curDir.content))
                    curDirTemp.content[name] = File(name, mutableMapOf(), 0, curDirTemp)
            } else {
                curDirTemp.content[name] = File(name, mutableMapOf(), words[0].toInt(), curDirTemp)
            }
        }
    }
    return root
}

var globalSum = 0
fun sizeLess100000(root: File): Int {
    var sum = root.size + root.content.map { (k,v)-> sizeLess100000(v) }.sum()
    if (sum<100000 && ! root.content.isEmpty()) {
        globalSum += sum
        //print("+")
    }
    //println("debug: ${root.name} -> ${sum}")
    return sum
}

fun part2(input: List<String>): Int {
    val root = parseFileSystem(input)
    globalSum = 0
    val totalSize = sizeLess100000(root)
    val requireSize = 30000000 - (70000000 - totalSize)
    return smallestMoreThan(requireSize, 30000001, root)
}

fun smallestMoreThan(requireSize: Int, curMin: Int, root: File): Int {
    var min = curMin
    root.content.forEach{(k,v)->
        val size = smallestMoreThan(requireSize, min, v)
        if (size>requireSize && size < min)
            min = size
    }
    if (! root.content.isEmpty()) {
        val size = sizeLess100000(root)
        if (size > requireSize && size < min)
            min = size
    }
    return min
}

fun main() {
    //val input = readInput("d7/test")
    val input = readInput("d7/input1")

    println(part1(input))
    println(part2(input))
}

