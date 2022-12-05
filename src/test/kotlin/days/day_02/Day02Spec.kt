package days.day_02

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day02Spec : FreeSpec({

    "Part 1" {
        Day02_1().solve(lines(2)) shouldBe 8890
    }

    "Part 2" {
        Day02_2().solve(lines(2)) shouldBe 10238
    }

})
