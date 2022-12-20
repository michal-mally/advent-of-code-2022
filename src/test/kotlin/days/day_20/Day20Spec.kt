package days.day_20

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day20Spec : FreeSpec({

    "Part 1" {
        Day20_1().solve(lines(20)) shouldBe 1192
    }

})
