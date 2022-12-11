@file:Suppress("PrivatePropertyName")

package days.day_11

import util.biginteger.isDivisibleBy
import util.sequence.splitBy
import java.math.BigInteger
import java.math.BigInteger.ONE

private const val STARTING_ITEM_WORRY_LEVELS = "startingItemWorryLevels"
private const val LEFT_OPERAND = "leftOperand"
private const val OPERATOR = "operator"
private const val RIGHT_OPERAND = "rightOperand"
private const val TEST_DIVISIBLE_BY = "testDivisibleBy"
private val TO_MONKEY = mapOf(
    true to "ifTrueThrowToMonkey",
    false to "ifFalseThrowToMonkey",
)

private val MONKEY_REGEX = """
    Monkey (\d+):
      Starting items: (?<$STARTING_ITEM_WORRY_LEVELS>\d+(, \d+)*)
      Operation: new = (?<$LEFT_OPERAND>old|\d+) (?<$OPERATOR>[*\+]) (?<$RIGHT_OPERAND>old|\d+)
      Test: divisible by (?<$TEST_DIVISIBLE_BY>\d+)
        If true: throw to monkey (?<${TO_MONKEY[true]}>\d+)
        If false: throw to monkey (?<${TO_MONKEY[false]}>\d+)
"""
    .trimIndent()
    .toRegex()

private data class Monkey(
    private val itemsWorryLevels: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testDivisibleBy: BigInteger,
    val toMonkey: Map<Boolean, Int>,
    val divisionAfterRound: BigInteger,
) {

    context(Monkeys) fun play(): Int {
        fun targetMonkey(newWorryLevel: BigInteger) =
            monkeys[toMonkey[newWorryLevel isDivisibleBy testDivisibleBy]!!]

        val worryLevelsCount = itemsWorryLevels.size
        while (itemsWorryLevels.isNotEmpty()) {
            val oldWorryLevel = itemsWorryLevels.removeLast()
            val newWorryLevel = operation(oldWorryLevel) / divisionAfterRound
            targetMonkey(newWorryLevel).acceptItem(newWorryLevel % numberSpace)
        }

        return worryLevelsCount
    }

    fun acceptItem(worryLevel: BigInteger) {
        itemsWorryLevels += worryLevel
    }

}

private fun Monkey(representation: String, divisionAfterRound: BigInteger): Monkey {
    val groups = MONKEY_REGEX
        .matchEntire(representation)
        ?.groups
        ?: throw IllegalArgumentException("Invalid monkey representation: $representation")

    fun groupValue(name: String) = groups[name]!!.value

    val startingItems = groupValue(STARTING_ITEM_WORRY_LEVELS)
        .split(", ")
        .map(String::toBigInteger)
        .toMutableList()
    val operation = { old: BigInteger ->
        fun toOperand(operandGroup: String) =
            groupValue(operandGroup).toBigIntegerOrNull() ?: old

        val operator = when (groupValue(OPERATOR)) {
            "*" -> BigInteger::times
            "+" -> BigInteger::plus
            else -> throw IllegalArgumentException("Invalid operator: ${groupValue(OPERATOR)}")
        }

        operator(toOperand(LEFT_OPERAND), toOperand(RIGHT_OPERAND))
    }
    val testDivisibleBy = groupValue(TEST_DIVISIBLE_BY).toBigInteger()
    val toMonkey = TO_MONKEY.mapValues { (_, value) -> groupValue(value).toInt() }

    return Monkey(
        startingItems,
        operation,
        testDivisibleBy,
        toMonkey,
        divisionAfterRound
    )
}

private class Monkeys(val monkeys: List<Monkey>) {
    val numberSpace = monkeys
        .map(Monkey::testDivisibleBy)
        .toSet()
        .reduce(BigInteger::multiply)
}

private fun monkeys(input: Sequence<String>, divisionAfterRound: BigInteger) =
    input
        .splitBy { it == "" }
        .map { it.joinToString("\n") }
        .map { Monkey(it, divisionAfterRound) }
        .toList()
        .let(::Monkeys)

fun play(input: Sequence<String>, rounds: Int, divisionAfterRound: BigInteger = ONE) =
    with(monkeys(input, divisionAfterRound)) {
        val inspections = MutableList(monkeys.size) { 0L }
        repeat(rounds) {
            monkeys.forEachIndexed { i, monkey ->
                inspections[i] += monkey.play().toLong()
            }
        }

        inspections
            .sortedDescending()
            .take(2)
            .reduce(Long::times)
    }
