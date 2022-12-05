package days.day_01

import util.Solver
import util.sequence.splitByNull

class Day01_2p2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map { it.toIntOrNull() }
            .splitByNull()
            .map { it.sum() }
            .fold(emptyList<Int>()) { acc, i -> (acc + i).sortedDescending().take(3) }
            .sum()

}
