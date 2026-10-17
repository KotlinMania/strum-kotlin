@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Designates that an enum can be iterated over.
 */
@HiddenFromObjC
public interface IntoEnumIterator<T> {
    public fun iter(): Iterator<T>
}

/**
 * Iterator interface over enum variants.
 */
@HiddenFromObjC
public interface VariantIterator<T> {
    public fun iter(): Iterator<T>
}
