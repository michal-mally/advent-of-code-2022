package days.day_12

import util.Solver
import util.array.TwoDimArray
import util.point.Point

class Day12_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val heightMap = input
            .map { it.toList() }
            .toList()
            .let { TwoDimArray(it) }

        val graph = mutableSetOf<Pair<Point<Int>, Point<Int>>>()

        for (position in heightMap.allPositions()) {
            val height = heightMap.height(position)
            for (neighbour in heightMap.neighbourPositions(position)) {
                if (heightMap.height(neighbour) - height <= 1) {
                    graph += position to neighbour
                }
            }
        }

        val startPoint = heightMap
            .allPositions()
            .first { heightMap[it] == 'S' }

        val distances = mutableMapOf<Point<Int>, Pair<Point<Int>, Int>>().apply { this[startPoint] = startPoint to 0 }
        val remainingNodes = heightMap
            .allPositions()
            .toMutableSet()

        while (remainingNodes.isNotEmpty()) {
            val u = remainingNodes.minBy { distances[it]?.second ?: Int.MAX_VALUE }
            remainingNodes.remove(u)

            for (v in graph.filter { it.first == u }.map { it.second }.filter { it in remainingNodes }) {
                val alt = (distances[u]?.second ?: Int.MAX_VALUE) + 1
                if (alt < (distances[v]?.second ?: Int.MAX_VALUE)) {
                    distances[v] = u to alt
                }
            }
        }

        return distances[heightMap.allPositions().first { heightMap[it] == 'E' }]!!.second
    }

    private fun TwoDimArray<Char>.height(position: Point<Int>) =
        when (this[position]) {
            'S' -> 0
            'E' -> 'z' - 'a'
            else -> this[position] - 'a'
        }

    private fun TwoDimArray<*>.neighbourPositions(position: Point<Int>) =
        sequenceOf(
            position.x - 1 to position.y,
            position.x + 1 to position.y,
            position.x to position.y - 1,
            position.x to position.y + 1,
        )
            .map { Point(it) }
            .filter { it.x in rowIndices && it.y in columnIndices }

}
