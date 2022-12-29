package days.day_22

import days.day_22.Day22_1.Instruction.*
import days.day_22.Day22_1.Square.Empty
import days.day_22.Day22_1.Square.Wall
import util.Solver
import util.number.nonNegativeModulo
import util.point.Point
import util.sequence.splitBy

class Day22_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val (map, instructionsRaw) = input
            .splitBy { it.isBlank() }
            .toList()

        val squares = map
            .flatMapIndexed { y, row -> parseRow(y, row) }
            .toMap()

        var position = squares
            .filterKeys { it.y == 0 }
            .filterValues { it == Empty }
            .map { it.key }
            .minBy { it.x }
        println(position)
        var direction = Point(1 to 0)
        val visited = mutableMapOf(position to '>')

        val instructions =
            """\d+|[LR]"""
                .toRegex()
                .findAll(instructionsRaw.first())
                .map { it.value }
                .map { parseInstruction(it) }
                .onEach { println(it) }
                .toList()

        var index = 0
        for (instruction in instructions) {
            check(squares.getValue(position) == Empty)
            print("${direction} ${position} ")
            direction = when (instruction) {
                is Left -> direction.rotateLeft()
                is Right -> direction.rotateRight()
                else -> direction
            }

            if (instruction is Forward) {
                repeat(instruction.steps) {
                    val nextInDirection = nextInDirection(squares, position, direction)
                    val square = squares.getValue(nextInDirection)
                    if (square == Wall) {
                        return@repeat
                    }

                    position = nextInDirection
                    visited[position] = when (direction) {
                        Point(1 to 0) -> '>'
                        Point(0 to 1) -> 'v'
                        Point(-1 to 0) -> '<'
                        Point(0 to -1) -> '^'
                        else -> throw IllegalArgumentException("Invalid direction: $direction")
                    }
                }
            }

            println("${instruction} ${direction} ${position}")
        }

        val directionScore = when (direction) {
            Point(1 to 0) -> 0
            Point(0 to 1) -> 1
            Point(-1 to 0) -> 2
            Point(0 to -1) -> 3
            else -> throw IllegalArgumentException("Invalid direction: $direction")
        }

        println(directionScore)
        println(position)

        val maxX = squares
            .keys
            .maxOf { it.x }
        val maxY = squares
            .keys
            .maxOf { it.y }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (Point(x to y) in visited) {
                    print(visited.getValue(Point(x to y)))
                } else if (squares[Point(x to y)] == Wall) {
                    print("#")
                } else if (squares[Point(x to y)] == Empty) {
                    print(".")
                } else {
                    print(" ")
                }
            }

            println()
        }

        return 1000 * (position.y + 1) + 4 * (position.x + 1) + directionScore
    }

    private fun nextInDirection(
        squares: Map<Point<Int>, Square>,
        position: Point<Int>,
        direction: Point<Int>
    ): Point<Int> {
        val maxX = squares
            .keys
            .maxOf { it.x } + 1
        val maxY = squares
            .keys
            .maxOf { it.y } + 1
        var nextPosition = position
        do {
            nextPosition =
                Point(((nextPosition.x + direction.x) nonNegativeModulo maxX) to ((nextPosition.y + direction.y) nonNegativeModulo maxY))
            check(nextPosition.x >= 0)
            check(nextPosition.y >= 0)
        } while (squares[nextPosition] == null)

        return nextPosition
    }

    private fun Point<Int>.rotateLeft() =
        when (this) {
            Point(1 to 0) -> Point(0 to -1) // East -> North
            Point(0 to 1) -> Point(1 to 0) // South -> East
            Point(-1 to 0) -> Point(0 to 1) // West -> South
            Point(0 to -1) -> Point(-1 to 0) // North -> West
            else -> throw IllegalArgumentException("Invalid direction: $this")
        }

    private fun Point<Int>.rotateRight() =
        when (this) {
            Point(1 to 0) -> Point(0 to 1) // East -> South
            Point(0 to 1) -> Point(-1 to 0) // South -> West
            Point(-1 to 0) -> Point(0 to -1) // West -> North
            Point(0 to -1) -> Point(1 to 0) // North -> East
            else -> throw IllegalArgumentException("Invalid direction: $this")
        }

    private fun parseInstruction(it: String) =
        when (it) {
            "L" -> Left
            "R" -> Right
            else -> Forward(it.toInt())
        }

    sealed interface Instruction {
        object Left : Instruction
        object Right : Instruction
        data class Forward(val steps: Int) : Instruction
    }

    private fun parseRow(y: Int, row: String) =
        row
            .asSequence()
            .mapIndexedNotNull { x, square -> parseSquare(x, y, square) }

    private fun parseSquare(x: Int, y: Int, square: Char) =
        when (square) {
            ' ' -> null
            '#' -> Point(x to y) to Wall
            '.' -> Point(x to y) to Empty
            else -> throw IllegalArgumentException("Invalid square: $square")
        }

    private enum class Square {
        Wall,
        Empty
    }

}
