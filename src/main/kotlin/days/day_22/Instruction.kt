package days.day_22

sealed interface Instruction {
    data class Rotate(val clockwiseRotation: Int) : Instruction
    data class Forward(val steps: Int) : Instruction
}
