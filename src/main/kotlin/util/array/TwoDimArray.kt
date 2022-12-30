package util.array

import util.point.Point
import util.point.neighbours

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
        xIndices.flatMap { x -> yIndices.map { Point(x to it) } }

    override fun toString() =
        values.joinToString("\n") { row -> row.toString() }

    operator fun contains(point: Point<Int>) =
        point.x in xIndices && point.y in yIndices

}

fun TwoDimArray<*>.neighbourPositions(position: Point<Int>) =
    position
        .neighbours()
        .filter { it.x in xIndices && it.y in yIndices }
