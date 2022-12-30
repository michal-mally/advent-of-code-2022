package days.day_07

import util.list.allPrefixes
import util.map.addIntToValue
import util.sequence.headAndTail
import util.sequence.splitBy

fun state(input: Sequence<String>) =
    State().apply {
        input
            .splitBy(true) { it.startsWith("$") }
            .map(::parseCommand)
            .forEach { it.execute() }
    }

class State(
    val currentDirectory: ArrayDeque<String> = ArrayDeque(listOf("/")),
    val directorySizes: MutableMap<String, Int> = mutableMapOf<String, Int>().withDefault { 0 },
)

private fun parseCommand(commandAndOutput: Sequence<String>): Command {
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

private sealed class Command(protected val output: Sequence<String>) {
    context(State) abstract fun execute()
}

private class CdCommand(val path: String) : Command(emptySequence()) {
    context(State) override fun execute() {
        with(currentDirectory) {
            when (path) {
                ".." -> removeLast()
                "/" -> clear()
                else -> addLast(path)
            }
        }
    }
}

private class LsCommand(output: Sequence<String>) : Command(output) {
    context(State) override fun execute() {
        val fileSizes = output
            .filterNot { it.startsWith("dir") }
            .map { it.substringBefore(" ") }
            .sumOf(String::toInt)

        currentDirectory
            .allPrefixes()
            .map { it.joinToString("/") }
            .forEach { directorySizes.addIntToValue(it, fileSizes) }
    }
}
