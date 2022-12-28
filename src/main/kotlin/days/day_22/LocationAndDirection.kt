package days.day_22

import util.point.Point

data class LocationAndDirection(val side: Side, val location: Point<Int>, val direction: Direction) {

    val squareAt
        get() = side.values[location]

    fun rotate(clockwiseRotation: Int) =
        copy(direction = direction.rotate(clockwiseRotation))

    val universalLocation
        get() = Point(location.x + side.location.x * side.size to location.y + side.location.y * side.size)

}
