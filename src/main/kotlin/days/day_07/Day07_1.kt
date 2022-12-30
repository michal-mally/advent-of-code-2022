package days.day_07

import util.Solver

class Day07_1 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        state(input)
            .directorySizes
            .values
            .filter { it <= 100_000 }
            .sum()

}
