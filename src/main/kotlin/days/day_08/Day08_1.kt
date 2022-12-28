package days.day_08

import util.Solver
import util.point.Point

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

    private fun Forest.visibleTreesInDirection(direction: Direction) =
        buildSet {
            var tallestTree = -1
            for (position in direction.points) {
                val tree = this@Forest[position]
                if (tree > tallestTree) {
                    tallestTree = tree
                    add(position)
                }
            }
        }

    private fun Forest.allDirections(): Sequence<Direction> =
        sequenceOf(
            xIndices.map { row -> yIndices.map { Point(row to it) } },
            yIndices.map { column -> xIndices.map { Point(it to column) } }
        )
            .reduce { acc, sequence -> acc + sequence }
            .flatMap { sequenceOf(it, it.toList().reversed().asSequence()) }
            .map { Direction(it) }

}
