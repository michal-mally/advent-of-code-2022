package days.day_10

fun xRegisterValues(input: Sequence<String>) =
    buildList {
        add(1)
        for (cmd in input.map { it.split(" ") }) {
            add(0)
            if (cmd[0] == "addx") {
                add(cmd[1].toInt())
            }
        }
    }
        .runningReduce(Int::plus)
