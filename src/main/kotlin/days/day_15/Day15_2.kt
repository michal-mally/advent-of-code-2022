package days.day_15

import util.Solver
import util.point.Point
import kotlin.math.abs
import kotlin.math.max

class Day15_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val regex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
        val sensorsAndBeacons = input
            .map { regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input: $it") }
            .map { it.groupValues.drop(1) }
            .map { it.map { it.toInt() } }
            .map { (xs, ys, xb, yb) -> Point(xs to ys) to Point(xb to yb) }
            .toList()
        var x = 0
        var y = 0
        while (true) {
            val xToAdd = sensorsAndBeacons
                .maxOf { (sensor, beacon) ->
                    val distanceToBeacon = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
                    val distanceToXY = abs(sensor.x - x) + abs(sensor.y - y)
                    distanceToBeacon - distanceToXY
                }
                .takeIf { it >= 0 }
                ?: return (x * 4000000L + y)
            x += max(xToAdd, 1)
            if (x > 4000000) {
                x = 0
                y++
            }
        }
    }

}
