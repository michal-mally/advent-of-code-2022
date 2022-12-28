package days.day_22

import days.day_22.Square.Empty
import days.day_22.Square.Wall
import util.number.nonNegativeModulo
import util.point.Point

const val STRAIGHT_ANGLE_DEGREES = 90

data class EdgeConnection(val side: Point<Int>, val clockwiseRotationDegrees: Int = 0) {
    init {
        require(clockwiseRotationDegrees % 90 == 0) { "Clockwise rotation must be a multiple of 90 degrees" }
    }
}

fun Square(square: Char) =
    when (square) {
        ' ' -> null
        '#' -> Wall
        '.' -> Empty
        else -> throw IllegalArgumentException("Invalid square: $square")
    }

enum class Square { Wall, Empty }


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
