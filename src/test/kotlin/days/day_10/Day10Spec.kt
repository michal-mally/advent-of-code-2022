package days.day_10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day10Spec : FreeSpec({

    "Part 1" {
        Day10_1().solve(lines(10)) shouldBe 14620
    }

    "Part 2" {
        Day10_2().solve(lines(10)) shouldBe """
            ###....##.####.###..#..#.###..####.#..#.
            #..#....#.#....#..#.#..#.#..#.#....#..#.
            ###.....#.###..#..#.####.#..#.###..#..#.
            #..#....#.#....###..#..#.###..#....#..#.
            #..#.#..#.#....#.#..#..#.#.#..#....#..#.
            ###...##..#....#..#.#..#.#..#.#.....##..
        """.trimIndent()
    }

})
