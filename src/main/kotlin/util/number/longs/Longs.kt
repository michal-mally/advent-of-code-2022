package util.number.longs

fun count(start: Long = 0, block: LongHolder.() -> Unit) =
    LongHolder(start).apply(block).value

class LongHolder(var value: Long = 0) {
    operator fun inc(): LongHolder {
        value++
        return this
    }
}

infix fun Long.nonNegativeModulo(operand: Int): Int =
    ((this % operand + operand) % operand).toInt()
