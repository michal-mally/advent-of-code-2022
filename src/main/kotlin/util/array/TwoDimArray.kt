package util.array

import util.position.Position

class TwoDimArray<T>(private val values: List<List<T>>) {
    init {
        require(values.isNotEmpty())
        require(values.all { it.size == values[0].size })
    }

    val rowCount = values.size
    val columnCount = values.first().size
    val rowIndices = values.indices.asSequence()
    val columnIndices = values.first().indices.asSequence()

    operator fun get(position: Position<Int>) = values[position.row][position.column]

    fun allPositions(): Sequence<Position<Int>> =
        rowIndices.flatMap { row -> columnIndices.map { Position(row to it) } }

}
