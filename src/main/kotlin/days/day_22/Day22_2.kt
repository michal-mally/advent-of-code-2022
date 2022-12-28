package days.day_22

import days.day_22.Day22_2.Square.Empty
import days.day_22.Day22_2.Square.Wall
import util.Solver
import util.array.TwoDimArray
import util.sequence.splitBy
import util.sequence.transpose

class Day22_2 : Solver<Sequence<String>, Int> {

    private val sideSize = 4
    override fun solve(input: Sequence<String>): Int {
        val (map, instructionsRaw) = input
            .splitBy { it.isBlank() }
            .toList()

        val sides = map
            .map { it.map { parseSquare(it) }.asSequence() }
            .chunked(sideSize)
            .flatMapIndexed { y, rows -> parseSides(y, rows)}
            .toList()

        TODO()
    }

    private fun parseSides(y: Int, rows: List<Sequence<Square?>>): Sequence<Side> {
        return rows
            .asSequence()
            .map { it.chunked(sideSize) }
            .transpose()
            .mapIndexedNotNull { x, side -> parseSide(x, y, side) }
    }

    private fun parseSide(x: Int, y: Int, side: Sequence<List<Square?>>): Side? {
        println("Side $x $y")
        side
            .forEach { println(it) }
        println()

        return side
            .takeIf { null !in it.flatMap { it } }
            ?.map { it.map { it!! } }
            ?.toList()
            ?.let { TwoDimArray(it) }
            ?.let { Side(it) }
    }
    private class Side(val values: TwoDimArray<Square>)

    private fun parseSquare(square: Char) =
        when (square) {
            ' ' -> null
            '#' -> Wall
            '.' -> Empty
            else -> throw IllegalArgumentException("Invalid square: $square")
        }

    private enum class Square {
        Wall,
        Empty
    }

}
