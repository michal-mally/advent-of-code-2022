package days.day_13

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
            is IntElementList ->
                if (value.isEmpty() || other.value.isEmpty()) {
                    value.size.compareTo(other.value.size)
                } else {
                    value
                        .first()
                        .compareTo(other.value.first())
                        .takeIf { it != 0 }
                        ?: IntElementList(value.drop(1)).compareTo(IntElementList(other.value.drop(1)))
                }
        }
}

fun parseLine(line: String) =
    tokenize(line)
        .toList()
        .drop(1)
        .let { ArrayDeque(it) }
        .let { createList(it) }

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
                if (next == "[") createList(signal) else IntElement(next.toInt())
            )
        }
    }.let(::IntElementList)
