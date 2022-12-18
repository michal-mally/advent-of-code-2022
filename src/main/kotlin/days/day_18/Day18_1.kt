package days.day_18

import util.Solver

data class XYZ(val x: Int, val y: Int, val z: Int) {

    fun neighbours(): Sequence<XYZ> = sequence {
        yield(XYZ(x - 1, y, z))
        yield(XYZ(x + 1, y, z))
        yield(XYZ(x, y - 1, z))
        yield(XYZ(x, y + 1, z))
        yield(XYZ(x, y, z - 1))
        yield(XYZ(x, y, z + 1))
    }

}

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
