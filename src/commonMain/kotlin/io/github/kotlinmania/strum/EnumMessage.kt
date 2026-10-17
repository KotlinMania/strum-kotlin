@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Associates additional informational messages, documentation, and serializations with an enum variant.
 */
@HiddenFromObjC
public interface EnumMessage {
    public fun getMessage(): String?

    public fun getDetailedMessage(): String?

    /** Get the doc comment associated with a variant if it exists. */
    public fun getDocumentation(): String?

    public fun getSerializations(): List<String>
}
