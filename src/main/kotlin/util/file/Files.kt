package util.file

fun text(day: Int) =
    reader(day).readText()

fun lines(day: Int) =
    reader(day).lineSequence()

private fun reader(day: Int) =
    object {}
        .javaClass
        .getResourceAsStream("/data/day/${day.toString().padStart(2, '0')}.txt")
        ?.bufferedReader()
        ?: throw IllegalArgumentException("No data for day $day")
