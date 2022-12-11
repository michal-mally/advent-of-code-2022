package days.day_11

import util.sequence.splitBy
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

private const val STARTING_WORRY_LEVELS = "startingWorryLevels"
private const val LEFT_OPERAND = "leftOperand"
private const val OPERATOR = "operator"
private const val RIGHT_OPERAND = "rightOperand"
private const val TEST_DIVISIBLE_BY = "testDivisibleBy"
private val TO_MONKEY = mapOf(
    true to "ifTrueThrowToMonkey",
    false to "ifFalseThrowToMonkey",
)

private val MONKEY_REGEX = """
Monkey \d+:
  Starting items: (?<$STARTING_WORRY_LEVELS>\d+(, \d+)*)
  Operation: new = (?<$LEFT_OPERAND>old|\d+) (?<$OPERATOR>[*\+]) (?<$RIGHT_OPERAND>old|\d+)
  Test: divisible by (?<$TEST_DIVISIBLE_BY>\d+)
    If true: throw to monkey (?<${TO_MONKEY[true]}>\d+)
    If false: throw to monkey (?<${TO_MONKEY[false]}>\d+)
"""
    .trim()
    .toRegex()

private data class Monkey(
    val worryLevels: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testDivisibleBy: BigInteger,
    val toMonkey: Map<Boolean, Int>,
    val divisionAfterRound: BigInteger,
) {

    context(Monkeys) fun play(): Int {
        val worryLevelsCount = worryLevels.size
        while (worryLevels.isNotEmpty()) {
            val oldWorryLevel = worryLevels.removeLast()
            val newWorryLevel = operation(oldWorryLevel) / divisionAfterRound
            monkeys[toMonkey[newWorryLevel.mod(testDivisibleBy) == ZERO]!!]
                .worryLevels
                .add(newWorryLevel.mod(numberSpace))
        }

        return worryLevelsCount
    }

}

private fun Monkey(representation: String, divisionAfterRound: BigInteger): Monkey {
    val groups = (MONKEY_REGEX
        .matchEntire(representation)
        ?.groups
        ?: throw IllegalArgumentException("Invalid monkey representation: $representation"))
    val startingItems = groups[STARTING_WORRY_LEVELS]!!
        .value
        .split(", ")
        .map { it.toBigInteger() }
        .toMutableList()
    val operation = { old: BigInteger ->
        val leftOperand = groups[LEFT_OPERAND]!!.value
        val rightOperand = groups[RIGHT_OPERAND]!!.value
        val operator = groups[OPERATOR]!!.value
        val left = if (leftOperand == "old") old else leftOperand.toBigInteger()
        val right = if (rightOperand == "old") old else rightOperand.toBigInteger()
        when (operator) {
            "*" -> left * right
            "+" -> left + right
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
    val testDivisibleBy = groups[TEST_DIVISIBLE_BY]!!.value.toBigInteger()
    val toMonkey = TO_MONKEY.mapValues { (_, value) -> groups[value]!!.value.toInt() }

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
