package days.day_19

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day19Spec : FreeSpec({

    "Part 1" {
        Day19_1().solve(lines(19)) shouldBe 1192
    }

    "Part 2" {
        Day19_2().solve(lines(19)) shouldBe 14725
    }

})
