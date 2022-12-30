package days.day_08

import util.Solver
import util.point.Point

class Day08_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        with(forest(input)) {
            allPositions()
                .filter { it.x in (1..<yCount - 1) && it.y in (1..<xCount - 1) }
                .maxOf { scenicViewOf(it) }
        }

    private fun Forest.scenicViewOf(position: Point<Int>): Int {
        val treeHeight = this@Forest[position]
        return allDirections(position)
            .map { direction -> direction.map { this@Forest[it] } }
            .map { visibleTreesInDirection(treeHeight, it) }
            .reduce(Int::times)
    }

    private fun visibleTreesInDirection(treeHeight: Int, otherTreeHeights: Sequence<Int>): Int {
        var visibleTrees = 0
        for (otherTreeHeight in otherTreeHeights) {
            visibleTrees++

            if (otherTreeHeight >= treeHeight) {
                break
            }
        }

        return visibleTrees
    }

    private fun Forest.allDirections(position: Point<Int>) =
        sequenceOf(
            (position.y - 1 downTo 0).asSequence().map { Point(position.x to it) },
            (position.y + 1..<xCount).asSequence().map { Point(position.x to it) },
            (position.x - 1 downTo 0).asSequence().map { Point(it to position.y) },
            (position.x + 1..<yCount).asSequence().map { Point(it to position.y) },
        )

}
