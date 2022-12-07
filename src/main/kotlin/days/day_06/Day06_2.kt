package days.day_06

import util.Solver

class Day06_2: Solver<Sequence<Char>, Int> {

    override fun solve(input: Sequence<Char>): Int {
        val length = 14
        val buffer = ArrayDeque<Char>()
        var position = 0

        val iterator = input.iterator()
        while (buffer.toSet().size < length) {
            position++
            buffer.addLast(iterator.next())
            if (buffer.size > length) buffer.removeFirst()
        }

        return position
    }

}
