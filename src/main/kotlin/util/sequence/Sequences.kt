package util.sequence

import util.iterator.nextOrNull
import util.pair.toPair

@Suppress("UNCHECKED_CAST")
fun <T : Any> Sequence<T?>.splitByNull(): Sequence<Sequence<T>> =
    splitBy(null) as Sequence<Sequence<T>>

fun <T> Sequence<T>.splitBy(delimiter: T, keepDelimiter: Boolean = false): Sequence<Sequence<T>> =
    splitBy(keepDelimiter) { it == delimiter }

fun <T> Sequence<T>.splitBy(keepDelimiter: Boolean = false, delimiter: (T) -> Boolean): Sequence<Sequence<T>> =
    sequence {
        var elements = mutableListOf<T>()
        for (n in this@splitBy) {
            if (delimiter(n)) {
                if (elements.isNotEmpty()) {
                    yield(elements.asSequence())
                    elements = mutableListOf()
                }
                if (keepDelimiter) elements.add(n)
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
            .map(Sequence<T>::iterator)
            .toList()

        while (iterators.any(Iterator<T>::hasNext)) {
            iterators
                .mapNotNull(Iterator<T>::nextOrNull)
                .asSequence()
                .let { yield(it) }
        }
    }

fun <T> Sequence<T>.toPair(): Pair<T, T> =
    toList().toPair()

fun <T> Sequence<T>.headAndTail(): Pair<T, Sequence<T>> =
    first() to drop(1)

fun <T> Sequence<T>.reversed(): Sequence<T> =
    toList()
        .reversed()
        .asSequence()
