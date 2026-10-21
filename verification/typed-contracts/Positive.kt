package contract

import io.github.kotlinmania.strum.AsStaticRef
import io.github.kotlinmania.strum.EnumIterator
import io.github.kotlinmania.strum.EnumIteratorContract
import io.github.kotlinmania.strum.IntoDiscriminant
import io.github.kotlinmania.strum.IntoEnumIterator
import io.github.kotlinmania.strum.VariantArray
import io.github.kotlinmania.strum.VariantIterator

enum class Entry : IntoEnumIterator<Entry, EnumIterator<Entry>>, VariantIterator<Entry, EnumIterator<Entry>>,
    VariantArray<Entry>, IntoDiscriminant<Int>, AsStaticRef<Entry> {
    FIRST, SECOND;
    override fun iter(): EnumIterator<Entry> = EnumIterator.from(entries)
    override val variants: List<Entry> get() = entries
    override fun discriminant(): Int = ordinal
    override fun asStatic(): Entry = this
}
fun <E : Any, I : EnumIteratorContract<E, I>> typedClone(provider: IntoEnumIterator<E, I>): I = provider.iter().clone()
val cloned: EnumIterator<Entry> = typedClone(Entry.FIRST)
val discriminant: Int = Entry.FIRST.discriminant()
val reference: Entry = Entry.FIRST.asStatic()
val variants: List<Entry> = Entry.FIRST.variants
val remaining: ULong = cloned.len()
