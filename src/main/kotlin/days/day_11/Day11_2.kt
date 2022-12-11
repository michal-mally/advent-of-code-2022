package days.day_11

import util.Solver
import java.math.BigInteger

class Day11_2 : Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>) =
        play(input, 10_000, BigInteger.ONE)
}
