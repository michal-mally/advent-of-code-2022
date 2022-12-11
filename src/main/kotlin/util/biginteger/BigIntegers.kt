package util.biginteger

import java.math.BigInteger
import java.math.BigInteger.ZERO

infix fun BigInteger.isDivisibleBy(other: BigInteger): Boolean =
    this % other == ZERO
