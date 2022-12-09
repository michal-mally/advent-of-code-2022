package days.day_09

import util.Solver
import util.pair.map
import util.pair.toPair
import util.point.Point
import util.point.minus
import util.point.plus
import util.point.sign

class Day09_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val moves = input
            .flatMap {
                it
                    .split(" ")
                    .toPair()
                    .map { first, second -> Direction.valueOf(first).position to second.toInt() }
                    .map { direction, distance -> List(distance) { direction }.asSequence() }
            }

        var headPosition = Point(0 to 0)
        var tailPosition = Point(0 to 0)
        val visited = mutableSetOf(tailPosition)
        for (move in moves) {
            headPosition += move
            val behind = headPosition - tailPosition
            if (behind.x in (-1..1) && behind.y in (-1..1)) {
                continue
            }

            tailPosition += behind.sign()
            visited += tailPosition
        }

        return visited.size
    }

    private enum class Direction(val position: Point<Int>) {
        U(Point(0 to -1)),
        R(Point(1 to 0)),
        D(Point(0 to 1)),
        L(Point(-1 to 0)),
    }

}
