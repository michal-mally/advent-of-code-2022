package util.graph

data class Edge<T>(private val edge: Pair<T, T>, val weight: Int = 1) {
    val from: T
        get() = edge.first
    val to: T
        get() = edge.second
}
