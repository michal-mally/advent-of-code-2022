package days.day_03

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day03Spec : FreeSpec({

    "Part 1" {
        Day03_1().solve(lines(3)) shouldBe 8109
    }

    "Part 2" {
        Day03_2().solve(lines(3)) shouldBe 2738
    }

})
