package days.day_15

import util.point.Point
import kotlin.math.abs

private val inputRegex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")

fun sensorsAndBeacons(input: Sequence<String>): Sequence<Pair<Point<Int>, Point<Int>>> {
    fun sensorAndBeacon(input: String) =
        (inputRegex.matchEntire(input) ?: throw IllegalArgumentException("Invalid input: $input"))
            .groupValues
            .drop(1)
            .map(String::toInt)
            .let { (xs, ys, xb, yb) -> Point(xs to ys) to Point(xb to yb) }

    return input.map(::sensorAndBeacon)
}

fun Point<Int>.manhattanDistanceTo(other: Point<Int>) = abs(x - other.x) + abs(y - other.y)
