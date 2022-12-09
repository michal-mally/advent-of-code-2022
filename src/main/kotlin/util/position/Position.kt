package util.position

@JvmInline
value class Position<T>(private val position: Pair<T, T>) {
    val row
        get() = position.first
    val column
        get() = position.second
}
