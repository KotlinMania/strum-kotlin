@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Metadata contract providing variant count, names, and current variant name.
 */
@HiddenFromObjC
public interface VariantMetadata {
    public val variantCount: Int
    public val variantNames: List<String>

    public fun variantName(): String
}
