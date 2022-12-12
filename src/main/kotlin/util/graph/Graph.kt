package util.graph

import kotlin.math.min

class Graph<V>(edges: Set<Edge<V>>) {

    private val edgesByFrom = edges.groupBy { edge -> edge.from }

    private val vertices = edges.flatMap { sequenceOf(it.from, it.to) }

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

            (edgesByFrom[intermediate] ?: error("No edges for $intermediate"))
                .filter { edge -> edge.to in remainingNodes }
                .forEach { edge ->
                    distances[edge.to] = min(
                        distanceToIntermediate + edge.weight,
                        distances[edge.to] ?: Int.MAX_VALUE,
                    )
                }
        }

        return distances
    }

}
