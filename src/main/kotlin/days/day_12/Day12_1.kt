package days.day_12

import util.Solver
import util.array.TwoDimArray
import util.graph.Edge
import util.graph.Graph
import util.point.Point

class Day12_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val heightMap = input
            .map { it.toList() }
            .toList()
            .let { TwoDimArray(it) }

        val startPoint = heightMap
            .allPositions()
            .first { heightMap[it] == 'S' }

        return Graph(
            heightMap
                .allPositions()
                .toSet(),
            heightMap
                .allPositions()
                .map { it to heightMap.neighbourPositions(it) }
                .flatMap { (vertex, neighbours) -> neighbours.map { neighbour -> vertex to neighbour } }
                .map { Edge(it) }
                .filter { edge -> heightMap.height(edge.to) - heightMap.height(edge.from) <= 1 }
                .associateWith { 1 }
        )
            .distancesFrom(startPoint)[heightMap.allPositions().first { heightMap[it] == 'E' }]!!
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
