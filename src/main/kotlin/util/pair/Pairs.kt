package util.pair

fun <A, B> Pair<A, A>.mapBoth(f: (A) -> B): Pair<B, B> =
    f(first) to f(second)

fun <A, B, C> Pair<A, B>.mapLeft(f: (A, B) -> C): Pair<C, B> =
    f(first, second) to second

fun <A, B, C> Pair<A, B>.mapRight(f: (A, B) -> C): Pair<A, C> =
    first to f(first, second)
