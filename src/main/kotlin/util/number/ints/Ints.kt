package util.number.ints

fun Int.isEven() =
    this % 2 == 0

fun count(start: Int = 0, block: IntHolder.() -> Unit) =
    IntHolder(start).apply(block).value

class IntHolder(var value: Int = 0) {
    operator fun inc(): IntHolder {
        value++
        return this
    }
}

infix fun Int.nonNegativeModulo(operand: Int): Int =
    (this % operand + operand) % operand
