package days.day_05

import util.Solver
import util.pair.map
import util.sequence.splitBy
import util.sequence.toPair
import util.sequence.transpose

class Day05_1 : Solver<Sequence<String>, String> {

    override fun solve(input: Sequence<String>) =
        input
            .splitBy("")
            .toPair()
            .map { stacks, moves ->
                Stacks(stacks)
                    .apply {
                        moves
                            .map(::Move)
                            .let(::performMoves)
                    }
            }
            .topElements()
            .joinToString("")

}

private class Stacks(private val stacks: List<ArrayDeque<Char>>) {

    fun performMoves(moves: Sequence<Move>) {
        moves.forEach { move ->
            repeat(move.amount) {
                stacks[move.from - 1]
                    .removeLast()
                    .let { stacks[move.to - 1].addLast(it) }
            }
        }
    }

    fun topElements() =
        stacks.map { it.last() }

}

private fun Stacks(stacksInitialState: Sequence<String>): Stacks {
    fun stack(stack: Sequence<Char>) =
        stack
            .filter(Char::isLetter)
            .toList()
            .asReversed()
            .let(::ArrayDeque)

    return stacksInitialState
        .map(String::asSequence)
        .transpose()
        .filterIndexed { index, _ -> index % 4 == 1 }
        .map(::stack)
        .toList()
        .let(::Stacks)
}

private data class Move(
    val amount: Int,
    val from: Int,
    val to: Int,
)

private fun Move(definition: String) =
    Regex("""move (\d+) from (\d+) to (\d+)""")
        .matchEntire(definition)
        ?.groupValues
        ?.drop(1)
        ?.map(String::toInt)
        ?.let { Move(it[0], it[1], it[2]) }
        ?: throw IllegalArgumentException("Invalid move: $definition")
