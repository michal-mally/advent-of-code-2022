package days.day_22

import util.point.Point
import util.point.plus

data class LocationAndDirection(val side: Side, val location: Point<Int>, val direction: Direction) {

    val squareAt
        get() = side.values[location]

    fun next() =
        copy(location = location + direction.point)

    fun rotate(clockwiseRotationDegrees: Int) =
        copy(direction = direction.rotate(clockwiseRotationDegrees))

    val universalLocation
        get() = Point(location.x + side.location.x * side.size to location.y + side.location.y * side.size)

}
