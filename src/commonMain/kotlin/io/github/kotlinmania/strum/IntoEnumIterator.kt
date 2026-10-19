package io.github.kotlinmania.strum

/**
 * Designates that an enum can be iterated over.
 */
public interface IntoEnumIterator {
    public fun iter(): List<Any>
}

/**
 * Iterator interface over enum variants.
 */
public interface VariantIterator {
    public fun iter(): List<Any>
}
