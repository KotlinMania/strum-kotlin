@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Reference conversion interface for static references.
 */
@Deprecated(
    "please use IntoStaticStr derive instead",
    level = DeprecationLevel.WARNING,
)
@HiddenFromObjC
public interface AsStaticRef<T> {
    public fun asStatic(): T
}
