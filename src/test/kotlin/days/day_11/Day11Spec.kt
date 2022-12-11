package days.day_11

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day11Spec : FreeSpec({

    "Part 1" {
        Day11_1().solve(lines(11)) shouldBe 14620
    }

})
