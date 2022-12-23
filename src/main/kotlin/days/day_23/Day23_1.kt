package days.day_23

import util.Solver
import util.point.Point

class Day23_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(elves(input)) {
            fun edgeLength(f: (Point<Int>) -> Int) =
                maxOf(f) - minOf(f) + 1

            val directions = directions()

            repeat(10) { performMoves(this, directions.next()) }

            edgeLength { it.x } * edgeLength { it.y } - count()
        }

}
