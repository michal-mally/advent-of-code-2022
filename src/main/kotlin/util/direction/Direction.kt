package util.direction

import util.number.ints.nonNegativeModulo
import util.point.Point

const val STRAIGHT_ANGLE_DEGREES = 90

enum class Direction(val point: Point<Int>) {
    Right(Point(1 to 0)),
    Down(Point(0 to 1)),
    Left(Point(-1 to 0)),
    Up(Point(0 to -1)),
    ;

    fun rotate(clockwiseDegrees: Int): Direction {
        require(clockwiseDegrees % STRAIGHT_ANGLE_DEGREES == 0) { "Invalid degrees: $clockwiseDegrees" }
        return values()[(ordinal + clockwiseDegrees / STRAIGHT_ANGLE_DEGREES) nonNegativeModulo values().size]
    }

}
