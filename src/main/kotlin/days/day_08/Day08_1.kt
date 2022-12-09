package days.day_08

import util.Solver
import util.array.TwoDimArray
import util.point.Point

typealias Forest = TwoDimArray<Int>
typealias Direction = Sequence<Point<Int>>

class Day08_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val forest = forest(input)

        return forest
            .allDirections()
            .fold(emptySet<Point<Int>>()) { visibleTrees, direction ->
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

    private fun Forest.visibleTreesInDirection(direction: Direction) =
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

    private fun Forest.allDirections(): Sequence<Direction> =
        sequenceOf(
            rowIndices.map { row -> columnIndices.map { Point(row to it) } },
            columnIndices.map { column -> rowIndices.map { Point(it to column) } }
        )
            .reduce { acc, sequence -> acc + sequence }
            .flatMap { sequenceOf(it, it.toList().reversed().asSequence()) }

}
