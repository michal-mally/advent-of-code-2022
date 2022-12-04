package days.day_04

import util.file.lines
import util.pair.mapLeftAndRight
import util.pair.toPair
import util.pair.toRange

fun main() {
    lines(4)
        .map(::transformLine)
        .map { it.mapLeftAndRight(IntRange::toSet) } // no need to create a set for each range
        .count { (first, second) -> first.containsAll(second) || second.containsAll(first) }
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
