package days.day_02

import util.Solver
import util.pair.*

class Day02_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        input
            .map(::transformLine)
            .flatMap { it.toSequence() }
            .sum()

    private fun transformLine(line: String) =
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

}

