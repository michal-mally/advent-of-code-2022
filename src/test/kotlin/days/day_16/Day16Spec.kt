package days.day_16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day16Spec : FreeSpec({

    "Part 1" {
        Day16_1().solve(lines(16)) shouldBe 1673
    }

})
