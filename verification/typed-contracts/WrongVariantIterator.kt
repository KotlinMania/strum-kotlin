package contract
import io.github.kotlinmania.strum.VariantIterator
class WrongVariantIterator : VariantIterator<String, Iterator<Int>> {
    override fun iter(): Iterator<Int> = listOf(1).iterator()
}
