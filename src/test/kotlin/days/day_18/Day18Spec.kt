package days.day_18

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day18Spec : FreeSpec({

    "Part 1" {
        Day18_1().solve(lines(18)) shouldBe 3586
    }

})
