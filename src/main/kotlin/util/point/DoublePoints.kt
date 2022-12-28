package util.point

import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

fun Point<Double>.rotate(degreesClockwise: Double, around: Point<Double> = Point(0.0 to 0.0)): Point<Double> =
    minus(around)
        .let { (x, y) ->
            val radians = toRadians(-degreesClockwise)
            val cos = cos(radians)
            val sin = sin(radians)

            Point(x * cos - y * sin to x * sin + y * cos)
        }
        .plus(around)

fun Point<Double>.toIntPoint(): Point<Int> =
    Point(x.roundToInt() to y.roundToInt())

operator fun Point<Double>.plus(point: Point<Double>): Point<Double> =
    Point(x + point.x to y + point.y)

operator fun Point<Double>.minus(point: Point<Double>): Point<Double> =
    Point(x - point.x to y - point.y)
