package util.list

fun <T> List<T>.allPrefixes(): List<List<T>> =
    (0..size).map { subList(0, it) }
