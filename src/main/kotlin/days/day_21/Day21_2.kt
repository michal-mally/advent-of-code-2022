package days.day_21

import util.Solver

class Day21_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val listOfMonkeys = input
            .map { parseMonkey(it) }
            .toList()
        val rootOperation = listOfMonkeys
            .first { it.name == "root" }
            .operation as? BinaryOperation
            ?: throw IllegalArgumentException("Root is not a binary operation")
        val monkeys = listOfMonkeys
            .filterNot { it.name in setOf("humn", "root") }
            .associateBy { it.name }
            .let { Monkeys(it) }

        val knownValues = mutableMapOf<String, Long>()
        try {
            knownValues[rootOperation.left] = monkeys.evaluate(rootOperation.left)
            knownValues[rootOperation.right] = knownValues[rootOperation.left]!!
        } catch (e: Exception) {
            null
        }

        try {
            knownValues[rootOperation.right] = monkeys.evaluate(rootOperation.right)
            knownValues[rootOperation.left] = knownValues[rootOperation.right]!!
        } catch (e: Exception) {
            null
        }

        for (monkey in listOfMonkeys.filter { it.name !in knownValues.keys }) {
            val value = try {
                monkeys.evaluate(monkey.name)
            } catch (e: Exception) {
                null
            }
            if (value != null) {
                knownValues[monkey.name] = value
            }
        }

        while ("humn" !in knownValues.keys) {
            val toSolve = monkeys
                .monkeys
                .values
                .filter { it.name in knownValues.keys }
                .filter { it.operation is BinaryOperation }
                .map { it as Monkey<BinaryOperation> }
                .filter { knownValues.keys.intersect(setOf(it.operation.left, it.operation.right)).size == 1 }
            for (mys in toSolve) {
                mys
                    .operation
                    .solve(knownValues[mys.name]!!, knownValues[mys.operation.left], knownValues[mys.operation.right])
                    .let { (name, value) -> knownValues[name] = value }
            }
        }

        return knownValues["humn"]!!
    }

    private fun parseMonkey(monkeyDeclaration: String): Monkey<*> {
        val groups = ("""(?<name>\w+): ((?<number>\d+)|(?<monkey1>\w+) (?<operator>[+\-*/]) (?<monkey2>\w+))"""
            .toRegex()
            .matchEntire(monkeyDeclaration)
            ?.groups
            ?: throw IllegalArgumentException("Invalid monkey declaration: $monkeyDeclaration"))

        val mame = groups["name"]!!.value
        val number = groups["number"]
            ?.value
            ?.toLong()
        if (number != null) {
            return Monkey(mame, Number(number))
        } else {
            return Monkey(
                mame,
                BinaryOperation(
                    groups["monkey1"]!!.value,
                    groups["operator"]!!.value,
                    groups["monkey2"]!!.value,
                )
            )
        }
    }

    private class Monkey<T : Operation>(val name: String, val operation: T)

    private class Monkeys(val monkeys: Map<String, Monkey<*>>) {
        fun evaluate(monkey: String) =
            monkeys[monkey]!!.operation.evaluate()
    }

    private sealed interface Operation {
        context(Monkeys) fun evaluate(): Long
    }

    private data class Number(val value: Long) : Operation {
        context(Monkeys) override fun evaluate() = value

    }

    private data class BinaryOperation(val left: String, val operator: String, val right: String) : Operation {
        context(Monkeys) override fun evaluate(): Long {
            val monkey1 = evaluate(left)
            val monkey2 = evaluate(right)

            return when (operator) {
                "+" -> monkey1 + monkey2
                "-" -> monkey1 - monkey2
                "*" -> monkey1 * monkey2
                "/" -> monkey1 / monkey2
                else -> throw IllegalArgumentException("Invalid operator: $operator")
            }
        }

        fun solve(result: Long, leftValue: Long?, rightValue: Long?): Pair<String, Long> {
            require(leftValue != null || rightValue != null) { "Either left or right value must be known" }
            require(leftValue == null || rightValue == null) { "Either left or right value must be known" }
            return if (leftValue != null) {
                when (operator) {
                    "+" -> right to result - leftValue
                    "-" -> right to leftValue - result
                    "*" -> right to result / leftValue
                    "/" -> right to leftValue / result
                    else -> throw IllegalArgumentException("Invalid operator: $operator")
                }
            } else if (rightValue != null) {
                when (operator) {
                    "+" -> left to result - rightValue
                    "-" -> left to result + rightValue
                    "*" -> left to result / rightValue
                    "/" -> left to result * rightValue
                    else -> throw IllegalArgumentException("Invalid operator: $operator")
                }
            } else {
                throw IllegalArgumentException("Either left or right value must be known")
            }
        }
    }

}
