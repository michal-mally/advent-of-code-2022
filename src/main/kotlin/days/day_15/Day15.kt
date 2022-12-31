package days.day_15

import util.point.Point

private val inputRegex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")

fun sensorsAndBeacons(input: Sequence<String>): Sequence<Pair<Point<Long>, Point<Long>>> {
    fun sensorAndBeacon(input: String) =
        (inputRegex.matchEntire(input) ?: throw IllegalArgumentException("Invalid input: $input"))
            .groupValues
            .drop(1)
            .map(String::toLong)
            .let { (xs, ys, xb, yb) -> Point(xs to ys) to Point(xb to yb) }

    return input.map(::sensorAndBeacon)
}
