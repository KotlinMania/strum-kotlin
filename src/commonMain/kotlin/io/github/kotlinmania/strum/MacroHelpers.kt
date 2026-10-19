package io.github.kotlinmania.strum

import io.github.kotlinmania.strummacros.helpers.nonEnumError as upstreamNonEnumError

/**
 * Helpers from published strum-macros-kotlin.
 */
internal object MacroHelpers {
    /** Helper error produced when deriving on non-enum types. */
    internal fun nonEnumError(): Any = upstreamNonEnumError()
}
