package days.day_04

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day04Spec : FreeSpec({

    "Part 1" {
        Day04_1().solve(lines(4)) shouldBe 441
    }

    "Part 2" {
        Day04_2().solve(lines(4)) shouldBe 861
    }

})
