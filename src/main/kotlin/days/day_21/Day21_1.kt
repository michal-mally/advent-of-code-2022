package days.day_21

import util.Solver

class Day21_1 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        input
            .map { parseMonkey(it) }
            .associateBy { it.name }
            .let { Monkeys(it) }
            .evaluate("root")

    private fun parseMonkey(monkeyDeclaration: String): Monkey {
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
            return Monkey(mame) { number }
        } else {
            return Monkey(mame) {
                val monkey1 = evaluate(groups["monkey1"]!!.value)
                val operator = groups["operator"]!!.value
                val monkey2 = evaluate(groups["monkey2"]!!.value)

                when (operator) {
                    "+" -> monkey1 + monkey2
                    "-" -> monkey1 - monkey2
                    "*" -> monkey1 * monkey2
                    "/" -> monkey1 / monkey2
                    else -> throw IllegalArgumentException("Invalid operator: $operator")
                }
            }
        }
    }

    private class Monkey(val name: String, val x: Monkeys.() -> Long)

    private class Monkeys(val monkeys: Map<String, Monkey>) {
        fun evaluate(monkey: String) =
            monkeys[monkey]!!.x(this@Monkeys)
    }

}
