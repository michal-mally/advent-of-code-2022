package days.day_01

import util.file.lines
import java.util.*

fun main() {
    var topElves = TreeSet<Int>()

    var elfSum = 0
    for (number in lines(1).map(String::toIntOrNull)) {
        if (number != null) {
            elfSum += number
        } else {
            topElves += elfSum
            elfSum = 0
            if (topElves.size > 3) {
                topElves.removeFirst()
            }
        }
    }

    println(topElves.sum())
}

fun <T> MutableCollection<T>.removeFirst() =
    remove(first())
