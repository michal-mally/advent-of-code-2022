package days.day_02

import util.file.lines
import util.pair.mapBoth
import util.pair.mapLeft

fun main() {
    lines(2)
        .map { it.split(" ") }
        .map { it[0] to it[1] }
        .map { it.mapBoth { it[0].code } }
        .map { (it.first - 'A'.code + 1) to (it.second - 'X'.code + 1) }
        .map { it.mapLeft { l, r -> result(l, r) } }
        .map { it.first + it.second }
        .sum()
        .let(::println)
}

fun result(left: Int, right: Int): Int {
    val right = if (right < left) right + 3 else right
    return when(right - left) {
        0 -> 3
        1 -> 6
        2 -> 0
        else -> throw IllegalArgumentException()
    }
}
