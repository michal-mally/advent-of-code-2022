package days.day_11

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

private const val STARTING_WORRY_LEVELS = "startingWorryLevels"
private const val LEFT_OPERAND = "leftOperand"
private const val OPERATOR = "operator"
private const val RIGHT_OPERAND = "rightOperand"
private const val TEST_DIVISIBLE_BY = "testDivisibleBy"
private const val IF_TRUE_THROW_TO_MONKEY = "ifTrueThrowToMonkey"
private const val IF_FALSE_THROW_TO_MONKEY = "ifFalseThrowToMonkey"

private val MONKEY_REGEX = """
Monkey \d+:
  Starting items: (?<$STARTING_WORRY_LEVELS>\d+(, \d+)*)
  Operation: new = (?<$LEFT_OPERAND>old|\d+) (?<$OPERATOR>[*\+]) (?<$RIGHT_OPERAND>old|\d+)
  Test: divisible by (?<$TEST_DIVISIBLE_BY>\d+)
    If true: throw to monkey (?<$IF_TRUE_THROW_TO_MONKEY>\d+)
    If false: throw to monkey (?<$IF_FALSE_THROW_TO_MONKEY>\d+)
"""
    .trim()
    .toRegex()

data class Monkey(
    val worryLevels: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testDivisibleBy: BigInteger,
    val ifTrueThrowToMonkey: Int,
    val ifFalseThrowToMonkey: Int,
    val divisionAfterRound: BigInteger,
) {

    context(List<Monkey>) fun play(): Int {
        val numberSpace = map { it.testDivisibleBy }
            .toSet()
            .reduce(BigInteger::multiply)

        val worryLevelsCount = worryLevels.size
        while (worryLevels.isNotEmpty()) {
            val oldWorryLevel = worryLevels.removeLast()
            val newWorryLevel = operation(oldWorryLevel) / divisionAfterRound
            get(if (newWorryLevel.mod(testDivisibleBy) == ZERO) ifTrueThrowToMonkey else ifFalseThrowToMonkey)
                .worryLevels
                .add(newWorryLevel.mod(numberSpace))
        }

        return worryLevelsCount
    }

}

fun Monkey(representation: String, divisionAfterRound: BigInteger = ONE): Monkey {
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
    val ifTrueThrowToMonkey = groups[IF_TRUE_THROW_TO_MONKEY]!!.value.toInt()
    val ifFalseThrowToMonkey = groups[IF_FALSE_THROW_TO_MONKEY]!!.value.toInt()

    return Monkey(
        startingItems,
        operation,
        testDivisibleBy,
        ifTrueThrowToMonkey,
        ifFalseThrowToMonkey,
        divisionAfterRound
    )
}
