package days.day_12

import util.Solver

class Day12_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(heightMap(input)) {
            val elevationZeroPoints = allPositions().filter { height(it) == 0 }
            graph(this)
                .distancesFrom(end())
                .filter { it.key in elevationZeroPoints }
                .values
                .min()
        }

}
