package days.day_11

import util.Solver

class Day11_1 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        play(input, 20, 3.toBigInteger())
}
