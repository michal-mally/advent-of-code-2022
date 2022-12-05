package util.iterator

fun <T> Iterator<T>.nextOrNull(): T? =
    if (hasNext()) next() else null
