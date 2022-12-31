package days.day_24

import util.Solver
import util.number.ints.nonNegativeModulo
import util.point.Point
import util.point.ints.manhattanDistanceTo
import util.point.ints.plus
import kotlin.math.min

class Day24_2 : Solver<Sequence<String>, Int> {

    var currentMin = Int.MAX_VALUE
    var considered = 0L
    var cache = mutableMapOf<Pair<Int, Point<Int>>, Int>()
    override fun solve(input: Sequence<String>): Int {
        val inputAsList = input
            .toList()
            .drop(1)
            .dropLast(1)
            .map { it.drop(1).dropLast(1) }
        val xSize = inputAsList.first().length
        val ySize = inputAsList.size

        var valley = inputAsList
            .asSequence()
            .flatMapIndexed { y, row -> parseRow(y, row) }
            .groupBy { it.location }
            .let { Valley(it, xSize, ySize) }

        var valleys = Valleys(valley)

        Thread {
            while (true) {
                Thread.sleep(5000)
                println("$currentMin $considered")
            }
        }
            .apply { isDaemon = true }
            .start()

        currentMin = Int.MAX_VALUE
        cache.clear()
        val fromStartToEnd = stepsTo(valley.from, valley.to, 0, valleys)
        currentMin = Int.MAX_VALUE
        cache.clear()
        val fromEndToStart = stepsTo(valley.to, valley.from, fromStartToEnd, valleys)
        currentMin = Int.MAX_VALUE
        cache.clear()
        val fromStartToEndAgain = stepsTo(valley.from, valley.to, fromEndToStart, valleys)
        return fromStartToEndAgain
    }

    private val possibleMoves = sequenceOf(
        0 to 0,
        1 to 0,
        -1 to 0,
        0 to 1,
        0 to -1,
    )
        .map { Point(it) }
        .toList()

    private fun stepsTo(from: Point<Int>, to: Point<Int>, round: Int, valleys: Valleys): Int {
        if (round to from in cache)
            return cache[round to from]!!

        considered++
        if (from == to) {
            currentMin = min(currentMin, round)
            return round
        }

        //       val x = valleys.round(round)
//        if (from.manhattanDistanceTo(to) > (x.xSize + x.ySize) + 20 - round * 0.31) {
//            return Int.MAX_VALUE
//        }

        if (from.manhattanDistanceTo(to) + round > currentMin) {
            return Int.MAX_VALUE
        }

        val valley = valleys.round(round + 1)
        val i = (possibleMoves
            .asSequence()
            .map { from + it }
            .filterNot { it in valley.blizzards }
            .filter { it in valley }
            .sortedBy { it manhattanDistanceTo to }
            .minOfOrNull { stepsTo(it, to, round + 1, valleys) }
            ?: Int.MAX_VALUE)
        return i.also { cache[round to from] = i }
    }

    private fun parseRow(y: Int, row: String) =
        row
            .asSequence()
            .mapIndexed { x, c -> Point(x to y) to c }
            .filter { it.second in setOf('>', 'v', '<', '^') }
            .map { Blizzard(it.first, parseDirection(it.second)) }

    private fun parseDirection(blizzard: Char) =
        when (blizzard) {
            '>' -> Point(1 to 0)
            'v' -> Point(0 to 1)
            '<' -> Point(-1 to 0)
            '^' -> Point(0 to -1)
            else -> throw IllegalArgumentException("Invalid blizzard: $blizzard")
        }

    private data class Blizzard(val location: Point<Int>, val direction: Point<Int>) {
        context(Valley) fun move() =
            location
                .plus(direction)
                .let { Point((it.x nonNegativeModulo xSize) to (it.y nonNegativeModulo ySize)) }
                .let { Blizzard(it, direction) }
    }

    private data class Valley(
        val blizzards: Map<Point<Int>, List<Blizzard>>,
        val xSize: Int,
        val ySize: Int,
    ) {

        val from = Point(0 to -1)
        val to = Point(xSize - 1 to ySize)

        fun afterNextRound() =
            blizzards
                .values
                .asSequence()
                .flatMap { it.asSequence() }
                .map { it.move() }
                .groupBy { it.location }
                .let { Valley(it, xSize, ySize) }

        operator fun contains(point: Point<Int>): Boolean {
            if (point.x in 0..<xSize && point.y in 0..<ySize) {
                return true
            }

            if (point in setOf(Point(0 to -1), Point(xSize - 1 to ySize))) {
                return true
            }

            return false
        }
    }

    private class Valleys(valley: Valley) {

        private val valleys = mutableListOf(valley)

        fun round(round: Int): Valley {
            require(round <= valleys.size) { "round $round is not yet calculated" }
            if (round == valleys.size) {
                println("Calculating round $round")
                valleys += valleys.last().afterNextRound()
            }

            return valleys[round]
        }

    }

}
