package days.day_08

import util.Solver
import util.position.Position

class Day08_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val forest = forest(input)

        return forest
            .allPositions()
            .filter { it.row in (1..<forest.rowCount - 1) && it.column in (1..<forest.columnCount - 1) }
            .maxOf { forest.scenicViewOf(it) }
    }

    private fun forest(input: Sequence<String>) =
        input
            .map { it.toList() }
            .map { it.map { c -> c.toString().toInt() } }
            .toList()
            .let(::Forest)

    private fun Forest.scenicViewOf(position: Position<Int>): Int {
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

    private fun Forest.allDirections(position: Position<Int>) =
        sequenceOf(
            (position.column - 1 downTo 0).asSequence().map { Position(position.row to it) },
            (position.column + 1..<columnCount).asSequence().map { Position(position.row to it) },
            (position.row - 1 downTo 0).asSequence().map { Position(it to position.column) },
            (position.row + 1..<rowCount).asSequence().map { Position(it to position.column) },
        )

    private val <L, R> Pair<L, R>.row
        get() = first
    private val <L, R> Pair<L, R>.column
        get() = second
}
