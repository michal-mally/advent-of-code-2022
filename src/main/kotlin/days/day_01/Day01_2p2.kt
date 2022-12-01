package days.day_01

import util.file.lines
import util.sequence.splitByNull

fun main() {
    lines(1)
        .map { it.toIntOrNull() }
        .splitByNull()
        .map { it.sum() }
        .fold(emptyList<Int>()) { acc, i -> (acc + i).sortedDescending().take(3) }
        .sum()
        .let(::println)
}
