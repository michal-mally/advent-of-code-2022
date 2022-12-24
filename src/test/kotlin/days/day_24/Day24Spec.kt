package days.day_24

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day24Spec : FreeSpec({

    "Part 1" {
        Day24_1().solve(lines(24)) shouldBe 3762
    }

})
