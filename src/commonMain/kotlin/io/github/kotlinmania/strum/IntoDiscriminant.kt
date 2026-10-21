package io.github.kotlinmania.strum

/** Retrieves the associated discriminant without erasing its type. */
public interface IntoDiscriminant<out D> {
    public fun discriminant(): D
}
