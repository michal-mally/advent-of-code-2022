package days.day_04

import util.Solver
import util.pair.mapLeftAndRight
import util.pair.toPair
import util.pair.toRange

class Day04_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .map { (first, second) -> first intersect second }
            .count(Set<Int>::isNotEmpty)

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
