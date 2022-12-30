package util.point

import util.number.nonNegativeModulo
import kotlin.math.abs
import kotlin.math.sign

operator fun Point<Int>.plus(point: Point<Int>): Point<Int> =
    Point(x + point.x to y + point.y)

operator fun Point<Int>.minus(point: Point<Int>): Point<Int> =
    Point(x - point.x to y - point.y)

fun Point<Int>.sign(): Point<Int> =
    Point(x.sign to y.sign)

fun Point<Int>.neighbours(): Sequence<Point<Int>> =
    sequenceOf(
        x - 1 to y,
        x + 1 to y,
        x to y - 1,
        x to y + 1,
    )
        .map { Point(it) }

fun Point<Int>.adjacents(): Sequence<Point<Int>> =
    sequence {
        for (x in -1..1) {
            for (y in -1..1) {
                if (x == 0 && y == 0) continue
                yield(this@adjacents + Point(x to y))
            }
        }
    }

val north = Point(0 to -1)
val east = Point(1 to 0)
val south = Point(0 to 1)
val west = Point(-1 to 0)

infix fun Point<Int>.manhattanDistanceTo(other: Point<Int>): Int =
    abs(x - other.x) + abs(y - other.y)

fun Point<Int>.toDoublePoint(): Point<Double> =
    Point(x.toDouble() to y.toDouble())

operator fun Point<Int>.times(other: Point<Int>): Point<Int> =
    Point(x * other.x to y * other.y)

operator fun Point<Int>.times(other: Int): Point<Int> =
    this * Point(other to other)

fun Point<Int>.toList(): List<Int> =
    listOf(x, y)

infix fun Point<Int>.nonNegativeModulo(other: Point<Int>): Point<Int> =
    Point((x nonNegativeModulo other.x) to (y nonNegativeModulo other.y))

val ZERO = Point(0 to 0)
