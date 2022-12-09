package days.day_08

import util.Solver
import util.array.TwoDimArray

class Day08_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val forest = input
            .map { it.toList() }
            .map { it.map { c -> c.toString().toInt() } }
            .toList()
            .let(::TwoDimArray)

        return forest
            .allPositions()
            .filter { it.first in (1..<forest.rowCount - 1) && it.second in (1..<forest.columnCount - 1) }
            .map { position ->
                println(position)
                listOf(
                    forest.columnIndices.filter { it < position.second }.reversed().map { position.first to it },
                    forest.columnIndices.filter { it > position.second }.map { position.first to it },
                    forest.rowIndices.filter { it < position.first }.reversed().map { it to position.second },
                    forest.rowIndices.filter { it > position.first }.map { it to position.second },
                )
                    .onEach { println(it) }
                    .map {
                        var visibleTrees = 0
                        for (p in it) {
                            visibleTrees++
                            if (forest[p] >= forest[position]) {
                                break
                            }
                        }
                        println(visibleTrees)
                        visibleTrees
                    }
                    .reduce(Int::times)
                    .also { println(it) }
            }
            .max()
    }
}
