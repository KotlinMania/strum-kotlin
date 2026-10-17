@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Interface retrieving the discriminant associated with an enum variant.
 */
@HiddenFromObjC
public interface IntoDiscriminant<D> {
    public fun discriminant(): D
}
