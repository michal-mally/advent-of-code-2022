package days.day_07

import util.Solver
import util.sequence.headAndTail
import util.sequence.splitBy

class Day07_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val state = State()
        input
            .splitBy(true) { it.startsWith("$") }
            .map(::parseCommandAndOutput)
            .forEach { it.execute(state) }
        return state
            .directorySizes
            .values
            .filter { it <= 100_000 }
            .sum()
    }

    private fun parseCommandAndOutput(commandAndOutput: Sequence<String>): Command {
        val (command, args) = commandAndOutput
            .first()
            .splitToSequence(" ")
            .drop(1)
            .headAndTail()
        val output = commandAndOutput.drop(1)

        return when (command) {
            "cd" -> CdCommand(args.single()).also { check(output.toList().isEmpty()) { "cd does not have any output" } }
            "ls" -> LsCommand(output).also { check(args.toList().isEmpty()) { "ls command does not take arguments" } }
            else -> throw IllegalArgumentException("Unknown command: $command")
        }
    }

    private class State(
        val currentDirectory: ArrayDeque<String> = ArrayDeque(),
        val directorySizes: MutableMap<String, Int> = mutableMapOf<String, Int>().withDefault { 0 },
    )

    private sealed class Command(protected val output: Sequence<String>) {
        abstract fun execute(state: State)
    }

    private class CdCommand(val path: String) : Command(emptySequence()) {
        override fun execute(state: State) {
            with(state.currentDirectory) {
                when (path) {
                    ".." -> removeLast()
                    "/" -> clear()
                    else -> addLast(path)
                }
            }
        }

    }

    private class LsCommand(output: Sequence<String>) : Command(output) {
        override fun execute(state: State) {
            val fileSizes = output
                .filterNot { it.startsWith("dir") }
                .map { it.substringBefore(" ") }
                .sumOf(String::toInt)

            val path = ArrayDeque(state.currentDirectory)
            while (path.isNotEmpty()) {
                val joinedPath = path.joinToString("/")
                state.directorySizes[joinedPath] = state.directorySizes.getValue(joinedPath) + fileSizes
                path.removeLast()
            }
        }

    }

}
