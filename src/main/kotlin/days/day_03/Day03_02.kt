package days.day_03

import util.file.lines

fun main() {
    lines(3)
        .chunked(3)
        .map { group ->
            group
                .map(String::toSet)
                .reduce(Set<Char>::intersect)
                .single()
        }
        .map {
            it.code - if (it.isLowerCase()) 'a'.code - 1 else 'A'.code - 27
        }
        .sum()
        .let(::println)
}
