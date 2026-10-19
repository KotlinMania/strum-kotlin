package io.github.kotlinmania.strum

/**
 * Accessor interface for key-value properties associated with enum variants.
 */
public interface EnumProperty {
    public fun getStr(prop: String): String?

    public fun getInt(prop: String): Long?

    public fun getBool(prop: String): Boolean?
}
