package days.day_22

import util.Solver
import util.point.Point
import util.point.plus
import util.point.times
import util.point.toList
import util.sequence.splitBy

private const val SIDE_SIZE = 50

class Day22_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val (map, instructionsRaw) = input
            .splitBy { it.isBlank() }
            .toList()

        return Game(sides(map, SIDE_SIZE))
            .simulate(instructions(instructionsRaw))
            .let(::score)
    }

    private fun score(locationAndDirection: LocationAndDirection) =
        locationAndDirection
            .universalLocation
            .plus(Point(1 to 1))
            .times(Point(4 to 1_000))
            .toList()
            .sum()
            .plus(locationAndDirection.direction.ordinal)

}
