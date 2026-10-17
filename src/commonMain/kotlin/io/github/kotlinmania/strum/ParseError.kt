@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * The [ParseError] enum is a collection of all the possible reasons
 * an enum can fail to parse from a string.
 */
public enum class ParseError {
    VARIANT_NOT_FOUND,
    ;

    override fun toString(): String =
        when (this) {
            VARIANT_NOT_FOUND -> "Matching variant not found"
        }

    /**
     * Display text for this parse error.
     */
    public fun fmt(): String = toString()

    /**
     * Long-form description of the parse error, mirroring the
     * `std::error::Error::description` text in upstream.
     */
    @HiddenFromObjC
    public fun description(): String =
        when (this) {
            VARIANT_NOT_FOUND -> {
                "Unable to find a variant of the given enum matching the string given. Matching " +
                    "can be extended with the Serialize attribute and is case sensitive."
            }
        }
}
