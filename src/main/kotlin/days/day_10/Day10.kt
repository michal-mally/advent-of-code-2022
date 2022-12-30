package days.day_10

import util.sequence.headAndTail

fun xRegisterValues(input: Sequence<String>) =
    buildList {
        fun parseCommand(command: String) =
            command
                .splitToSequence(" ")
                .headAndTail()

        this += 1
        for ((cmd, args) in input.map(::parseCommand)) {
            this += 0
            if (cmd == "addx") {
                this += args.first().toInt()
            }
        }
    }
        .runningReduce(Int::plus)
