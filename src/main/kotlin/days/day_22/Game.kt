package days.day_22

import util.number.nonNegativeModulo
import util.point.*

class Game(sides: List<Side>) {

    private val sides = sides.associateBy { it.location }

    private val edgeConnections = edgeConnections(this.sides)

    private val sideSize = sides.first().size

    private val sideCenter = Point((sideSize - 1) / 2.0 to (sideSize - 1) / 2.0)

    private val startingSide =
        sides.filter { it.location.y == 0 }.minBy { it.location.x }

    fun simulate(instructions: List<Instruction>): LocationAndDirection {
        var locationAndDirection = LocationAndDirection(startingSide, Point(0 to 0), Direction.Right)
        for (instruction in instructions) {
            when (instruction) {
                is Instruction.Rotate -> locationAndDirection =
                    locationAndDirection.rotate(instruction.clockwiseRotation)

                is Instruction.Forward -> for (i in 0..<instruction.steps) {
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
                        forward = LocationAndDirection(sides[sideConnection.side]!!, newPoint, newDirection)
                    }

                    if (forward.squareAt == Square.Wall) {
                        break
                    }

                    locationAndDirection = forward
                }
            }
        }

        return locationAndDirection
    }

    private fun Point<Int>.invertY() =
        Point(x to -y + sideSize - 1)

}
