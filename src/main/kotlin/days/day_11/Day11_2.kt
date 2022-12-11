package days.day_11

import util.Solver
import util.sequence.splitBy

class Day11_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val monkeys = input
            .splitBy { it == "" }
            .map { it.joinToString("\n") }
            .map { Monkey(it) }
            .toList()

        val inspections = MutableList(monkeys.size) { 0L }
        with(monkeys) {
            repeat(10_000) {
                forEachIndexed { i, monkey ->
                    inspections[i] += monkey.play().toLong()
                }
            }
        }

        return inspections
            .sortedDescending()
            .take(2)
            .reduce(Long::times)
    }
}
