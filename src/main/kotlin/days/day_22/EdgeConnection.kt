package days.day_22

import util.point.Point
import util.point.plus

const val STRAIGHT_ANGLE_DEGREES = 90

data class EdgeConnection(val side: Point<Int>, val clockwiseRotationDegrees: Int = 0) {
    init {
        require(clockwiseRotationDegrees % 90 == 0) { "Clockwise rotation must be a multiple of 90 degrees" }
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

    val values: Map<Direction, EdgeConnection>
        get() = connections

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
            for (sideConnections in values) {
                for (missing in sideConnections.missingConnections()) {
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
