package days.day_02

import util.file.lines
import util.pair.mapBoth
import util.pair.mapLeft
import util.pair.toSequence

fun main() {
    lines(2)
        .map { it.split(" ") }
        .map { it[0] to it[1] }
        .map { it.mapBoth { value -> value[0].code } }
        .map { (it.first - 'A'.code + 1) to (it.second - 'X'.code + 1) }
        .map { it.mapLeft { l, r -> r - l + 4 } }
        .map { it.mapLeft { l, _ -> l % 3 } }
        .map { it.mapLeft { l, _ -> l * 3 } }
        .flatMap { it.toSequence() }
        .sum()
        .let(::println)
}

