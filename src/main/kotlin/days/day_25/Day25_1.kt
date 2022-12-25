package days.day_25

import util.Solver

class Day25_1 : Solver<Sequence<String>, String> {
    override fun solve(input: Sequence<String>): String {
        println(1L.toSNAFU())
        println(2L.toSNAFU())
        println(3L.toSNAFU())
        println(4L.toSNAFU())
        println(5L.toSNAFU())
        println(314159265L.toSNAFU())

        return input
            .sumOf { it.fromSNAFU() }
            .toSNAFU()
    }

    private fun String.fromSNAFU() =
        toList()
            .map {
                when (it) {
                    '-' -> "-1"
                    '=' -> "-2"
                    else -> "$it"
                }
            }
            .map { it.toLong() }
            .reduce { acc, v -> acc * 5 + v }

    private fun Long.toSNAFU(): String {
        if (this == 0L) return ""

        val remainder = this % 5
        val asString = when (remainder) {
            3L -> "="
            4L -> "-"
            else -> remainder.toString()
        }
        val next = if (remainder < 3) this / 5 else this / 5 + 1
        return next.toSNAFU() + asString
    }


}
