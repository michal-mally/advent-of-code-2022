package days.day_23

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day23Spec : FreeSpec({

    "Part 1" {
        Day23_1().solve(lines(23)) shouldBe 3762
    }

    "Part 2" {
        Day23_2().solve(lines(23)) shouldBe 997
    }

})
