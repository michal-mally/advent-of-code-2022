package days.day_08

import util.Solver
import util.point.Point
import util.sequence.reversed

class Day08_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(forest(input)) {
            allDirections()
                .fold(emptySet<Point<Int>>()) { visibleTrees, direction ->
                    visibleTrees + visibleTreesInDirection(direction)
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
                    this += position
                }
            }
        }

    private fun Forest.allDirections(): Sequence<Direction> =
        sequenceOf(
            xIndices.map { x -> yIndices.map { Point(x to it) } },
            yIndices.map { y -> xIndices.map { Point(it to y) } }
        )
            .flatMap { it }
            .flatMap { sequenceOf(it, it.reversed()) }
            .map(::Direction)

}
