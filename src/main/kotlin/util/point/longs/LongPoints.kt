package util.point.longs

import util.point.Point
import kotlin.math.abs

infix fun Point<Long>.manhattanDistanceTo(other: Point<Long>): Long =
    abs(x - other.x) + abs(y - other.y)
