package days.day_02

import util.file.lines
import util.pair.mapLeft
import util.pair.mapLeftAndRight
import util.pair.toPair
import util.pair.toSequence

fun main() {
    lines(2)
        .map { it.split(" ") }
        .map { it.toPair() }
        .map { it.mapLeftAndRight { value -> value[0].code } }
        .map { (it.first - 'A'.code) to (it.second - 'X'.code) }
        .map { it.mapLeft { l, r -> r - l + 4 } }
        .map { it.mapLeftAndRight { v -> v % 3 } }
        .map { it.first * 3 to it.second + 1 }
        .flatMap { it.toSequence() }
        .sum()
        .let(::println)
}

