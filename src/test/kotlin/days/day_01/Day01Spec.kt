package days.day_01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import util.file.lines

class Day01Spec : FreeSpec({

    "Part 1" {
        Day01_1().solve(lines(1)) shouldBe 66487
    }

    "Part 2" {
        Day01_2().solve(lines(1)) shouldBe 197301
    }

    "Part 2p" {
        Day01_2p().solve(lines(1)) shouldBe 197301
    }

    "Part 2p2" {
        Day01_2p2().solve(lines(1)) shouldBe 197301
    }

})
