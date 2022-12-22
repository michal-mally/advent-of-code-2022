package days.day_22

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day22Spec : FreeSpec({

    "Part 1" {
        Day22_1().solve(lines(22)) shouldBe 1484
    }

})
