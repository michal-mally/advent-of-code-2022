package days.day_20

import util.Solver
import java.util.*

class Day20_1: Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val initialList = input
            .mapIndexed { index, number -> index to number.toInt() }
            .toList()

        val decryptedList : LinkedList<Pair<Int, Int>> = LinkedList(initialList)
        for (number in initialList) {
//            println(number)
            val indexOf = decryptedList.indexOf(number)
            decryptedList.removeAt(indexOf)
            val newIndex = (100 * decryptedList.size + indexOf + number.second) % decryptedList.size
            decryptedList.add(if (newIndex == 0) decryptedList.size else newIndex, number)
//            println(decryptedList)
        }

        return sequence {
            while(true) {
                yieldAll(decryptedList)
            }
        }
            .map { it.second }
            .dropWhile { it != 0 }
            .take(4000)
            .toList()
            .slice(listOf(1000, 2000, 3000))
            .sum()
    }

}
