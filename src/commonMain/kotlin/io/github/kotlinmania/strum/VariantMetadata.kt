package io.github.kotlinmania.strum

/**
 * Metadata contract providing variant count, names, and current variant name.
 */
public interface VariantMetadata {
    public val variantCount: Int
    public val variantNames: List<String>

    public fun variantName(): String
}
