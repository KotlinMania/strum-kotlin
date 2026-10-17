@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Interface retrieving the list of variant names of an enum.
 */
@HiddenFromObjC
public interface VariantNames {
    /** Names of the variants of this enum */
    public val variantNames: List<String>
}
