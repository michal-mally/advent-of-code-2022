package days.day_06

import util.number.ints.count

fun solve(input: Sequence<Char>, length: Int): Int {
    val buffer = ArrayDeque<Char>()

    val iterator = input.iterator()
    return count {
        while (buffer.toSet().size < length) {
            inc()
            buffer += iterator.next()
            if (buffer.size > length) buffer.removeFirst()
        }
    }
}
