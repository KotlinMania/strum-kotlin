package contract
import io.github.kotlinmania.strum.EnumIterator
import io.github.kotlinmania.strum.IntoEnumIterator
class WrongElement : IntoEnumIterator<String, EnumIterator<Int>> {
    override fun iter(): EnumIterator<Int> = EnumIterator.from(listOf(1))
}
