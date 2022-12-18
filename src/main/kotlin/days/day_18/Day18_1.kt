package days.day_18

import util.Solver

class Day18_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val cubes = input
            .map { toXYZ(it) }
            .toSet()

        return cubes
            .asSequence()
            .flatMap { it.neighbours() }
            .count { it !in cubes }
    }

    private fun toXYZ(it: String) =
        it
            .split(",")
            .map { it.toInt() }
            .let { (x, y, z) -> XYZ(x, y, z) }

}
