package days.day_02

import util.file.lines
import util.pair.mapBoth
import util.pair.mapLeft
import util.pair.mapRight
import util.pair.toSequence

fun main() {
    lines(2)
        .map { it.split(" ") }
        .map { it[0] to it[1] }
        .map { it.mapBoth { value -> value[0].code } }
        .map { (it.first - 'A'.code) to (it.second - 'X'.code) }
        .map { it.mapRight { l, r -> transformRight(l, r) } }
        .map { it.mapLeft { l, r -> r - l + 4 } }
        .map { it.mapLeft { l, _ -> l % 3 } }
        .map { it.first * 3 to it.second + 1 }
        .flatMap { it.toSequence() }
        .sum()
        .let(::println)
}

fun transformRight(l: Int, r: Int) =
    when (r) {
        0 -> (l + 2) % 3
        1 -> l
        2 -> (l + 1) % 3
        else -> throw IllegalArgumentException("r must be 0, 1, or 2")
    }

