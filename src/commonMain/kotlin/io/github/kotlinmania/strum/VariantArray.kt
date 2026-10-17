@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Interface retrieving an array or list containing all variants of an enum.
 */
@HiddenFromObjC
public interface VariantArray<T> {
    public val variants: List<T>
}
