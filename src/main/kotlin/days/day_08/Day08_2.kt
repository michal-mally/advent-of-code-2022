package days.day_08

import util.Solver
import util.array.TwoDimArray

class Day08_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        input
            .map { it.toList() }
            .map { it.map { c -> c.toString().toInt() } }
            .toList()
            .let(::TwoDimArray)
            .let { forest ->
                with(forest) {
                    allPositions()
                        .filter { it.row in (1..<forest.rowCount - 1) && it.column in (1..<forest.columnCount - 1) }
                        .maxOf { scenicViewOf(it) }
                }
            }

    context(TwoDimArray<Int>) private fun scenicViewOf(position: Pair<Int, Int>) =
        listOf(
            columnIndices.filter { it < position.column }.reversed().map { position.row to it },
            columnIndices.filter { it > position.column }.map { position.row to it },
            rowIndices.filter { it < position.row }.reversed().map { it to position.column },
            rowIndices.filter { it > position.row }.map { it to position.column },
        )
            .map {
                var visibleTrees = 0
                for (p in it) {
                    visibleTrees++
                    if (this@TwoDimArray[p] >= this@TwoDimArray[position]) {
                        break
                    }
                }
                visibleTrees
            }
            .reduce(Int::times)

    private val <L, R> Pair<L, R>.row
        get() = first
    private val <L, R> Pair<L, R>.column
        get() = second
}
