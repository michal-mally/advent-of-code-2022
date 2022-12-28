package days.day_22

import days.day_22.Day22_2.Direction.Right
import days.day_22.Day22_2.Direction.values
import days.day_22.Day22_2.Instruction.Forward
import days.day_22.Day22_2.Instruction.Rotate
import days.day_22.Day22_2.Square.Empty
import days.day_22.Day22_2.Square.Wall
import util.Solver
import util.array.TwoDimArray
import util.number.nonNegativeModulo
import util.point.*
import util.sequence.splitBy
import util.sequence.transpose

class Day22_2 : Solver<Sequence<String>, Int> {

    private val sideSize = 50

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
        val instructions =
            """\d+|[LR]"""
                .toRegex()
                .findAll(instructionsRaw.first())
                .map { it.value }
                .map { parseInstruction(it) }
                .toList()

        var locationAndDirection = LocationAndDirection(sides.startingSide, Point(0 to 0), Right)
        for (instruction in instructions) {
            when (instruction) {
                is Rotate -> locationAndDirection = locationAndDirection.rotate(instruction.clockwiseRotation)
                is Forward -> for (i in 0..<instruction.steps) {
                    var forward =
                        locationAndDirection.copy(location = locationAndDirection.location + locationAndDirection.direction.point)
                    if (forward.location !in locationAndDirection.side.values) {
                        val sideConnection =
                            connections[locationAndDirection.side.location]!![locationAndDirection.direction]!!
                        var newPoint =
                            Point((forward.location.x nonNegativeModulo sideSize) to (forward.location.y nonNegativeModulo sideSize))
                        newPoint = newPoint
                            .toDoublePoint()
                            .let { Point(it.x to -it.y) }
                            .minus(Point((sideSize - 1) / 2.0 to -(sideSize - 1) / 2.0))
                            .rotate(-sideConnection.clockwiseRotation.toDouble() * 90)
                            .plus(Point((sideSize - 1) / 2.0 to -(sideSize - 1) / 2.0))
                            .let { Point(it.x to -it.y) }
                            .toIntPoint()
                        val newDirection =
                            locationAndDirection.direction.rotateClockwise(-sideConnection.clockwiseRotation)
                        forward = LocationAndDirection(sides.sides[sideConnection.side]!!, newPoint, newDirection)
                    }

                    if (forward.squareAt == Wall) {
                        break
                    }

                    locationAndDirection = forward
                }
            }
        }

        return 1000 * (locationAndDirection.location.y + locationAndDirection.side.location.y * sideSize + 1) + 4 * (locationAndDirection.location.x + locationAndDirection.side.location.x * sideSize + 1) + locationAndDirection.direction.ordinal
    }

    private data class LocationAndDirection(val side: Side, val location: Point<Int>, val direction: Direction) {

        val squareAt
            get() = side.values[location]

        fun rotate(clockwiseRotation: Int) =
            copy(direction = direction.rotateClockwise(clockwiseRotation))

    }

    fun parseInstruction(instruction: String) =
        when (instruction) {
            "L" -> Rotate(-1)
            "R" -> Rotate(1)
            else -> Forward(instruction.toInt())
        }

    sealed interface Instruction {
        data class Rotate(val clockwiseRotation: Int) : Instruction
        data class Forward(val steps: Int) : Instruction
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
            ?.map { it.map { it!! }.asSequence() }
            ?.transpose()
            ?.map { it.toList() }
            ?.toList()
            ?.let { TwoDimArray(it) }
            ?.let { Side(location, it) }

    private data class Sides(val sides: Map<Point<Int>, Side>) {
        val startingSide =
            sides
                .values
                .filter { it.location.y == 0 }
                .minBy { it.location.x }
    }

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
