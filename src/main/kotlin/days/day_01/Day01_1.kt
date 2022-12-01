package days.day_01

import util.file.text

fun main() {
    text(1)
        .split("\n\n")
        .maxOfOrNull(String::elfCalories)
        .let(::println)
}

fun String.elfCalories() =
    split("\n")
        .mapNotNull(String::toIntOrNull)
        .sum()
