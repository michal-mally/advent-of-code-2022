package days.day_01

import util.Solver
import util.sequence.splitByNull

class Day01_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map { it.toIntOrNull() }
            .splitByNull()
            .maxOf { it.sum() }

}
