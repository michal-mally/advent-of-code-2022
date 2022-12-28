package days.day_22

import days.day_22.Day22_2.Direction.values
import days.day_22.Day22_2.Square.Empty
import days.day_22.Day22_2.Square.Wall
import util.Solver
import util.array.TwoDimArray
import util.number.nonNegativeModulo
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

        val connections = buildSideConnections(sides)

        connections
            .forEach { (side, cons) ->
                println("$side:")
                cons.values.forEach { (dir, side) ->
                    println("  $dir -> $side")
                }
                println()
            }

        TODO()
    }

    private fun buildSideConnections(sides: Sides): Map<Point<Int>, SideConnections> =
        buildMap {
            for (side in sides.sides.values) {
                for (direction in values()) {
                    val neighbour = sides.sides[side.location + direction.point] ?: continue
                    getOrPut(side.location) { SideConnections() }[direction] = SideConnection(neighbour.location)
                }
            }

            while (values.any { !it.complete }) {
                for (sideConnections in values) {
                    for (missing in sideConnections.incomplete()) {
                        fun fillConnection(first: Direction, second: Direction, clockwiseRotation: Int) {
                            if (sideConnections[missing] != null) {
                                return
                            }

                            val sideConnection1 = sideConnections[first] ?: return
                            val sideConnection2 =
                                this[sideConnection1.side]!![second.rotateClockwise(-sideConnection1.clockwiseRotation)]
                                    ?: return
                            sideConnections[missing] =
                                sideConnection2.copy(clockwiseRotation = sideConnection1.clockwiseRotation + sideConnection2.clockwiseRotation + clockwiseRotation)
                        }

                        for (rotation in listOf(-1, 1)) {
                            fillConnection(missing.rotateClockwise(-rotation), missing, rotation)
                        }
                    }
                }
            }
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
        Right(Point(1 to 0)),
        Down(Point(0 to 1)),
        Left(Point(-1 to 0)),
        Up(Point(0 to -1)),
        ;

        fun rotateClockwise(times: Int) = values()[(ordinal + times) nonNegativeModulo values().size]
    }

    private data class SideConnections(
        private val connections: MutableMap<Direction, SideConnection> = mutableMapOf(),
    ) {
        val complete
            get() = connections.size == 4

        fun incomplete() =
            Direction
                .values()
                .filter { it !in connections }
                .toSet()

        operator fun get(direction: Direction) =
            connections[direction]

        operator fun set(direction: Direction, value: SideConnection) {
            connections[direction] = value
        }

        val values: Map<Direction, SideConnection>
            get() = connections

    }

    private data class SideConnection(val side: Point<Int>, val clockwiseRotation: Int = 0)

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
