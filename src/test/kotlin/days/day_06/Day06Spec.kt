package days.day_06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.chars

class Day06Spec : FreeSpec({

    "Part 1" {
        Day06_1().solve(chars(6)) shouldBe 1912
    }

    "Part 2" {
        Day06_2().solve(chars(6)) shouldBe 2122
    }

})
