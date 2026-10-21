package io.github.kotlinmania.strum

/** Designates that a value supplies a typed, cloneable, double-ended enum iterator. */
public interface IntoEnumIterator<E : Any, I : EnumIteratorContract<E, I>> {
    public fun iter(): I
}

/** Supplies an iterator whose elements have the same type as the value. */
public interface VariantIterator<E, I : Iterator<E>> {
    public fun iter(): I
}
