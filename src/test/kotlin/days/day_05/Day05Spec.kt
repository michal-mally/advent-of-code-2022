package days.day_05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day05Spec : FreeSpec({

    "Part 1" {
        Day05_1().solve(lines(5)) shouldBe "TLNGFGMFN"
    }

    "Part 2" {
        Day05_2().solve(lines(5)) shouldBe "FGLQJCMBD"
    }

})
