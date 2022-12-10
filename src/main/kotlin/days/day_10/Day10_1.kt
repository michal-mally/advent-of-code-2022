package days.day_10

import util.Solver

class Day10_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        with(xRegisterValues(input)) {
            (20..220 step 40).sumOf { this[it - 1] * it }
        }

}
