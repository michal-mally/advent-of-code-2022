package util.array

import util.point.Point

data class TwoDimArray<T>(private val values: List<List<T>>) {
    init {
        require(values.isNotEmpty()) { "Array must not be empty" }
        require(values.all { it.size == values[0].size }) { "All rows must have the same length" }
    }

    val yCount = values.size
    val xCount = values.first().size
    val xIndices = values.indices.asSequence()
    val yIndices = values.first().indices.asSequence()

    operator fun get(position: Point<Int>) = values[position.x][position.y]

    fun allPositions(): Sequence<Point<Int>> =
        xIndices.flatMap { row -> yIndices.map { Point(row to it) } }

}
