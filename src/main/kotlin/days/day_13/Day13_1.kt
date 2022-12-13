package days.day_13

import util.Solver
import util.sequence.splitBy
import util.sequence.toPair

class Day13_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .splitBy { it.isBlank() }
            .map(::parseGroup)
            .mapIndexed { index, v -> (index + 1) to (v.first <= v.second) }
            .filter { (_, orderCorrect) -> orderCorrect }
            .map { (index, _) -> index }
            .sum()

    private fun parseGroup(group: Sequence<String>) =
        group.map(::parseLine).toPair()

}
