package days.day_22

import util.array.TwoDimArray
import util.point.Point
import util.sequence.transpose

data class Side(val location: Point<Int>, val values: TwoDimArray<Square>) {
    val size = values.xCount
}

fun sides(map: Sequence<String>, sideSize: Int) =
    map
        .map { it.map(::Square) }
        .map { it.asSequence() }
        .chunked(sideSize)
        .flatMapIndexed { y, rows -> sides(sideSize, y, rows) }
        .toList()

private fun sides(sideSize: Int, y: Int, rows: List<Sequence<Square?>>) =
    rows
        .asSequence()
        .map { it.chunked(sideSize) }
        .transpose()
        .mapIndexedNotNull { x, side -> Side(Point(x to y), side) }

private fun Side(location: Point<Int>, side: Sequence<List<Square?>>) =
    side
        .takeIf { null !in it.flatMap { it } }
        ?.map { it.map { it!! }.asSequence() }
        ?.transpose()
        ?.map { it.toList() }
        ?.toList()
        ?.let { TwoDimArray(it) }
        ?.let { Side(location, it) }
