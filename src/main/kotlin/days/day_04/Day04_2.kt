package days.day_04

import util.Solver

class Day04_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .map { (first, second) -> first intersect second }
            .count(Set<Int>::isNotEmpty)

}
