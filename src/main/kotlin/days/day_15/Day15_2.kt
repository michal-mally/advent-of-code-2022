package days.day_15

import util.Solver
import util.point.Point

class Day15_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val sensorsAndBeacons = sensorsAndBeacons(input)
            .map { (sensor, beacon) -> sensor to sensor.manhattanDistanceTo(beacon) }
            .sortedByDescending { (_, distanceFromSensorToBeacon) -> distanceFromSensorToBeacon }
            .toList()

        var x = 0
        var y = 0
        while (true) {
            val pointXY = Point(x to y)
            val xToAdd = sensorsAndBeacons
                .asSequence()
                .map { (sensor, distanceToBeacon) ->
                    distanceToBeacon - sensor.manhattanDistanceTo(pointXY)
                }
                .firstOrNull { it >= 0 }
                ?: return (x * 4000000L + y)
            x += xToAdd + 1
            if (x >= 4000000) {
                x = 0
                y++
            }
        }
    }

}
