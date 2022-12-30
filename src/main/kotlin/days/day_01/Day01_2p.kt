package days.day_01

import util.Solver
import java.util.*

class Day01_2p : Solver<Sequence<String>, Int> {

    override fun solve(input: Sequence<String>): Int {
        val topElves = PriorityQueue<Int>()

        var elfSum = 0
        for (number in input.map(String::toIntOrNull)) {
            if (number != null) {
                elfSum += number
            } else {
                topElves += elfSum
                elfSum = 0
                if (topElves.size > 3) {
                    topElves.removeFirst()
                }
            }
        }

        return topElves.sum()
    }

    private fun <T> MutableCollection<T>.removeFirst() =
        remove(first())

}
