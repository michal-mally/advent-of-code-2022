package days.day_22

import days.day_22.Instruction.Forward
import days.day_22.Instruction.Rotate
import days.day_22.Square.Wall
import util.number.nonNegativeModulo
import util.point.Point
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

        val edgeConnection = edgeConnections[current.side.location]!![current.direction]!!

        return LocationAndDirection(
            sides[edgeConnection.side]!!,
            next
                .location
                .let { (x, y) -> (x nonNegativeModulo sideSize) to (y nonNegativeModulo sideSize) }
                .let { Point(it) }
                .rotate(-edgeConnection.clockwiseRotationDegrees),
            current.direction.rotate(-edgeConnection.clockwiseRotationDegrees)
        )
    }

    private fun startingLocationAndDirection(): LocationAndDirection {
        fun startingSide() =
            sides
                .values
                .filter { it.location.y == 0 }
                .minBy { it.location.x }

        return LocationAndDirection(startingSide(), Point(0 to 0), Direction.Right)
    }

    private fun Point<Int>.rotate(clockwiseRotationDegrees: Int) =
        invertY()
            .toDoublePoint()
            .rotate(clockwiseRotationDegrees.toDouble(), sideCenter)
            .toIntPoint()
            .invertY()

    private fun Point<Int>.invertY() =
        Point(x to -y + sideSize - 1)

}
