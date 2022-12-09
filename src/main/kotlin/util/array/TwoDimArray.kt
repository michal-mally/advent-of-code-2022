package util.array

class TwoDimArray<T>(private val values: List<List<T>>) {
    init {
        require(values.isNotEmpty())
        require(values.all { it.size == values[0].size })
    }

    val rowCount = values.size
    val columnCount = values.first().size
    val rowIndices = values.indices.asSequence()
    val columnIndices = values.first().indices.asSequence()

    operator fun get(position: Pair<Int, Int>) = values[position.first][position.second]

    fun allPositions(): Sequence<Pair<Int, Int>> =
        rowIndices.flatMap { row -> columnIndices.map { row to it } }

}
