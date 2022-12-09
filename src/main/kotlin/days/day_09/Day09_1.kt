package days.day_09

import util.Solver
import util.pair.map
import util.pair.toPair
import kotlin.math.sign

typealias Position = Pair<Int, Int>

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

        var headPosition = 0 to 0
        var tailPosition = 0 to 0
        val visited = mutableSetOf(tailPosition)
        for (move in moves) {
            headPosition += move
            val behind = headPosition - tailPosition
            if (behind.first in (-1..1) && behind.second in (-1..1)) {
                continue
            }

            tailPosition += behind.first.sign to behind.second.sign
            visited += tailPosition
        }

        return visited.size
    }

    private enum class Direction(val position: Position) {
        U(0 to -1),
        R(1 to 0),
        D(0 to 1),
        L(-1 to 0),
    }

}

private operator fun Position.plus(position: Position) =
    first + position.first to second + position.second

private operator fun Position.minus(position: Position) =
    first - position.first to second - position.second