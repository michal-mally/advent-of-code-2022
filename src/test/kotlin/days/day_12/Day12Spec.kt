package days.day_12

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day12Spec : FreeSpec({

    "Part 1" {
        Day12_1().solve(lines(12)) shouldBe 394
    }

    "Part 2" {
        Day12_2().solve(lines(12)) shouldBe 388
    }

})
