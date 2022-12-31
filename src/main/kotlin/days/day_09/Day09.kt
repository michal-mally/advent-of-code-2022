package days.day_09

import util.direction.Direction
import util.pair.toPair
import util.point.Point
import util.point.ints.ZERO
import util.point.ints.minus
import util.point.ints.plus
import util.point.ints.sign
import kotlin.math.abs

fun Direction(direction: Char) =
    Direction
        .values()
        .firstOrNull { it.name.first() == direction }
        ?: error("Invalid direction: $direction")

fun moves(input: Sequence<String>) =
    input
        .map { it.split(" ") }
        .map { it.toPair() }
        .flatMap { (direction, distance) -> List(distance.toInt()) { Direction(direction.single()) } }

fun performMoves(knotCount: Int, moves: Sequence<Direction>): Int {
    val knotPositions = MutableList(knotCount) { ZERO }
    val visited = mutableSetOf(ZERO)
    for (move in moves) {
        knotPositions[0] += move.point
        updateTailKnotPositions(knotPositions)

        visited += knotPositions.last()
    }

    return visited.size
}

fun updateTailKnotPositions(knotPositions: MutableList<Point<Int>>) {
    fun updateTailKnotPosition(i: Int) {
        val behindHead = knotPositions[i - 1] - knotPositions[i]
        if (abs(behindHead.x) > 1 || abs(behindHead.y) > 1) {
            knotPositions[i] += behindHead.sign()
        }
    }

    for (i in knotPositions.indices.drop(1)) {
        updateTailKnotPosition(i)
    }
}
