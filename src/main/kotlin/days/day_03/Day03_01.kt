package days.day_03

import util.file.lines

fun main() {
    lines(3)
        .map { line ->
            line
                .toList()
                .chunked(line.length / 2)
                .map(List<Char>::toSet)
                .reduce(Set<Char>::intersect)
                .single()
        }
        .map {
            it.code - if (it.isLowerCase()) 'a'.code - 1 else 'A'.code - 27
        }
        .sum()
        .let(::println)
}
