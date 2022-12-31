package days.day_22

import util.Solver
import util.point.Point
import util.point.ints.plus
import util.point.ints.times
import util.point.ints.toList
import util.sequence.splitBy

private const val SIDE_SIZE = 50

class Day22_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .splitBy { it.isBlank() }
            .toList()
            .let { (map, instructionsRaw) -> sides(map, SIDE_SIZE) to instructions(instructionsRaw) }
            .let { (sides, instructions) -> Game(sides).simulate(instructions) }
            .score()

    private fun LocationAndDirection.score() =
        universalLocation
            .plus(Point(1 to 1))
            .times(Point(4 to 1_000))
            .toList()
            .sum()
            .plus(direction.ordinal)

}
