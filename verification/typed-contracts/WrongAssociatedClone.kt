package contract
import io.github.kotlinmania.strum.EnumIterator
import io.github.kotlinmania.strum.EnumIteratorContract
import io.github.kotlinmania.strum.IntoEnumIterator
class ForeignClone : EnumIteratorContract<Int, EnumIterator<Int>> by EnumIterator.from(listOf(1))
class WrongAssociatedClone : IntoEnumIterator<Int, ForeignClone> {
    override fun iter(): ForeignClone = ForeignClone()
}
