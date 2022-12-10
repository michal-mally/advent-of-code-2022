package days.day_10

import util.Solver

class Day10_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val xRegisterValues = xRegisterValues(input)
        return (20..220 step 40).sumOf { xRegisterValues[it - 1] * it }
    }

}
