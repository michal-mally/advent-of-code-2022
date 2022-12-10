package days.day_10

import util.Solver

class Day10_1: Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val effects = mutableListOf(1)
        input
            .map { it.split(" ") }
            .forEach { cmd ->
                effects += 0
                if (cmd[0] == "addx") {
                    effects += cmd[1].toInt()
                }
            }

        return effects
            .runningReduce(Int::plus)
            .let {x ->
                listOf(20, 60, 100, 140, 180, 220).map { x[it-1] * it }
            }
            .sum()
    }

}
