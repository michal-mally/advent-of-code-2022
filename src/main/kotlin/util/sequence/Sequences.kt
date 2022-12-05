package util.sequence

import util.pair.toPair

@Suppress("UNCHECKED_CAST")
fun <T : Any> Sequence<T?>.splitByNull(): Sequence<Sequence<T>> =
    splitBy(null) as Sequence<Sequence<T>>

fun <T> Sequence<T>.splitBy(delimiter: T): Sequence<Sequence<T>> =
    sequence {
        var elements = mutableListOf<T>()
        for (n in this@splitBy) {
            if (n == delimiter) {
                if (elements.isNotEmpty()) {
                    yield(elements.asSequence())
                    elements = mutableListOf()
                }
            } else {
                elements += n
            }
        }
        if (elements.isNotEmpty()) {
            yield(elements.asSequence())
        }
    }

fun <T> Sequence<Sequence<T>>.transpose(): Sequence<Sequence<T>> =
    sequence {
        val iterators = this@transpose
            .map { it.iterator() }
            .toList()

        while (iterators.any { it.hasNext() }) {
            iterators
                .filter { it.hasNext() }
                .map { it.next() }
                .asSequence()
                .let { yield(it) }
        }
    }

fun <T> Sequence<T>.toPair(): Pair<T, T> =
    toList().toPair()
