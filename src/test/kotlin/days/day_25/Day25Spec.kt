package days.day_25

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day25Spec : FreeSpec({

    "Part 1" {
        Day25_1().solve(lines(25)) shouldBe "4890"
    }

})
