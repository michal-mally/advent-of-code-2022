package days.day_23

import util.Solver
import util.point.Point

class Day23_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val elves = elves(input)
        val directions = directions()

        repeat(10) { performMoves(elves, directions.next()) }

        fun edgeLength(f: (Point<Int>) -> Int) =
            elves.maxOf(f) - elves.minOf(f) + 1

        return edgeLength { it.x } * edgeLength { it.y } - elves.count()
    }

}
