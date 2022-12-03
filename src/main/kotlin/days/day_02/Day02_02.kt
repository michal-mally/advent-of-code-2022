package days.day_02

import util.file.lines
import util.pair.*

fun main() {
    lines(2)
        .flatMap { line ->
            line
                .split(" ")
                .map { it.first() }
                .map { it.code }
                .toPair()
                .map { first, second -> (first - 'A'.code) to (second - 'X'.code) }
                .mapRight { first, second -> first + second + 2 }
                .mapLeft { first, second -> second - first + 4 }
                .mapLeftAndRight { it % 3 }
                .map { first, second -> first * 3 to second + 1 }
                .toSequence()
        }
        .sum()
        .let(::println)
}
