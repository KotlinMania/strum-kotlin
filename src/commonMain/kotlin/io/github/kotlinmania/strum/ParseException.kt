@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Exception thrown when parsing an enum from a string fails.
 *
 * @property error The underlying [ParseError] describing the failure reason.
 */
public class ParseException(
    public val error: ParseError,
    message: String = error.toString(),
) : RuntimeException(message) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ParseException) return false
        return error == other.error && message == other.message
    }

    override fun hashCode(): Int = 31 * error.hashCode() + (message?.hashCode() ?: 0)

    override fun toString(): String = "ParseException(error=$error, message=$message)"
}

/**
 * Converts a [ParseError] into a [ParseException].
 */
@HiddenFromObjC
public fun ParseError.asException(): ParseException = ParseException(this)
