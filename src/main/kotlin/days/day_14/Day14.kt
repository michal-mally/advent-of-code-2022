package days.day_14

import util.line.Line
import util.line.allPoints
import util.line.rangeTo
import util.pair.toPair
import util.point.Point
import util.point.plus

fun spotsTaken(input: Sequence<String>) =
    input
        .flatMap(::toLines)
        .flatMap(Line<Int>::allPoints)
        .toMutableSet()

context(MutableSet<Point<Int>>) fun dropGrainsOfSand(lowestElevation: Int, fallThrough: Boolean): Int {
    var stableGrains = 0
    while (true) {
        val fallPosition = dropGrainOfSand(this@MutableSet, lowestElevation, fallThrough) ?: break

        stableGrains++
        this@MutableSet += fallPosition
    }

    return stableGrains
}

private tailrec fun dropGrainOfSand(
    spotsTaken: Set<Point<Int>>,
    lowestElevation: Int,
    fallThrough: Boolean,
    dropPoint: Point<Int> = Point(500 to 0)
): Point<Int>? {
    if (dropPoint in spotsTaken || dropPoint.y >= lowestElevation) {
        return null
    }

    val nextDropPoint = sequenceOf(0 to 1, -1 to 1, 1 to 1)
        .map { Point(it) }
        .map { dropPoint + it }
        .filter { fallThrough || it.y < lowestElevation }
        .filterNot { it in spotsTaken }
        .firstOrNull()
        ?: return dropPoint

    return dropGrainOfSand(spotsTaken, lowestElevation, fallThrough, nextDropPoint)
}

fun toLines(line: String) =
    line
        .splitToSequence(" -> ")
        .map(::toPoint)
        .zipWithNext()
        .map { (from, to) -> from..to }

private fun toPoint(point: String) =
    point
        .split(",")
        .map(String::toInt)
        .toPair()
        .let(::Point)
