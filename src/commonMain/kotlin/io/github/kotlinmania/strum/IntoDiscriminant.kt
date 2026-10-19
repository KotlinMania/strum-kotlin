package io.github.kotlinmania.strum

/**
 * Interface retrieving the discriminant associated with an enum variant.
 */
// generic-free: discriminant is represented as variant name String across KMP and Swift Export.
public interface IntoDiscriminant {
    public fun discriminant(): String
}
