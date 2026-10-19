package io.github.kotlinmania.strum

/**
 * Associates additional informational messages, documentation, and serializations with an enum variant.
 */
public interface EnumMessage {
    public fun getMessage(): String?

    public fun getDetailedMessage(): String?

    /** Get the doc comment associated with a variant if it exists. */
    public fun getDocumentation(): String?

    public fun getSerializations(): List<String>
}
