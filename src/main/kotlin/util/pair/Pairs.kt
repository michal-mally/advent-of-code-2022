package util.pair

fun <A, B, C, D> Pair<A, B>.map(f: (A, B) -> Pair<C, D>): Pair<C, D> =
    f(first, second)

fun <A, B> Pair<A, A>.mapLeftAndRight(f: (A) -> B): Pair<B, B> =
    f(first) to f(second)

fun <A, B, C> Pair<A, B>.mapLeft(f: (A, B) -> C): Pair<C, B> =
    f(first, second) to second

fun <A, B, C> Pair<A, B>.mapRight(f: (A, B) -> C): Pair<A, C> =
    first to f(first, second)

fun <A> Pair<A, A>.toSequence(): Sequence<A> =
    sequenceOf(first, second)

fun <A> Collection<A>.toPair(): Pair<A, A> {
    require(size == 2) { "Collection must have exactly 2 elements" }
    return first() to last()
}
