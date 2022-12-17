package days.day_17

import util.Solver
import util.point.Point
import util.point.plus

data class Rock(val segments: Set<Point<Int>>) {

    init {
        require(segments.isNotEmpty()) { "No segments" }
    }

    operator fun plus(point: Point<Int>) =
        Rock(segments.map { it + point }.toSet())

    fun leftMost() =
        segments.minOf { it.x }

    fun rightMost() =
        segments.maxOf { it.x }

    fun bottomMost() =
        segments.minOf { it.y }

}

private fun String.toRock(): Rock =
    lines()
        .reversed()
        .flatMapIndexed { y, line -> line.mapIndexedNotNull { x, char -> if (char == '#') Point(x to y) else null } }
        .toSet()
        .let(::Rock)

val rocks =
    listOf(
        """
        ####
        """,

        """
        .#.
        ###
        .#.
        """,

        """
        ..#
        ..#
        ### 
        """,

        """
        #
        #
        #
        #
        """,

        """
        ##
        ##
        """,
    )
        .map(String::trimIndent)
        .map(String::toRock)

class Day17_1 : Solver<Sequence<Char>, Int> {
    override fun solve(input: Sequence<Char>): Int {
        val jets = sequence {
            while (true) {
                yieldAll(input.filter { it in setOf('<', '>') })
            }
        }.iterator()

        val rocks = sequence {
            while (true) {
                yieldAll(rocks)
            }
        }.iterator()

        val chamber = mutableSetOf<Point<Int>>()
        repeat(2022) {
            val newRockLocation = Point(2 to ((chamber.maxOfOrNull { it.y } ?: -1) + 5))

            var rock = rocks.next() + newRockLocation
            while(true) {
                val afterDrop = rock + Point(0 to -1)
                if (afterDrop.bottomMost() < 0 || afterDrop.segments.intersect(chamber).isNotEmpty()) {
                    println(rock.segments)
                    chamber += rock.segments
                    break
                }

                rock = afterDrop

                val jet = jets.next()
                println(jet)
                val jetEffect = when (jet) {
                    '>' -> Point(1 to 0)
                    '<' -> Point(-1 to 0)
                    else -> error("Invalid jet '$jet'")
                }

                val afterJet = rock + jetEffect
                rock = if (afterJet.leftMost() < 0 || afterJet.rightMost() > 6 || afterJet.segments.intersect(chamber).isNotEmpty()) {
                    rock
                } else {
                    afterJet
                }
            }
        }

        return chamber.maxOf { it.y } + 1
    }
}
