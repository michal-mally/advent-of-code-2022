package days.day_14

import util.Solver

class Day14_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(spotsTaken(input)) {
            dropGrainsOfSand(maxOf { it.y }, true)
        }
}
