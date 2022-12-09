package days.day_09

import util.Solver
import util.pair.map
import util.pair.toPair
import kotlin.math.sign

class Day09_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val moves = input
            .flatMap {
                it
                    .split(" ")
                    .toPair()
                    .map { first, second -> Direction.valueOf(first).position to second.toInt() }
                    .map { direction, distance -> List(distance) { direction }.asSequence() }
            }

        var knotPositions = MutableList(10) { 0 to 0 }
        val visited = mutableSetOf(0 to 0)
        for (move in moves) {
            knotPositions[0] += move
            for (i in 1..<knotPositions.size) {
                val headPosition = knotPositions[i - 1]
                val tailPosition = knotPositions[i]
                val behind = headPosition - tailPosition
                if (behind.first in (-1..1) && behind.second in (-1..1)) {
                    continue
                }

                knotPositions[i] += behind.first.sign to behind.second.sign
            }

            knotPositions.forEachIndexed() { index, v -> println("$index: $v") }
            visited += knotPositions.last()
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
