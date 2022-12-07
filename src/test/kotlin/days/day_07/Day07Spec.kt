package days.day_07

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day07Spec : FreeSpec({

    "Part 1" {
        Day07_1().solve(lines(7)) shouldBe 1912
    }

})
