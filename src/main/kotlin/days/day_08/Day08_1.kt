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
        listOf(
            forest
                .rowIndices
                .map { row -> forest.columnIndices.map { row to it } },
            forest
                .rowIndices
                .map { row -> forest.columnIndices.reversed().map { row to it } },
            forest
                .columnIndices
                .map { col -> forest.rowIndices.map { it to col } },
            forest
                .columnIndices
                .map { col -> forest.rowIndices.reversed().map { it to col } },
        )
            .flatten()
            .map {
                var tallestTree = -1
                for (position in it) {
                    val tree = forest[position]
                    if (tree > tallestTree) {
                        tallestTree = tree
                        visibleTrees += position
                    }
                }
            }
        return visibleTrees.size
    }
}
