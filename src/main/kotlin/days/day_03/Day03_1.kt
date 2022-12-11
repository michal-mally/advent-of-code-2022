package days.day_03

import util.Solver

class Day03_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::extractCharFromLine)
            .sumOf {
                it.code - if (it.isLowerCase()) 'a'.code - 1 else 'A'.code - 27
            }

    private fun extractCharFromLine(line: String) =
        line
            .toList()
            .chunked(line.length / 2)
            .map(List<Char>::toSet)
            .reduce(Set<Char>::intersect)
            .single()

}

