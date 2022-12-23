package days.day_23

import util.Solver

class Day23_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(elves(input)) {
            val directions = directions()
            generateSequence { performMoves(this, directions.next()) }.count() + 1
        }

}
