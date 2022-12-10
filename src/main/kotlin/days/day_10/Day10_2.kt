package days.day_10

import util.Solver
import kotlin.math.abs

private const val LINE_LENGTH = 40
private const val LINES = 6

class Day10_2 : Solver<Sequence<String>, String> {

    override fun solve(input: Sequence<String>) =
        buildString {
            val xRegisterValues = xRegisterValues(input)
            repeat(LINES) { line ->
                for (cycle in 0..<LINE_LENGTH) {
                    val spritePosition = xRegisterValues[line * LINE_LENGTH + cycle]
                    append(
                        if (abs(cycle - spritePosition) <= 1) "#" else "."
                    )
                }
                appendLine()
            }
        }

}
