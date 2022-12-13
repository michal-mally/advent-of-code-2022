package days.day_13

import util.Solver

class Day13_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val allSignals = input
            .filter { it.isNotBlank() }
            .map { parseLine(it) }
            .toList()

        val first = parseLine("[[2]]")
        val second = parseLine("[[6]]")
        return (allSignals.count { it < first } + 1) * (allSignals.count { it < second } + 2)
    }

}
