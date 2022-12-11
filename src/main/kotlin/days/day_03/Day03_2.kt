package days.day_03

import util.Solver

class Day03_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .chunked(3)
            .map(::extractCharFromGroup)
            .sumOf {
                it.code - if (it.isLowerCase()) 'a'.code - 1 else 'A'.code - 27
            }

    private fun extractCharFromGroup(group: List<String>) =
        group
            .map(String::toSet)
            .reduce(Set<Char>::intersect)
            .single()

}

