package days.day_05

import util.Solver
import util.sequence.splitBy

class Day05_1 : Solver<Sequence<String>, String> {

    override fun solve(input: Sequence<String>): String {
        val (initialState, moves) = input
            .splitBy("")
            .toList()

        val reversed = initialState
            .toList()
            .reversed()
        val stackCount = reversed
            .first()
            .count { it.isDigit() }
        val stacks = List(stackCount) { ArrayDeque<Char>() }
        reversed
            .drop(1)
            .forEach { l ->
                repeat(stackCount) { stackNumber ->
                    l
                        .getOrNull(stackIndex(stackNumber))
                        ?.takeIf { !it.isWhitespace() }
                        ?.let { stacks[stackNumber].addLast(it) }
                }
            }

        moves
            .forEach { move ->
                val (a, b, c) = Regex("""move (\d+) from (\d+) to (\d+)""")
                    .matchEntire(move)
                    ?.groupValues
                    ?.drop(1)
                    ?.map { it.toInt() }
                    ?: throw IllegalArgumentException("Invalid move: $move")

                repeat(a) {
                    stacks[c - 1].addLast(stacks[b - 1].removeLast())
                }
            }

        return stacks
            .map { it.last() }
            .joinToString("")
    }

    private fun stackIndex(n: Int) =
        1 + n * 4

}
