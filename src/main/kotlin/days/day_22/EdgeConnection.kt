package days.day_22

import util.direction.Direction
import util.point.Point
import util.point.plus

const val STRAIGHT_ANGLE_DEGREES = 90

data class EdgeConnection(val side: Point<Int>, val clockwiseRotationDegrees: Int = 0) {
    init {
        require(clockwiseRotationDegrees % STRAIGHT_ANGLE_DEGREES == 0) {
            "Clockwise rotation must be a multiple of 90 degrees"
        }
    }
}

data class EdgeConnections(private val connections: MutableMap<Direction, EdgeConnection> = mutableMapOf()) {
    val complete
        get() = connections.size == 4

    fun missingConnections() =
        Direction
            .values()
            .filter { it !in connections }
            .toSet()

    operator fun get(direction: Direction) =
        connections[direction]

    operator fun set(direction: Direction, value: EdgeConnection) {
        connections[direction] = value
    }

}

fun edgeConnections(sides: Map<Point<Int>, Side>): Map<Point<Int>, EdgeConnections> =
    buildMap {
        for (side in sides.values) {
            for (direction in Direction.values()) {
                val neighbour = sides[side.location + direction.point] ?: continue
                getOrPut(side.location) { EdgeConnections() }[direction] = EdgeConnection(neighbour.location)
            }
        }

        while (values.any { !it.complete }) {
            for (edgeConnections in values) {
                for (missing in edgeConnections.missingConnections()) {
                    fun fillMissingEdgeConnection(
                        immediateDirection: Direction,
                        transitiveDirection: Direction,
                        clockwiseRotationDegrees: Int,
                    ) {
                        if (edgeConnections[missing] != null) {
                            return
                        }

                        val immediateConnection = edgeConnections[immediateDirection] ?: return
                        val transitiveConnection = immediateConnection
                            .side
                            .let(::getValue)[transitiveDirection.rotate(-immediateConnection.clockwiseRotationDegrees)]
                            ?: return

                        edgeConnections[missing] = immediateConnection
                            .clockwiseRotationDegrees
                            .plus(transitiveConnection.clockwiseRotationDegrees)
                            .plus(clockwiseRotationDegrees)
                            .let { transitiveConnection.copy(clockwiseRotationDegrees = it) }
                    }

                    for (clockwiseRotationDegrees in listOf(-STRAIGHT_ANGLE_DEGREES, STRAIGHT_ANGLE_DEGREES)) {
                        fillMissingEdgeConnection(
                            missing.rotate(-clockwiseRotationDegrees),
                            missing,
                            clockwiseRotationDegrees
                        )
                    }
                }
            }
        }
    }
