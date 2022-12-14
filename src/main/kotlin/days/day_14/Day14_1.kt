package days.day_14

import util.Solver
import util.line.Line
import util.line.allPoints
import util.line.rangeTo
import util.pair.toPair
import util.point.Point
import util.point.plus

class Day14_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val spotsTaken = input
            .flatMap(::toLines)
            .flatMap(Line<Int>::allPoints)
            .toMutableSet()

        val lowestElevation = spotsTaken.maxOf { it.y }

        var stableGrains = 0
        while (true) {
            val fallPosition = dropGrainOfSand(spotsTaken, lowestElevation) ?: break

            stableGrains++
            spotsTaken += fallPosition
        }

        return stableGrains
    }

    private tailrec fun dropGrainOfSand(
        spotsTaken: Set<Point<Int>>,
        lowestElevation: Int,
        dropPoint: Point<Int> = Point(500 to 0)
    ): Point<Int>? {
        require(dropPoint !in spotsTaken) { "Drop point must not be on already filled square" }

        if (dropPoint.y >= lowestElevation) {
            return null
        }

        val nextDropPoint = sequenceOf(0 to 1, -1 to 1, 1 to 1)
            .map { Point(it) }
            .map { dropPoint + it }
            .filterNot { it in spotsTaken }
            .firstOrNull()
            ?: return dropPoint

        return dropGrainOfSand(spotsTaken, lowestElevation, nextDropPoint)
    }

    private fun toLines(line: String) =
        line
            .splitToSequence(" -> ")
            .map(::toPoint)
            .zipWithNext()
            .map { (from, to) -> from..to }

    private fun toPoint(point: String) =
        point
            .splitToSequence(",")
            .map(String::toInt)
            .toList()
            .toPair()
            .let(::Point)

}
