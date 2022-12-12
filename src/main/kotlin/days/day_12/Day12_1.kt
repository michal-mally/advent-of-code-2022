package days.day_12

import util.Solver

class Day12_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(heightMap(input)) {
            graph(this)
                .distancesFrom(end())[start()]
                ?: error("No path found")
        }

}
