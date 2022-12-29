package util.point

@JvmInline
value class Point<T>(private val position: Pair<T, T>) {
    val x
        get() = position.first

    val y
        get() = position.second

    operator fun component1() = x

    operator fun component2() = y
}
