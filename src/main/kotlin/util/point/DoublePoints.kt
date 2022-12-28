package util.point

import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun Point<Double>.rotate(degreesClockwise: Double): Point<Double> {
    val radians = toRadians(-degreesClockwise)
    val cos = cos(radians)
    val sin = sin(radians)

    return Point(x * cos - y * sin to x * sin + y * cos)
}

fun Point<Double>.toIntPoint(): Point<Int> =
    Point(x.roundToInt() to y.roundToInt())

operator fun Point<Double>.plus(point: Point<Double>): Point<Double> =
    Point(x + point.x to y + point.y)

operator fun Point<Double>.minus(point: Point<Double>): Point<Double> =
    Point(x - point.x to y - point.y)
