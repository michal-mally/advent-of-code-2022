package days.day_22

import days.day_22.Instruction.Forward
import days.day_22.Instruction.Rotate

private val instructionRegex = """\d+|[LR]""".toRegex()

sealed interface Instruction {
    data class Rotate(val clockwiseRotation: Int) : Instruction
    data class Forward(val steps: Int) : Instruction
}

fun instructions(instructionsRaw: Sequence<String>): List<Instruction> =
    instructionRegex
        .findAll(instructionsRaw.first())
        .map { it.value }
        .map { Instruction(it) }
        .toList()

private fun Instruction(instruction: String) =
    when (instruction) {
        "L" -> Rotate(-STRAIGHT_ANGLE_DEGREES)
        "R" -> Rotate(STRAIGHT_ANGLE_DEGREES)
        else -> Forward(instruction.toInt())
    }
