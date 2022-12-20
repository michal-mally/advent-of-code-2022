package util.number

fun count(start: Int = 0, block: IntHolder.() -> Unit) =
    IntHolder(start).apply(block).value

class IntHolder(var value: Int = 0) {
    operator fun inc(): IntHolder {
        value++
        return this
    }
}

infix fun Long.nonNegativeModulo(operand: Int): Int =
    ((this % operand + operand) % operand).toInt()
