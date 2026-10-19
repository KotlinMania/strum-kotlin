package io.github.kotlinmania.strum

/**
 * Reference conversion interface for static string references.
 *
 * Deprecated in upstream strum 0.22.0 in favor of `IntoStaticStr`.
 */
public interface AsStaticRef {
    public fun asStatic(): String
}
