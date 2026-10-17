@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Interface capturing the total number of variants in an enum.
 */
@HiddenFromObjC
public interface EnumCount {
    public val count: Int
}
