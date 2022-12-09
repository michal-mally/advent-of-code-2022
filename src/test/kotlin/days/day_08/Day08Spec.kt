package days.day_08

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day08Spec : FreeSpec({

    "Part 1" {
        Day08_1().solve(lines(8)) shouldBe 1717
    }

    "Part 2" {
        Day08_2().solve(lines(8)) shouldBe 1717
    }

})
