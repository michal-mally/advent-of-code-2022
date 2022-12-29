package days.day_22

import util.array.TwoDimArray
import util.point.Point
import util.sequence.transpose

private val instructionRegex = """\d+|[LR]""".toRegex()

fun instructions(instructionsRaw: Sequence<String>): List<Instruction> =
    instructionRegex
        .findAll(instructionsRaw.first())
        .map { it.value }
        .map { Instruction(it) }
        .toList()

fun sides(map: Sequence<String>, sideSize: Int) =
    map
        .map { it.map(::Square) }
        .map { it.asSequence() }
        .chunked(sideSize)
        .flatMapIndexed { y, rows -> sides(sideSize, y, rows) }
        .toList()

private fun sides(sideSize: Int, y: Int, rows: List<Sequence<Square?>>) =
    rows
        .asSequence()
        .map { it.chunked(sideSize) }
        .transpose()
        .mapIndexedNotNull { x, side -> Side(Point(x to y), side) }

private fun Side(location: Point<Int>, side: Sequence<List<Square?>>) =
    side
        .takeIf { null !in it.flatMap { it } }
        ?.map { it.map { it!! }.asSequence() }
        ?.transpose()
        ?.map { it.toList() }
        ?.toList()
        ?.let { TwoDimArray(it) }
        ?.let { Side(location, it) }

private fun Square(square: Char) =
    when (square) {
        ' ' -> null
        '#' -> Square.Wall
        '.' -> Square.Empty
        else -> throw IllegalArgumentException("Invalid square: $square")
    }

private fun Instruction(instruction: String) =
    when (instruction) {
        "L" -> Instruction.Rotate(-STRAIGHT_ANGLE_DEGREES)
        "R" -> Instruction.Rotate(STRAIGHT_ANGLE_DEGREES)
        else -> Instruction.Forward(instruction.toInt())
    }
