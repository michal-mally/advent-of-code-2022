package util.sequence

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
