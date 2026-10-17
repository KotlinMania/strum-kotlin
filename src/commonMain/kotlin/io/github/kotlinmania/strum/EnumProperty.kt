@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Accessor interface for key-value properties associated with enum variants.
 */
@HiddenFromObjC
public interface EnumProperty {
    public fun getStr(prop: String): String?

    public fun getInt(prop: String): Long?

    public fun getBool(prop: String): Boolean?
}
