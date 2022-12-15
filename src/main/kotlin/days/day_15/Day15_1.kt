package days.day_15

import util.Solver
import util.line.Line
import util.point.Point
import kotlin.math.abs

//Sensor at x=2, y=18: closest beacon is at x=-2, y=15
//Sensor at x=9, y=16: closest beacon is at x=10, y=16
//Sensor at x=13, y=2: closest beacon is at x=15, y=3
//Sensor at x=12, y=14: closest beacon is at x=10, y=16
//Sensor at x=10, y=20: closest beacon is at x=10, y=16
//Sensor at x=14, y=17: closest beacon is at x=10, y=16
//Sensor at x=8, y=7: closest beacon is at x=2, y=10
//Sensor at x=2, y=0: closest beacon is at x=2, y=10
//Sensor at x=0, y=11: closest beacon is at x=2, y=10
//Sensor at x=20, y=14: closest beacon is at x=25, y=17
//Sensor at x=17, y=20: closest beacon is at x=21, y=22
//Sensor at x=16, y=7: closest beacon is at x=15, y=3
//Sensor at x=14, y=3: closest beacon is at x=15, y=3
//Sensor at x=20, y=1: closest beacon is at x=15, y=3

class Day15_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val regex = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
        val sensorsAndBeacons = input
            .map { regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input: $it") }
            .map { it.groupValues.drop(1) }
            .map { it.map { it.toInt() } }
            .map { (xs, ys, xb, yb) -> Point(xs to ys) to Point(xb to yb) }
            .toList()
        val beacons = sensorsAndBeacons
            .map { it.second }
            .filter { it.y == 2000000 }
            .map { it.x }
            .toSet()
        val lines = sensorsAndBeacons
            .mapNotNull { (sensor, beacon) -> toLine(sensor, beacon, 2000000) }
            .toList()

        val minX = lines.minOf { it.from.x }
        val maxX = lines.maxOf { it.to.x }
        println(" " + minX + " " + maxX)

        var sum=0
        for (x in minX..maxX) {
            if (x in beacons) {
                continue
            }

            if (lines.any { it.from.x <= x && x <= it.to.x }) {
                sum++
            }
        }

        return sum
    }

    fun toLine(sensor: Point<Int>, beacon: Point<Int>, y: Int): Line<Int>? {
        val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        val distanceLeft = distance - abs(y - sensor.y)
        if (distanceLeft < 0) {
            return null
        }

        return Line(Point(sensor.x - distanceLeft to y), Point(sensor.x + distanceLeft to y))
    }


}
