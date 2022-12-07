package days.day_07

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day07Spec : FreeSpec({

    "Part 1" {
        Day07_1().solve(lines(7)) shouldBe 1447046
    }

    "Part 2" {
        Day07_2().solve(lines(7)) shouldBe 578710
    }

})
