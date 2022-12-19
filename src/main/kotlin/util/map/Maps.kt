package util.map

fun <K> MutableMap<K, Int>.addIntToValue(key: K, value: Int) {
    this[key] = getValue(key) + value
}

fun <K> MutableMap<K, Int>.subtractIntFromValue(key: K, value: Int) {
    this[key] = getValue(key) - value
}

fun <K> Map<K, Int>.addValues(other: Map<K, Int>): Map<K, Int> =
    this
        .toMutableMap()
        .apply { other.forEach { (key, value) -> addIntToValue(key, value) } }

fun <K> Map<K, Int>.minusValues(other: Map<K, Int>): Map<K, Int> =
    this
        .toMutableMap()
        .apply { other.forEach { (key, value) -> addIntToValue(key, -value) } }
