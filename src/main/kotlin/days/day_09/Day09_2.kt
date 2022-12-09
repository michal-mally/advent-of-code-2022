package days.day_09

import util.Solver

class Day09_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        performMoves(10, moves(input))

}
