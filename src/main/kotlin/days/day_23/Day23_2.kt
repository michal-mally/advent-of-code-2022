package days.day_23

import util.Solver

class Day23_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val elves = elves(input)
        val directions = directions()

        return generateSequence { performMoves(elves, directions.next()) }.count() + 1
    }

}
