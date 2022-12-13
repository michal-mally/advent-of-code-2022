package days.day_13

import util.Solver
import util.sequence.splitBy
import util.sequence.toPair

class Day13_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        input
            .splitBy { it.isBlank() }
            .map(::parseGroup)
            .mapIndexed { index, v -> (index + 1) to (v.first <= v.second) }
            .filter { it.second }
            .map { it.first }
            .sum()

    private fun parseGroup(group: Sequence<String>) =
        group
            .map(::tokenize)
            .map(Sequence<String>::toList)
            .map(::ArrayDeque)
            .onEach(ArrayDeque<String>::removeFirst)
            .map(::createList)
            .toPair()

    private fun tokenize(it: String) =
        Regex("""\[|\]|\d+""")
            .findAll(it)
            .map(MatchResult::value)

    private fun createList(signal: ArrayDeque<String>): Element =
        buildList {
            while (true) {
                val next = signal
                    .removeFirst()
                    .takeIf { it != "]" }
                    ?: break

                add(
                    if (next == "[")
                        createList(signal)
                    else
                        IntElement(next.toInt())
                )
            }
        }.let(::IntElementList)

    sealed interface Element : Comparable<Element>

    class IntElement(val value: Int) : Element {
        override fun compareTo(other: Element) =
            when (other) {
                is IntElement -> value.compareTo(other.value)
                is IntElementList -> IntElementList(listOf(this)).compareTo(other)
            }
    }

    class IntElementList(val value: List<Element>) : Element {
        override fun compareTo(other: Element): Int =
            when (other) {
                is IntElement -> compareTo(IntElementList(listOf(other)))
                is IntElementList -> if (value.isEmpty() && other.value.isEmpty()) {
                    0
                } else if (value.isEmpty()) {
                    -1
                } else if (other.value.isEmpty()) {
                    1
                } else if (value.first().compareTo(other.value.first()) != 0) {
                    value.first().compareTo(other.value.first())
                } else {
                    IntElementList(value.drop(1)).compareTo(IntElementList(other.value.drop(1)))
                }
            }
    }

}
