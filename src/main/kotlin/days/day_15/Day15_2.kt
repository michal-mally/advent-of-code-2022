package days.day_15

import util.Solver
import util.point.Point
import util.point.longs.manhattanDistanceTo

private const val MAX_XY = 4000000L

class Day15_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val sensorsAndBeacons = sensorsAndBeacons(input)
            .map { (sensor, beacon) -> sensor to (sensor manhattanDistanceTo beacon) }
            .sortedByDescending { (_, distanceFromSensorToBeacon) -> distanceFromSensorToBeacon }
            .toList()

        var x = 0L
        var y = 0L
        while (true) {
            val pointXY = Point(x to y)
            val xToAdd = sensorsAndBeacons
                .asSequence()
                .map { (sensor, distanceToBeacon) ->
                    distanceToBeacon - (sensor manhattanDistanceTo pointXY)
                }
                .firstOrNull { it >= 0 }
                ?: return (x * MAX_XY + y)
            x += xToAdd + 1
            if (x >= MAX_XY) {
                x = 0
                y++
            }
        }
    }

}
