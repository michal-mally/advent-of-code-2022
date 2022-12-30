package days.day_04

import util.Solver
import util.pair.mapFirstAndSecond

class Day04_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .map { it.mapFirstAndSecond(IntRange::toSet) } // no need to create a set for each range
            .count { (first, second) -> first.containsAll(second) || second.containsAll(first) }

}
