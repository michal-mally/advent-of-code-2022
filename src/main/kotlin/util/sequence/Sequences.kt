package util.sequence

@Suppress("UNCHECKED_CAST")
fun <T : Any> Sequence<T?>.splitByNull() =
    splitBy(null) as Sequence<List<T>>

fun <T> Sequence<T>.splitBy(delimiter: T): Sequence<List<T>> =
    sequence {
        var elements = mutableListOf<T>()
        for (n in this@splitBy) {
            if (n == delimiter) {
                if (elements.isNotEmpty()) {
                    yield(elements)
                    elements = mutableListOf()
                }
            } else {
                elements += n
            }
        }
        if (elements.isNotEmpty()) {
            yield(elements)
        }
    }
