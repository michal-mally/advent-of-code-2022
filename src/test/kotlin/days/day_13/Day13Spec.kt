package days.day_13

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day13Spec : FreeSpec({

    "Part 1" {
        Day13_1().solve(lines(13)) shouldBe 6568
    }

    "Part 2" {
        Day13_2().solve(lines(13)) shouldBe 19493
    }

})
