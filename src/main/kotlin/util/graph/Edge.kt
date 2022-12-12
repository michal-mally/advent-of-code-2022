package util.graph

@JvmInline
value class Edge<T>(private val edge: Pair<T, T>) {
    val from: T
        get() = edge.first
    val to: T
        get() = edge.second
}
