package days.day_23

import util.point.*
import kotlin.math.abs

fun elves(input: Sequence<String>): MutableSet<Point<Int>> {
    fun parseRow(y: Int, row: String) =
        row
            .asSequence()
            .mapIndexed { x, char -> Point(x to y) to char }
            .filter { it.second == '#' }
            .map { it.first }

    return input
        .flatMapIndexed { y, row -> parseRow(y, row) }
        .toMutableSet()
}

fun directions() =
    sequence {
        fun direction(p: Point<Int>): Set<Point<Int>> {
            require(p.x == 0 || p.y == 0)
            require(abs(p.x) == 1 || abs(p.y) == 1)
            return Point(0 to 0)
                .adjacents()
                .filter { p.x == 0 || it.x == p.x }
                .filter { p.y == 0 || it.y == p.y }
                .toSet()
        }

        val directionsOrdered = listOf(north, south, west, east)
            .map(::direction)
            .toMutableList()
        while (true) {
            yield(directionsOrdered.toList().asSequence())
            directionsOrdered.add(directionsOrdered.removeFirst())
        }
    }.iterator()

fun performMoves(elves: MutableSet<Point<Int>>, consideredDirections: Sequence<Set<Point<Int>>>) =
    moves(elves, consideredDirections)
        .takeIf { it.isNotEmpty() }
        ?.forEach { (from, to) ->
            elves.remove(from)
            elves.add(to)
        }

private fun moves(elves: Set<Point<Int>>, consideredDirections: Sequence<Set<Point<Int>>>) =
    buildMap<Point<Int>, MutableSet<Point<Int>>> {
        for (elf in elves) {
            consideredDirections
                .takeIf { elf.adjacents().toSet().intersect(elves).isNotEmpty() }
                ?.firstOrNull { dirs -> dirs.map { elf + it }.intersect(elves).isEmpty() }
                ?.first { it.x == 0 || it.y == 0 }
                ?.let { elf + it }
                ?.let { this[it] = this.getOrDefault(it, mutableSetOf()).apply { add(elf) } }
        }
    }
        .filterValues { it.size == 1 }
        .mapValues { it.value.first() }
        .map { (to, from) -> from to to }
