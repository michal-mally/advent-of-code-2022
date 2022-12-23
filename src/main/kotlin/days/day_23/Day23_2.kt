package days.day_23

import util.Solver
import util.point.Point
import util.point.plus

class Day23_2 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val elves = input
            .flatMapIndexed { y, row -> parseRow(y, row) }
            .toMutableSet()

        val directions = sequence {
            val directionOrder = mutableListOf(
                setOf(Point(-1 to -1), Point(0 to -1), Point(1 to -1)),
                setOf(Point(-1 to 1), Point(0 to 1), Point(1 to 1)),
                setOf(Point(-1 to -1), Point(-1 to 0), Point(-1 to 1)),
                setOf(Point(1 to -1), Point(1 to 0), Point(1 to 1)),
            )
            while (true) {
                yield(directionOrder.toList())
                directionOrder.add(directionOrder.removeFirst())
            }
        }.iterator()

        var round = 0
        while(true) {
            round++
            val consideredDirections = directions.next()

            val proposedMoves = mutableMapOf<Point<Int>, MutableSet<Point<Int>>>().withDefault { mutableSetOf() }
            for (elf in elves) {
                consideredDirections
                    .takeIf { it.flatten().map { elf + it }.intersect(elves).isNotEmpty() }
                    ?.firstOrNull { dirs -> dirs.map { elf + it }.intersect(elves).isEmpty() }
                    ?.first { it.x == 0 || it.y == 0 }
                    ?.let { elf + it }
                    ?.let { proposedMoves[it] = proposedMoves.getValue(it).apply { add(elf) } }
            }

            if (proposedMoves.isEmpty()) {
                return round
            }

            proposedMoves
                .filterValues { it.size == 1 }
                .mapValues { it.value.first() }
                .forEach { (to, from) ->
                    elves.remove(from)
                    elves.add(to)
                }
        }
    }

    private fun parseRow(y: Int, row: String) =
        row
            .asSequence()
            .mapIndexed { x, char -> Point(x to y) to char }
            .filter { it.second == '#' }
            .map { it.first }

}
