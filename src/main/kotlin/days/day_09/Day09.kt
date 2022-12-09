package days.day_09

import util.pair.toPair
import util.point.Point
import util.point.minus
import util.point.plus
import util.point.sign
import kotlin.math.abs

fun Point(direction: String) = Point(
    when (direction) {
        "U" -> 0 to -1
        "R" -> 1 to 0
        "D" -> 0 to 1
        "L" -> -1 to 0
        else -> throw IllegalArgumentException("Unknown direction: $direction")
    }
)

fun moves(input: Sequence<String>) =
    input
        .map { it.split(" ") }
        .map { it.toPair() }
        .flatMap { (direction, distance) -> List(distance.toInt()) { Point(direction) } }

fun performMoves(knotCount: Int, moves: Sequence<Point<Int>>): Int {
    val knotPositions = MutableList(knotCount) { Point(0 to 0) }
    val visited = mutableSetOf(Point(0 to 0))
    for (move in moves) {
        knotPositions[0] += move
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
