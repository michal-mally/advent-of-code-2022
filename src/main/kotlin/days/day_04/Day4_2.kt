package days.day_04

import util.file.lines
import util.pair.mapLeftAndRight
import util.pair.toPair
import util.pair.toRange

fun main() {
    lines(4)
        .map(::transformLine)
        .map { (first, second) -> first intersect second }
        .count(Set<Int>::isNotEmpty)
        .let(::println)
}

private fun transformLine(line: String) =
    line
        .split(",")
        .toPair()
        .mapLeftAndRight(::transformToRange)

private fun transformToRange(range: String) =
    range
        .split("-")
        .toPair()
        .mapLeftAndRight(String::toInt)
        .toRange()
