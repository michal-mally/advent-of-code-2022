package days.day_15

import util.Solver
import util.line.Line
import util.number.longs.count
import util.point.Point
import kotlin.math.abs

private const val ROW = 2000000L

class Day15_1 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        count {
            val sensorsAndBeacons = sensorsAndBeacons(input).toList()
            val beacons = sensorsAndBeacons
                .map { it.second }
                .filter { it.y == ROW }
                .map { it.x }
                .toSet()
            val lines = sensorsAndBeacons
                .mapNotNull { (sensor, beacon) -> toLine(sensor, beacon, ROW) }
                .toList()

            val minX = lines.minOf { it.from.x }
            val maxX = lines.maxOf { it.to.x }

            for (x in minX..maxX) {
                if (x in beacons) {
                    continue
                }

                if (lines.any { it.from.x <= x && x <= it.to.x }) {
                    inc()
                }
            }
        }

    private fun toLine(sensor: Point<Long>, beacon: Point<Long>, y: Long): Line<Long>? {
        val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        val distanceLeft = distance - abs(y - sensor.y)
        if (distanceLeft < 0) {
            return null
        }

        return Line(Point(sensor.x - distanceLeft to y), Point(sensor.x + distanceLeft to y))
    }


}
