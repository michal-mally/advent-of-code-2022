package days.day_13

import util.Solver
import util.sequence.filterIndexes
import util.sequence.splitBy
import util.sequence.toPair

class Day13_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .splitBy { it.isBlank() }
            .map(::parseGroup)
            .filterIndexes { it.first <= it.second }
            .map { it + 1 }
            .sum()

    private fun parseGroup(group: Sequence<String>) =
        group.map(::parseLine).toPair()

}
