package util.point

import kotlin.math.sign

@JvmInline
value class Point<T>(private val position: Pair<T, T>) {
    val x
        get() = position.first
    val y
        get() = position.second
}

operator fun Point<Int>.plus(position: Point<Int>): Point<Int> =
    Point(x + position.x to y + position.y)

operator fun Point<Int>.minus(position: Point<Int>): Point<Int> =
    Point(x - position.x to y - position.y)

fun Point<Int>.sign(): Point<Int> =
    Point(x.sign to y.sign)
