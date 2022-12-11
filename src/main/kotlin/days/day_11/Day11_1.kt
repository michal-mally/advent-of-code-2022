package days.day_11

import util.Solver
import util.sequence.splitBy

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
""".trim().toRegex()

private data class Monkey(
    val worryLevels: MutableList<Int>,
    val operation: (Int) -> Int,
    val testDivisibleBy: Int,
    val ifTrueThrowToMonkey: Int,
    val ifFalseThrowToMonkey: Int
) {

    context(List<Monkey>) fun play(): Int {
        val worryLevelsCount = worryLevels.size
        while (worryLevels.isNotEmpty()) {
            val oldWorryLevel = worryLevels.removeLast()
            val newWorryLevel = operation(oldWorryLevel) / 3
            get(if (newWorryLevel % testDivisibleBy == 0) ifTrueThrowToMonkey else ifFalseThrowToMonkey)
                .worryLevels
                .add(newWorryLevel)
        }

        return worryLevelsCount
    }

}

private fun Monkey(representation: String): Monkey {
    val groups = (MONKEY_REGEX
        .matchEntire(representation)
        ?.groups
        ?: throw IllegalArgumentException("Invalid monkey representation: $representation"))
    val startingItems = groups[STARTING_WORRY_LEVELS]!!
        .value
        .split(", ")
        .map { it.toInt() }
        .toMutableList()
    val operation = { old: Int ->
        val leftOperand = groups[LEFT_OPERAND]!!.value
        val rightOperand = groups[RIGHT_OPERAND]!!.value
        val operator = groups[OPERATOR]!!.value
        val left = if (leftOperand == "old") old else leftOperand.toInt()
        val right = if (rightOperand == "old") old else rightOperand.toInt()
        when (operator) {
            "*" -> left * right
            "+" -> left + right
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
    val testDivisibleBy = groups[TEST_DIVISIBLE_BY]!!
        .value
        .toInt()
    val ifTrueThrowToMonkey = groups[IF_TRUE_THROW_TO_MONKEY]!!.value.toInt()
    val ifFalseThrowToMonkey = groups[IF_FALSE_THROW_TO_MONKEY]!!.value.toInt()

    return Monkey(startingItems, operation, testDivisibleBy, ifTrueThrowToMonkey, ifFalseThrowToMonkey)
}

class Day11_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val monkeys = input
            .splitBy { it == "" }
            .map { it.joinToString("\n") }
            .map(::Monkey)
            .toList()

        val inspections = MutableList(monkeys.size) { 0 }
        with(monkeys) {
            repeat(20) {
                forEachIndexed { i, monkey ->
                    inspections[i] += monkey.play()
                }
            }
        }

        return inspections
            .sortedDescending()
            .take(2)
            .reduce(Int::times)
    }
}
