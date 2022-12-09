package days.day_08

import util.Solver
import util.array.TwoDimArray

class Day08_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val forest = input
            .map { it.toList() }
            .map { it.map { c -> c.toString().toInt() } }
            .toList()
            .let(::TwoDimArray)

        val visibleTrees = mutableSetOf<Pair<Int, Int>>()
        for (direction in forest.allDirections()) {
            forest.visibleTreesInDirection(direction, visibleTrees)
        }

        return visibleTrees.size
    }

    private fun TwoDimArray<Int>.visibleTreesInDirection(
        direction: List<Pair<Int, Int>>,
        visibleTrees: MutableSet<Pair<Int, Int>>,
    ) {
        var tallestTree = -1
        for (position in direction) {
            val tree = this@TwoDimArray[position]
            if (tree > tallestTree) {
                tallestTree = tree
                visibleTrees += position
            }
        }
    }

    private fun TwoDimArray<Int>.allDirections() =
        sequenceOf(
            rowIndices.map { row -> columnIndices.map { row to it } },
            rowIndices.map { row -> columnIndices.reversed().map { row to it } },
            columnIndices.map { col -> rowIndices.map { it to col } },
            columnIndices.map { col -> rowIndices.reversed().map { it to col } },
        )
            .flatten()
}
