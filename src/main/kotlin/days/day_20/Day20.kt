package days.day_20

import util.number.nonNegativeModulo
import java.util.*

fun solve(input: Sequence<Long>, repeat: Int = 1): Long {
    val initialListIndexed = input
        .mapIndexed { index, number -> index to number }
        .toList()

    return LinkedList(initialListIndexed)
        .apply {
            repeat(repeat) {
                for (number in initialListIndexed) {
                    val indexOf = indexOf(number)
                    removeAt(indexOf)
                    add((indexOf + number.second) nonNegativeModulo size, number)
                }
            }
        }
        .let { decryptedList ->
            sequence {
                yieldAll(decryptedList.dropWhile { it.second != 0L })
                while (true) {
                    yieldAll(decryptedList)
                }
            }
        }
        .map { it.second }
        .filterIndexed { index, _ -> index % 1000 == 0 }
        .drop(1)
        .take(3)
        .sum()
}
