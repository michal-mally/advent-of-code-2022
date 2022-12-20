package days.day_20

import util.Solver

class Day20_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        input
            .map { it.toLong() }
            .map { it * 811589153 }
            .let { solve(it, numberOfMixes = 10) }

}
