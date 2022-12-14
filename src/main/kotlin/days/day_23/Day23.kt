package days.day_23

import util.point.Point
import util.point.ints.ZERO
import util.point.ints.adjacents
import util.point.ints.east
import util.point.ints.north
import util.point.ints.plus
import util.point.ints.south
import util.point.ints.west
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
            return ZERO
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
    asSequence()
        .mapNotNull { elf -> move(elf, consideredDirections)?.let { to -> elf to to } }
        .groupBy { it.second }
        .map { it.value }
        .filter { it.size == 1 }
        .associate { it.first() }

context(Set<Point<Int>>) private fun move(elf: Point<Int>, consideredDirections: Sequence<Set<Point<Int>>>) =
    consideredDirections
        .takeIf { elf.adjacents().toSet().intersect(this@Set).isNotEmpty() }
        ?.firstOrNull { dirs -> dirs.map { elf + it }.intersect(this@Set).isEmpty() }
        ?.first { it.x == 0 || it.y == 0 }
        ?.let { elf + it }
