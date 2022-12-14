package days.day_17

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.chars

class Day17Spec : FreeSpec({

    "Part 1" {
        Day17_1().solve(chars(17)) shouldBe 3153
    }

    "Part2" {
        Day17_2().solve(chars(17)) shouldBe 1553665689155L
    }

})
