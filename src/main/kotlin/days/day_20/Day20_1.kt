package days.day_20

import util.Solver

class Day20_1 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        input
            .map { it.toLong() }
            .let { solve(it) }

}
