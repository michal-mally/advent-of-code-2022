package days.day_20

import util.number.longs.nonNegativeModulo
import java.util.*

fun solve(input: Sequence<Long>, numberOfMixes: Int = 1): Long {
    val initialListIndexed = input
        .mapIndexed { index, number -> IndexedValue(index to number) }
        .toList()

    return LinkedList(initialListIndexed)
        .apply { repeat(numberOfMixes) { mix(initialListIndexed) } }
        .map { it.value }
        .cyclicSequence(0L)
        .filterIndexed { index, _ -> index % 1000 == 0 }
        .drop(1)
        .take(3)
        .sum()
}

private fun MutableList<IndexedValue<Long>>.mix(initialListIndexed: List<IndexedValue<Long>>) {
    for (indexedValue in initialListIndexed) {
        indexOf(indexedValue)
            .also(::removeAt)
            .let { index -> index + indexedValue.value }
            .let { index -> index nonNegativeModulo size }
            .let { index -> add(index, indexedValue) }
    }
}

private fun <T> Collection<T>.cyclicSequence(start: T) =
    let { list ->
        sequence {
            yieldAll(list.dropWhile { it != start })
            while (true) {
                yieldAll(list)
            }
        }
    }

private data class IndexedValue<T>(private val indexAndValue: Pair<Int, T>) {
    val value get() = indexAndValue.second
}
