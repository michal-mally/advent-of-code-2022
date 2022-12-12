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

            edges
                .asSequence()
                .filter { (edge, _) -> edge.from == intermediate }
                .filter { (edge, _) -> edge.to in remainingNodes }
                .forEach { (edge, weight) ->
                    distances[edge.to] = min(distanceToIntermediate + weight, distances[edge.to] ?: Int.MAX_VALUE)
                }
        }

        return distances
    }

}
