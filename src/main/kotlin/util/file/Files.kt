package util.file

fun text(day: Int) =
    object {}
        .javaClass
        .getResourceAsStream("/data/day/${day.toString().padStart(2, '0')}.txt")!!
        .bufferedReader()
        .readText()
