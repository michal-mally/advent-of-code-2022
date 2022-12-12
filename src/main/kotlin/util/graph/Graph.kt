package util.graph

import kotlin.math.min

class Graph<V>(edgesWithWeights: Map<Edge<V>, Int>) {

    private val edges = edgesWithWeights
        .entries
        .groupBy { (edge, _) -> edge.from }
        .mapValues { (_, edges) -> edges.associate { (edge, weight) -> edge.to to weight } }

    private val vertices = edgesWithWeights
        .keys
        .asSequence()
        .flatMap { sequenceOf(it.from, it.to) }
        .toList()

    fun distancesFrom(start: V): Map<V, Int> {
        val distances = mutableMapOf(start to 0)

        val remainingNodes = vertices.toMutableSet()
        while (remainingNodes.isNotEmpty()) {
            val (intermediate, distanceToIntermediate) = distances
                .asSequence()
                .filter { it.key in remainingNodes }
                .minByOrNull { it.value }
                ?.also { remainingNodes.remove(it.key) }
                ?: break

            (edges[intermediate] ?: error("No edges for $intermediate"))
                .filter { (to, _) -> to in remainingNodes }
                .forEach { (to, weight) ->
                    distances[to] = min(distanceToIntermediate + weight, distances[to] ?: Int.MAX_VALUE)
                }
        }

        return distances
    }

}
