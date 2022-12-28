package days.day_22

import days.day_22.Day22_2.Instruction.Forward
import days.day_22.Day22_2.Instruction.Rotate
import days.day_22.Direction.Right
import days.day_22.Square.Wall
import util.Solver
import util.array.TwoDimArray
import util.number.nonNegativeModulo
import util.point.*
import util.sequence.splitBy
import util.sequence.transpose

class Day22_2 : Solver<Sequence<String>, Int> {

    private val sideSize = 50

    private val sideCenter = Point((sideSize - 1) / 2.0 to (sideSize - 1) / 2.0)

    override fun solve(input: Sequence<String>): Int {
        val (map, instructionsRaw) = input
            .splitBy { it.isBlank() }
            .toList()

        val sides = sides(map)
        val edgeConnections = edgeConnections(sides)
        val instructions = instructions(instructionsRaw)

        var locationAndDirection = LocationAndDirection(sides.startingSide, Point(0 to 0), Right)
        for (instruction in instructions) {
            when (instruction) {
                is Rotate -> locationAndDirection = locationAndDirection.rotate(instruction.clockwiseRotation)
                is Forward -> for (i in 0..<instruction.steps) {
                    var forward =
                        locationAndDirection.copy(location = locationAndDirection.location + locationAndDirection.direction.point)
                    if (forward.location !in locationAndDirection.side.values) {
                        val sideConnection =
                            edgeConnections[locationAndDirection.side.location]!![locationAndDirection.direction]!!
                        var newPoint =
                            Point((forward.location.x nonNegativeModulo sideSize) to (forward.location.y nonNegativeModulo sideSize))
                        newPoint = newPoint
                            .invertY()
                            .toDoublePoint()
                            .rotate(-sideConnection.clockwiseRotationDegrees.toDouble(), sideCenter)
                            .toIntPoint()
                            .invertY()
                        val newDirection =
                            locationAndDirection.direction.rotate(-sideConnection.clockwiseRotationDegrees)
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

    private fun instructions(instructionsRaw: Sequence<String>) =
        """\d+|[LR]"""
            .toRegex()
            .findAll(instructionsRaw.first())
            .map { it.value }
            .map { parseInstruction(it) }
            .toList()

    private fun sides(map: Sequence<String>) =
        map
            .map { it.map(::Square) }
            .map { it.asSequence() }
            .chunked(sideSize)
            .flatMapIndexed { y, rows -> sides(y, rows) }
            .associateBy { it.location }
            .toMap()
            .let(::Sides)

    private fun Point<Int>.invertY() =
        Point(x to -y + sideSize - 1)

    private data class LocationAndDirection(val side: Side, val location: Point<Int>, val direction: Direction) {

        val squareAt
            get() = side.values[location]

        fun rotate(clockwiseRotation: Int) =
            copy(direction = direction.rotate(clockwiseRotation))

    }

    fun parseInstruction(instruction: String) =
        when (instruction) {
            "L" -> Rotate(-STRAIGHT_ANGLE_DEGREES)
            "R" -> Rotate(STRAIGHT_ANGLE_DEGREES)
            else -> Forward(instruction.toInt())
        }

    sealed interface Instruction {
        data class Rotate(val clockwiseRotation: Int) : Instruction
        data class Forward(val steps: Int) : Instruction
    }

    private fun edgeConnections(sides: Sides): Map<Point<Int>, EdgeConnections> =
        buildMap {
            for (side in sides.sides.values) {
                for (direction in Direction.values()) {
                    val neighbour = sides.sides[side.location + direction.point] ?: continue
                    getOrPut(side.location) { EdgeConnections() }[direction] = EdgeConnection(neighbour.location)
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
                                this[sideConnection1.side]!![second.rotate(-sideConnection1.clockwiseRotationDegrees)]
                                    ?: return
                            sideConnections[missing] =
                                sideConnection2.copy(clockwiseRotationDegrees = sideConnection1.clockwiseRotationDegrees + sideConnection2.clockwiseRotationDegrees + clockwiseRotation)
                        }

                        for (rotation in listOf(-STRAIGHT_ANGLE_DEGREES, STRAIGHT_ANGLE_DEGREES)) {
                            fillConnection(missing.rotate(-rotation), missing, rotation)
                        }
                    }
                }
            }
        }

    private fun sides(y: Int, rows: List<Sequence<Square?>>) =
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

    data class EdgeConnections(private val connections: MutableMap<Direction, EdgeConnection> = mutableMapOf()) {
        val complete
            get() = connections.size == 4

        fun incomplete() =
            Direction
                .values()
                .filter { it !in connections }
                .toSet()

        operator fun get(direction: Direction) =
            connections[direction]

        operator fun set(direction: Direction, value: EdgeConnection) {
            connections[direction] = value
        }

        val values: Map<Direction, EdgeConnection>
            get() = connections

    }

}
