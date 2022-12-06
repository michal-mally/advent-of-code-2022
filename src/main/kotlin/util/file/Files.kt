package util.file

fun lines(day: Int) =
    inputStream(day)
        .bufferedReader()
        .lineSequence()

fun chars(day: Int) =
    sequence {
        inputStream(day)
            .reader()
            .use { reader ->
                while (true) {
                    val char = reader.read()
                    if (char == -1) break
                    yield(char.toChar())
                }
            }

    }

private fun inputStream(day: Int) =
    object {}
        .javaClass
        .getResourceAsStream("/data/day/${day.toString().padStart(2, '0')}.txt")
        ?: throw IllegalArgumentException("No data for day $day")
