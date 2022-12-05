package days.day_04

import util.Solver
import util.pair.mapLeftAndRight
import util.pair.toPair
import util.pair.toRange

class Day04_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .map { it.mapLeftAndRight(IntRange::toSet) } // no need to create a set for each range
            .count { (first, second) -> first.containsAll(second) || second.containsAll(first) }

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

}
