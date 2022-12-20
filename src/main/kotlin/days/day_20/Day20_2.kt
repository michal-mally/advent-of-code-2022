package days.day_20

import util.Solver
import java.util.*

class Day20_2: Solver<Sequence<String>, Long> {
    override fun solve(input: Sequence<String>): Long {
        val initialList = input
            .mapIndexed { index, number -> index to number.toLong() * 811589153 }
            .toList()
        println(initialList)

        val decryptedList : LinkedList<Pair<Int, Long>> = LinkedList(initialList)
        repeat(10) {
            for (number in initialList) {
//                println(number)
                val indexOf = decryptedList.indexOf(number)
                decryptedList.removeAt(indexOf)
                var newIndex = (indexOf + number.second) % decryptedList.size
                if (newIndex < 0) {
                    newIndex += decryptedList.size
                }
                if (newIndex == 0L) {
                    newIndex = decryptedList.size.toLong()
                }
                decryptedList.add(newIndex.toInt(), number)
//                println(decryptedList)
            }
        }

        return sequence {
            while(true) {
                yieldAll(decryptedList)
            }
        }
            .map { it.second }
            .dropWhile { it != 0L }
            .take(4000)
            .toList()
            .slice(listOf(1000, 2000, 3000))
            .sum()
    }

}
