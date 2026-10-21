package consumer

import io.github.kotlinmania.strum.AsStaticRef
import io.github.kotlinmania.strum.EnumIterator
import io.github.kotlinmania.strum.IntoDiscriminant
import io.github.kotlinmania.strum.IntoEnumIterator
import io.github.kotlinmania.strum.ParseError
import io.github.kotlinmania.strum.VariantArray
import io.github.kotlinmania.strum.VariantIterator
import io.github.kotlinmania.strum.fromString

public enum class ConsumerColor : IntoEnumIterator<ConsumerColor, EnumIterator<ConsumerColor>>,
    VariantIterator<ConsumerColor, EnumIterator<ConsumerColor>>, VariantArray<ConsumerColor>,
    IntoDiscriminant<Int>, AsStaticRef<ConsumerColor> {
    Red,
    Blue,
    ;

    override fun iter(): EnumIterator<ConsumerColor> = EnumIterator.from(entries)

    override val variants: List<ConsumerColor> get() = entries

    override fun discriminant(): Int = ordinal

    override fun asStatic(): ConsumerColor = this
}

public fun parseColor(value: String): ConsumerColor = fromString(value)

public fun parseErrorMessage(): String = ParseError.VARIANT_NOT_FOUND.fmt()
