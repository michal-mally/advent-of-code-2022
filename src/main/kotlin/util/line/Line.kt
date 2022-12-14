package util.line

import util.point.Point
import util.point.minus
import util.point.plus
import util.point.sign

data class Line<T>(val from: Point<T>, val to: Point<T>)

fun Line<Int>.allPoints(): Sequence<Point<Int>> =
    sequence {
        require(from.x == to.x || from.y == to.y) { "Line must be horizontal or vertical" }

        yield(from)

        val sign = (to - from).sign()
        var current = from
        while (current != to) {
            current += sign
            yield(current)
        }
    }

operator fun <T> Point<T>.rangeTo(other: Point<T>): Line<T> =
    Line(this, other)
