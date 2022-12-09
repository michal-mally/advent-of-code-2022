package days.day_09

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day09Spec : FreeSpec({

    "Part 1" {
        Day09_1().solve(lines(9)) shouldBe 6376
    }

})
