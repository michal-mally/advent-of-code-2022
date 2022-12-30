package days.day_04

import util.pair.mapFirstAndSecond
import util.pair.toPair
import util.pair.toRange

fun transformLine(line: String) =
    line
        .split(",")
        .toPair()
        .mapFirstAndSecond(::transformToRange)

private fun transformToRange(range: String) =
    range
        .split("-")
        .toPair()
        .mapFirstAndSecond(String::toInt)
        .toRange()
