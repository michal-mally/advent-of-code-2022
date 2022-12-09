package days.day_08

import util.array.TwoDimArray
import util.point.Point

typealias Forest = TwoDimArray<Int>

@JvmInline
value class Direction(val points: Sequence<Point<Int>>)

fun forest(input: Sequence<String>) =
    input
        .map { it.toList() }
        .map { it.map { c -> c.toString().toInt() } }
        .toList()
        .let(::Forest)
