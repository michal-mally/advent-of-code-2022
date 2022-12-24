package util.point

import kotlin.math.abs
import kotlin.math.sign

@JvmInline
value class Point<T>(private val position: Pair<T, T>) {
    val x
        get() = position.first
    val y
        get() = position.second
}


operator fun Point<Int>.plus(point: Point<Int>): Point<Int> =
    Point(x + point.x to y + point.y)

operator fun Point<Int>.minus(point: Point<Int>): Point<Int> =
    Point(x - point.x to y - point.y)

fun Point<Int>.sign(): Point<Int> =
    Point(x.sign to y.sign)

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
