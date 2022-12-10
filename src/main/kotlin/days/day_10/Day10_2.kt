package days.day_10

import util.Solver

class Day10_2: Solver<Sequence<String>, String> {

    override fun solve(input: Sequence<String>): String {
        val effects = mutableListOf(1)
        input
            .map { it.split(" ") }
            .forEach { cmd ->
                effects += 0
                if (cmd[0] == "addx") {
                    effects += cmd[1].toInt()
                }
            }

        val x = effects.runningReduce(Int::plus)

        return buildString {
            for (cycle in 0..<240) {
                if (cycle % 40 == 0) appendLine()
                val spritePosition = x[cycle]
                if ((cycle % 40) in spritePosition - 1..spritePosition + 1) {
                    append("#")
                } else
                    append(".")
            }
        }.trim()
    }

}
