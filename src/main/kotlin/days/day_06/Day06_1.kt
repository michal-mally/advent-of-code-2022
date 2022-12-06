package days.day_06

import util.Solver

class Day06_1: Solver<Sequence<Char>, Int> {

    override fun solve(input: Sequence<Char>): Int {
        val buffer = ArrayDeque<Char>()
        var position = 0

        val iterator = input.iterator()
        while (buffer.toSet().size < 4) {
            position++
            buffer.addLast(iterator.next())
            if (buffer.size > 4) buffer.removeFirst()
        }

        return position
    }

}
