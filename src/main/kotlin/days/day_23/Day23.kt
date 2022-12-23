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
            yield(directionsOrdered.asSequence())
            directionsOrdered += directionsOrdered.removeFirst()
        }
    }.iterator()

context(MutableSet<Point<Int>>) fun performMoves(consideredDirections: Sequence<Set<Point<Int>>>) =
    moves(consideredDirections)
        .takeIf { it.isNotEmpty() }
        ?.forEach { (from, to) ->
            remove(from)
            add(to)
        }

context(Set<Point<Int>>) private fun moves(consideredDirections: Sequence<Set<Point<Int>>>) =
    buildMap<Point<Int>, MutableSet<Point<Int>>> {
        for (elf in this@Set) {
            moveForElf(elf, consideredDirections)
                ?.let { to -> this[to] = this.getOrDefault(to, mutableSetOf()).apply { this += elf } }
        }
    }
        .filterValues { it.size == 1 }
        .mapValues { it.value.first() }
        .map { (to, from) -> from to to }

context(Set<Point<Int>>) private fun moveForElf(elf: Point<Int>, consideredDirections: Sequence<Set<Point<Int>>>) =
    consideredDirections
        .takeIf { elf.adjacents().toSet().intersect(this@Set).isNotEmpty() }
        ?.firstOrNull { dirs -> dirs.map { elf + it }.intersect(this@Set).isEmpty() }
        ?.first { it.x == 0 || it.y == 0 }
        ?.let { elf + it }
