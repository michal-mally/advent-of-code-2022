package days.day_01

import util.file.text

fun main() {
    text(1)
        .split("\n\n")
        .map(String::elfCalories)
        .sortedDescending()
        .take(3)
        .sum()
        .let(::println)
}
