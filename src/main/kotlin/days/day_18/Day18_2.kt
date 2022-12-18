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

fun Set<XYZ<Int>>.extent(): XYZ<IntRange> {
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
            .map(::toXYZ)
            .toSet()
        val cubesExtent = cubes.extent()
        val knownToBeAlreadyInside = mutableSetOf<XYZ<Int>>()

        return cubes
            .asSequence()
            .flatMap { it.neighbours() }
            .filter { it !in cubes }
            .count { cube -> !inside(cubes, cubesExtent, knownToBeAlreadyInside, cube) }
    }

    private fun inside(
        cubes: Set<XYZ<Int>>,
        cubesRange: XYZ<IntRange>,
        knownToBeAlreadyInside: MutableSet<XYZ<Int>>,
        cube: XYZ<Int>,
    ): Boolean {
        check(cube !in cubes) { "Cube $cube is part of cubes!" }
        if (cube in knownToBeAlreadyInside) return true

        val alreadyConsidered = mutableSetOf(cube)

        while(true) {
            val newlyDiscoveredEmptyNeighbours = alreadyConsidered
                .asSequence()
                .flatMap { it.neighbours() }
                .filter { it !in alreadyConsidered }
                .filter { it !in cubes }
                .toSet()
            if (newlyDiscoveredEmptyNeighbours.isEmpty()) {
                knownToBeAlreadyInside += alreadyConsidered
                return true
            }

            if (newlyDiscoveredEmptyNeighbours.any { it !in cubesRange }) return false

            alreadyConsidered += newlyDiscoveredEmptyNeighbours
        }
    }

    private fun toXYZ(it: String) =
        it
            .split(",")
            .map { it.toInt() }
            .let { (x, y, z) -> XYZ(x, y, z) }

}
