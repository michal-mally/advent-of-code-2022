package days.day_22

import days.day_22.Instruction.Forward
import days.day_22.Instruction.Rotate
import days.day_22.Square.Wall
import util.point.Point
import util.point.nonNegativeModulo
import util.point.rotate
import util.point.toDoublePoint
import util.point.toIntPoint

class Game(sides: List<Side>) {

    private val sides = sides.associateBy { it.location }

    private val edgeConnections = edgeConnections(this.sides)

    private val sideSize = sides.first().size

    private val sideCenter = ((sideSize - 1) / 2.0).let { Point(it to it) }

    fun simulate(instructions: List<Instruction>) =
        instructions.fold(startingLocationAndDirection()) { acc, instruction -> simulateInstruction(acc, instruction) }

    private fun simulateInstruction(locationAndDirection: LocationAndDirection, instruction: Instruction) =
        when (instruction) {
            is Rotate -> locationAndDirection.rotate(instruction.clockwiseRotation)
            is Forward -> generateSequence(locationAndDirection, ::nextLocation)
                .take(instruction.steps + 1)
                .takeWhile { it.squareAt != Wall }
                .last()
        }

    private fun nextLocation(current: LocationAndDirection): LocationAndDirection {
        val next = current.next()
        if (next.location in current.side.values) {
            return next
        }

        val edgeConnection = edgeConnections.getValue(current.side.location)[current.direction]
            ?: error("No edge connection for side ${current.side.location} and direction ${current.direction}")

        return LocationAndDirection(
            side = sides.getValue(edgeConnection.side),
            location = next
                .location
                .let { it nonNegativeModulo Point(sideSize to sideSize) }
                .rotate(-edgeConnection.clockwiseRotationDegrees),
            direction = current
                .direction
                .rotate(-edgeConnection.clockwiseRotationDegrees)
        )
    }

    private fun startingLocationAndDirection() =
        sides
            .values
            .filter { it.location.y == 0 }
            .minBy { it.location.x }
            .let { LocationAndDirection(it, Point(0 to 0), Direction.Right) }

    private fun Point<Int>.rotate(clockwiseRotationDegrees: Int): Point<Int> {
        fun Point<Int>.invertY() = Point(x to -y + sideSize - 1)

        return invertY()
            .toDoublePoint()
            .rotate(clockwiseRotationDegrees.toDouble(), sideCenter)
            .toIntPoint()
            .invertY()
    }

}
