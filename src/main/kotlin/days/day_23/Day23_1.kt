package days.day_23

import util.Solver
import util.point.Point
import util.point.plus

class Day23_1 : Solver<Sequence<String>, Int> {
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

        for (y in elves.minOf { it.y }..elves.maxOf { it.y }) {
            for (x in elves.minOf { it.x }..elves.maxOf { it.x }) {
                print(if (Point(x to y) in elves) '#' else '.')
            }
            println()
        }
        println()

        repeat(10) {
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

            proposedMoves
                .filterValues { it.size == 1 }
                .mapValues { it.value.first() }
                .forEach { (to, from) ->
                    elves.remove(from)
                    elves.add(to)
                }

            for (y in elves.minOf { it.y }..elves.maxOf { it.y }) {
                for (x in elves.minOf { it.x }..elves.maxOf { it.x }) {
                    print(if (Point(x to y) in elves) '#' else '.')
                }
                println()
            }
            println()
        }

        val x = elves.maxOf { it.x } - elves.minOf { it.x } + 1
        val y = elves.maxOf { it.y } - elves.minOf { it.y } + 1

        println(x)
        println(y)
        return x * y - elves.count()
    }

    private fun parseRow(y: Int, row: String) =
        row
            .asSequence()
            .mapIndexed { x, char -> Point(x to y) to char }
            .filter { it.second == '#' }
            .map { it.first }

}
