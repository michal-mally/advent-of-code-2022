package days.day_06

fun solve(input: Sequence<Char>, length: Int): Int {
    val buffer = ArrayDeque<Char>()
    var position = 0

    val iterator = input.iterator()
    while (buffer.toSet().size < length) {
        position++
        buffer += iterator.next()
        if (buffer.size > length) buffer.removeFirst()
    }

    return position
}
