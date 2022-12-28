package util.point

@JvmInline
value class Point<T>(private val position: Pair<T, T>) {
    val x
        get() = position.first
    val y
        get() = position.second
}
