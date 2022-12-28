package days.day_12

import util.array.TwoDimArray
import util.graph.Edge
import util.graph.Graph
import util.point.Point

fun heightMap(input: Sequence<String>) =
    input
        .map { it.toList() }
        .toList()
        .let { TwoDimArray(it) }

fun graph(heightMap: TwoDimArray<Char>) =
    Graph(heightMap.edges())

fun TwoDimArray<Char>.start() =
    allPositions().first { this[it] == 'S' }

fun TwoDimArray<Char>.end() =
    allPositions().first { this[it] == 'E' }

fun TwoDimArray<Char>.height(position: Point<Int>) =
    when (this[position]) {
        'S' -> 0
        'E' -> 'z' - 'a'
        else -> this[position] - 'a'
    }

private fun TwoDimArray<Char>.edges() =
    allPositions()
        .map { it to neighbourPositions(it) }
        .flatMap { (vertex, neighbours) -> neighbours.map { neighbour -> vertex to neighbour } }
        .map { Edge(it) }
        .filter { edge -> height(edge.from) - height(edge.to) <= 1 }
        .toSet()

private fun TwoDimArray<*>.neighbourPositions(position: Point<Int>) =
    sequenceOf(
        position.x - 1 to position.y,
        position.x + 1 to position.y,
        position.x to position.y - 1,
        position.x to position.y + 1,
    )
        .map { Point(it) }
        .filter { it.x in xIndices && it.y in yIndices }
