package days.day_04

import util.Solver
import util.pair.mapFirstAndSecond
import util.pair.toPair
import util.pair.toRange

class Day04_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .map { it.mapFirstAndSecond(IntRange::toSet) } // no need to create a set for each range
            .count { (first, second) -> first.containsAll(second) || second.containsAll(first) }

    private fun transformLine(line: String) =
        line
            .split(",")
            .toPair()
            .mapFirstAndSecond(::transformToRange)

    private fun transformToRange(range: String) =
        range
            .split("-")
            .toPair()
            .mapFirstAndSecond(String::toInt)
            .toRange()

}
