package days.day_18

import util.Solver

data class XYZ<T>(val x: T, val y: T, val z: T)

fun XYZ<Int>.neighbours() =
    sequence {
        yield(XYZ(x - 1, y, z))
        yield(XYZ(x + 1, y, z))
        yield(XYZ(x, y - 1, z))
        yield(XYZ(x, y + 1, z))
        yield(XYZ(x, y, z - 1))
        yield(XYZ(x, y, z + 1))
    }

fun Set<XYZ<Int>>.range(): XYZ<IntRange> {
    check(isNotEmpty()) { "Set is empty" }
    return XYZ(
        x = map { it.x }.let { it.minOrNull()!!..it.maxOrNull()!! },
        y = map { it.y }.let { it.minOrNull()!!..it.maxOrNull()!! },
        z = map { it.z }.let { it.minOrNull()!!..it.maxOrNull()!! },
    )
}

operator fun XYZ<IntRange>.contains(cube: XYZ<Int>) =
    cube.x in x && cube.y in y && cube.z in z

class Day18_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val cubes = input
            .map { toXYZ(it) }
            .toSet()

        val cubesRange = cubes.range()

        val alreadyInside = mutableSetOf<XYZ<Int>>()

        return cubes
            .asSequence()
            .flatMap { it.neighbours() }
            .filter { it !in cubes }
            .count { !inside(cubes, cubesRange, it, alreadyInside) }
    }

    private fun inside(
        cubes: Set<XYZ<Int>>,
        cubesRange: XYZ<IntRange>,
        cube: XYZ<Int>,
        alreadyInside: MutableSet<XYZ<Int>>,
    ): Boolean {
        if (cube in alreadyInside) {
            return true
        }

        val already = mutableSetOf(cube)

        while(true) {
            val toAdd = already
                .asSequence()
                .flatMap { it.neighbours() }
                .filter { it !in already }
                .filter { it !in cubes }
                .toSet()
            if (toAdd.isEmpty()) {
                alreadyInside += already
                return true
            }

            if (toAdd.any { it !in cubesRange })
                return false

            already.addAll(toAdd)
        }
    }

    private fun toXYZ(it: String) =
        it
            .split(",")
            .map { it.toInt() }
            .let { (x, y, z) -> XYZ(x, y, z) }

}
