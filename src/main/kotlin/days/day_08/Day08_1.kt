package days.day_08

import util.Solver
import util.array.TwoDimArray

typealias Forest = TwoDimArray<Int>
typealias Position = Pair<Int, Int>

class Day08_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val forest = forest(input)

        return forest
            .allDirections()
            .fold(emptySet<Position>()) { visibleTrees, direction ->
                visibleTrees + forest.visibleTreesInDirection(direction)
            }
            .size
    }

    private fun forest(input: Sequence<String>) =
        input
            .map(String::toList)
            .map { it.map { c -> c.toString().toInt() } }
            .toList()
            .let(::Forest)

    private fun Forest.visibleTreesInDirection(direction: List<Position>) =
        buildSet {
            var tallestTree = -1
            for (position in direction) {
                val tree = this@Forest[position]
                if (tree > tallestTree) {
                    tallestTree = tree
                    add(position)
                }
            }
        }

    private fun Forest.allDirections() =
        sequenceOf(
            rowIndices.map { row -> columnIndices.map { row to it } },
            rowIndices.map { row -> columnIndices.reversed().map { row to it } },
            columnIndices.map { col -> rowIndices.map { it to col } },
            columnIndices.map { col -> rowIndices.reversed().map { it to col } },
        )
            .flatten()

}
