package io.github.kotlinmania.strum

/**
 * A reference-to-reference conversion preserving the referenced type.
 *
 * Deprecated in upstream strum 0.22.0 in favor of `IntoStaticStr`.
 */
public interface AsStaticRef<out T> {
    public fun asStatic(): T
}
