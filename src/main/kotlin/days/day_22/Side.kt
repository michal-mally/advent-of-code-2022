package days.day_22

import util.array.TwoDimArray
import util.point.Point

data class Side(val location: Point<Int>, val values: TwoDimArray<Square>) {
    val size = values.xCount
}
