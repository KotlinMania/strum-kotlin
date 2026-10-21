package io.github.kotlinmania.strum

/** Retrieves the immutable sequence of variants with their concrete type. */
public interface VariantArray<out E> {
    public val variants: List<E>
}
