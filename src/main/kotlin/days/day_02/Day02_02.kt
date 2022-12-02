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
        .map { it.mapRight { l, r -> (l + r + 2) % 3 } }
        .map { it.mapLeft { l, r -> r - l + 4 } }
        .map { it.mapLeft { l, _ -> l % 3 } }
        .map { it.first * 3 to it.second + 1 }
        .flatMap { it.toSequence() }
        .sum()
        .let(::println)
}
