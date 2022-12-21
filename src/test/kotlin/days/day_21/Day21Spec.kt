package days.day_21

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day21Spec : FreeSpec({

    "Part 1" {
        Day21_1().solve(lines(21)) shouldBe 152
    }

    "Part 2" {
        Day21_2().solve(lines(21)) shouldBe 3403989691757L
    }

})
