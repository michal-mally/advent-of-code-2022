package util.list

fun <T> List<T>.allPrefixes(): List<List<T>> =
    (0..size).map { subList(0, it) }

infix fun <T> List<T>.cartesianProduct(other: List<T>) =
    flatMap { a -> other.map { b -> a to b } }
