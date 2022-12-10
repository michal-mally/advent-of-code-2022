package days.day_10

import util.sequence.headAndTail

fun xRegisterValues(input: Sequence<String>) =
    buildList {
        fun parseCommand(command: String) = command.splitToSequence(" ").headAndTail()

        add(1)
        for ((cmd, args) in input.map(::parseCommand)) {
            add(0)
            if (cmd == "addx") {
                add(args.first().toInt())
            }
        }
    }
        .runningReduce(Int::plus)
