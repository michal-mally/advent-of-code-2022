package days.day_07

import util.Solver

class Day07_2 : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>) =
        state(input)
            .run {
                val spaceToBeFreed = 30_000_000 - (70_000_000 - directorySizes.getValue(""))

                directorySizes
                    .values
                    .filter { it >= spaceToBeFreed }
                    .min()
            }

}
