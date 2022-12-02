package days.day_02

import util.file.lines
import util.pair.*

fun main() {
    lines(2)
        .map { it.split(" ") }
        .map { it.map { value -> value[0].code } }
        .map { it.toPair() }
        .map { (it.first - 'A'.code) to (it.second - 'X'.code) }
        .map { it.mapRight { l, r -> l + r + 2 } }
        .map { it.mapLeft { l, r -> r - l + 4 } }
        .map { it.mapLeftAndRight { v -> v % 3 } }
        .map { it.first * 3 to it.second + 1 }
        .flatMap { it.toSequence() }
        .sum()
        .let(::println)
}
