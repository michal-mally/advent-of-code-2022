package days.day_01

import util.file.lines
import util.sequence.splitByNull

fun main() {
    lines(1)
        .map { it.toIntOrNull() }
        .splitByNull()
        .map { it.sum() }
        .maxOrNull()
        .let(::println)
}
