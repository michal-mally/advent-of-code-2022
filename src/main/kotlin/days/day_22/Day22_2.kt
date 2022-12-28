package days.day_22

import days.day_22.Day22_2.Square.Empty
import days.day_22.Day22_2.Square.Wall
import util.Solver
import util.array.TwoDimArray
import util.point.Point
import util.point.plus
import util.sequence.splitBy
import util.sequence.transpose

class Day22_2 : Solver<Sequence<String>, Int> {

    private val sideSize = 4
    override fun solve(input: Sequence<String>): Int {
        val (map, instructionsRaw) = input
            .splitBy { it.isBlank() }
            .toList()

        val sides = map
            .map { it.map(::parseSquare) }
            .map { it.asSequence() }
            .chunked(sideSize)
            .flatMapIndexed { y, rows -> parseSides(y, rows) }
            .associateBy { it.location }
            .toMap()
            .let(::Sides)

        val connections = mutableMapOf<Point<Int>, SideConnections>()
        for (side in sides.sides.values) {
            for (direction in Direction.values()) {
                val neighbour = sides.sides[side.location + direction.point] ?: continue
                connections.getOrPut(side.location) { SideConnections() }.connections[direction] = neighbour
            }
        }

        TODO()
    }

    private fun parseSides(y: Int, rows: List<Sequence<Square?>>) =
        rows
            .asSequence()
            .map { it.chunked(sideSize) }
            .transpose()
            .mapIndexedNotNull { x, side -> parseSide(Point(x to y), side) }

    private fun parseSide(location: Point<Int>, side: Sequence<List<Square?>>) =
        side
            .takeIf { null !in it.flatMap { it } }
            ?.map { it.map { it!! } }
            ?.toList()
            ?.let { TwoDimArray(it) }
            ?.let { Side(location, it) }

    private data class Sides(val sides: Map<Point<Int>, Side>)

    private data class Side(val location: Point<Int>, val values: TwoDimArray<Square>)

    private enum class Direction(val point: Point<Int>) {
        Up(Point(0 to -1)),
        Right(Point(1 to 0)),
        Down(Point(0 to 1)),
        Left(Point(-1 to 0)),
    }

    private data class SideConnections(val connections: MutableMap<Direction, Side> = mutableMapOf()) {
        val complete = connections.size == 4
    }


    private fun parseSquare(square: Char) =
        when (square) {
            ' ' -> null
            '#' -> Wall
            '.' -> Empty
            else -> throw IllegalArgumentException("Invalid square: $square")
        }

    private enum class Square {
        Wall,
        Empty
    }

}
