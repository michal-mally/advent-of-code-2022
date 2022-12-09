package days.day_09

import util.Solver

class Day09_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        performMoves(2, moves(input))

}
