package days.day_14

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day14Spec : FreeSpec({

    "Part 1" {
        Day14_1().solve(lines(14)) shouldBe 665
    }

})
