package util.graph

import kotlin.math.min

class Graph<V>(
    private val edges: Map<Edge<V>, Int>
) {

    private val vertices = edges
        .keys
        .asSequence()
        .flatMap { sequenceOf(it.from, it.to) }
        .toList()

    fun distancesFrom(start: V): Map<V, Int?> {
        val distances = mutableMapOf<V, Int?>().apply { this[start] = 0 }

        val remainingNodes = vertices.toMutableSet()

        while (remainingNodes.isNotEmpty()) {
            val u = remainingNodes.minBy { distances[it] ?: Int.MAX_VALUE }
            remainingNodes.remove(u)

            val edgesToRemainingNodes = edges.filter { (edge, _) -> edge.from == u && edge.to in remainingNodes }
            for (edge in edgesToRemainingNodes) {
                val fromStartToU = distances[u] ?: continue

                distances[edge.key.to] = min(fromStartToU + edge.value, distances[edge.key.to] ?: Int.MAX_VALUE)
            }
        }

        return distances
    }

}
