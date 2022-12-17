package days.day_17

import util.Solver
import util.point.Point

class Day17_2 : Solver<Sequence<Char>, Long> {
    override fun solve(input: Sequence<Char>): Long {
        val jets = sequence {
            val toList = input.toList()
            println(toList.size)
            while (true) {
                yieldAll(toList.filter { it in setOf('<', '>') })
            }
        }.iterator()

        val rocks = sequence {
            while (true) {
                yieldAll(rocks)
            }
        }.iterator()

        val chamber = mutableSetOf<Point<Int>>()
        val heights = mutableListOf(0)
        repeat(10000) {
            chamber
                .map { it.y }
                .distinct()
                .sortedDescending()
                .map { y -> (0..6).map { x -> Point(x to y) } }
                .firstOrNull { chamber.containsAll(it) }
                ?.first()
                ?.y
                ?.let { fullY -> chamber.removeIf { it.y < fullY } }


            val newRockLocation = Point(2 to ((chamber.maxOfOrNull { it.y } ?: -1) + 5))

            var rock = rocks.next() + newRockLocation
            while (true) {
                val afterDrop = rock + Point(0 to -1)
                if (afterDrop.bottomMost() < 0 || afterDrop.segments.intersect(chamber).isNotEmpty()) {
                    chamber += rock.segments
                    break
                }

                rock = afterDrop

                val jet = jets.next()
                val jetEffect = when (jet) {
                    '>' -> Point(1 to 0)
                    '<' -> Point(-1 to 0)
                    else -> error("Invalid jet '$jet'")
                }

                val afterJet = rock + jetEffect
                rock = if (afterJet.leftMost() < 0 || afterJet.rightMost() > 6 || afterJet.segments.intersect(chamber)
                        .isNotEmpty()
                ) {
                    rock
                } else {
                    afterJet
                }
            }

            heights += chamber.maxOf { it.y } + 1

        }

        val heights1 = heights.zipWithNext { a, b -> b - a }
        val (start, length) = x(heights1) ?: error("No pattern")

        val pattern = heights1.drop(start).take(length)
        val l = 1000000000000 - start

        return heights1.take(start).sum() + l / length * pattern.sum() + pattern.take((l % length).toInt()).sum()
    }

    fun x(heights: List<Int>): Pair<Int, Int>? {
        for (start in 0..heights.size / 4) {
            for (length in 10..heights.size / 4) {
                if (heights.subList(start, start + length) == heights.subList(
                        start + length,
                        start + length * 2
                    ) && heights.subList(start, start + length) == heights.subList(
                        start + length * 2,
                        start + length * 3
                    ) && heights.subList(start, start + length) == heights.subList(
                        start + length * 3,
                        start + length * 4
                    )
                ) {
                    return start to length
                }
            }
        }

        return null
    }

}
