package days.day_22

import days.day_22.Square.Empty
import days.day_22.Square.Wall

enum class Square { Wall, Empty }

fun Square(square: Char) =
    when (square) {
        ' ' -> null
        '#' -> Wall
        '.' -> Empty
        else -> throw IllegalArgumentException("Invalid square: $square")
    }
