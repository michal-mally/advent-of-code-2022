package util.map

fun <K> MutableMap<K, Int>.add(key: K, value: Int) {
    this[key] = getValue(key) + value
}
