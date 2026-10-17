@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC
import io.github.kotlinmania.strummacros.helpers.nonEnumError as upstreamNonEnumError

/**
 * Helpers from published strum-macros-kotlin.
 */
@HiddenFromObjC
public object MacroHelpers {
    /** Helper error produced when deriving on non-enum types. */
    public fun nonEnumError(): Any = upstreamNonEnumError()
}
