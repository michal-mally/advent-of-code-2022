package util.array

import util.point.Point

class TwoDimArray<T>(private val values: List<List<T>>) {
    init {
        require(values.isNotEmpty())
        require(values.all { it.size == values[0].size })
    }

    val rowCount = values.size
    val columnCount = values.first().size
    val rowIndices = values.indices.asSequence()
    val columnIndices = values.first().indices.asSequence()

    operator fun get(position: Point<Int>) = values[position.x][position.y]

    fun allPositions(): Sequence<Point<Int>> =
        rowIndices.flatMap { row -> columnIndices.map { Point(row to it) } }

}
