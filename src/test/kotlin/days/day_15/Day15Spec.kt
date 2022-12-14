package days.day_15

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day15Spec : FreeSpec({

    "Part 1" {
        Day15_1().solve(lines(15)) shouldBe 4883971L
    }

    "Part 2" {
        Day15_2().solve(lines(15)) shouldBe 12691026767556L
    }

})
