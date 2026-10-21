package contract
import io.github.kotlinmania.strum.EnumIterator
import io.github.kotlinmania.strum.EnumIteratorContract
class MissingReverse : EnumIteratorContract<Int, MissingReverse> {
    private val delegate = EnumIterator.from(listOf(1))
    override fun clone(): MissingReverse = MissingReverse()
    override fun hasNext(): Boolean = delegate.hasNext()
    override fun next(): Int = delegate.next()
    override fun nextOrNull(): Int? = delegate.nextOrNull()
    override fun nth(index: ULong): Int? = delegate.nth(index)
    override fun nthBack(index: ULong): Int? = delegate.nthBack(index)
    override fun len(): ULong = delegate.len()
    override fun sizeHint(): Pair<ULong, ULong> = delegate.sizeHint()
}
