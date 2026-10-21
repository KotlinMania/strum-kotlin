package io.github.kotlinmania.strum

/** A typed iterator with independent clones, reverse traversal and fused exhaustion. */
public interface EnumIteratorContract<E : Any, I : EnumIteratorContract<E, I>> : Iterator<E> {
    public fun clone(): I

    public fun nextOrNull(): E?

    public fun nextBack(): E?

    public fun nth(index: ULong): E?

    public fun nth(index: Int): E? {
        require(index >= 0)
        return nth(index.toULong())
    }

    public fun nthBack(index: ULong): E?

    public fun nthBack(index: Int): E? {
        require(index >= 0)
        return nthBack(index.toULong())
    }

    public fun len(): ULong

    public fun sizeHint(): Pair<ULong, ULong>
}

/** Traverses variants constructed by their index from either end. */
public class EnumIterator<E : Any>(
    private val variantCount: Int,
    private val variantAt: (Int) -> E,
) : EnumIteratorContract<E, EnumIterator<E>> {
    private var front: Int = 0
    private var back: Int = variantCount

    init {
        require(variantCount >= 0)
    }

    private constructor(
        variantCount: Int,
        variantAt: (Int) -> E,
        front: Int,
        back: Int,
    ) : this(variantCount, variantAt) {
        this.front = front
        this.back = back
    }

    override fun hasNext(): Boolean = front < back

    override fun next(): E = nextOrNull() ?: throw NoSuchElementException()

    override fun nextOrNull(): E? = nth(0uL)

    override fun nextBack(): E? {
        if (!hasNext()) return null
        back--
        return variantAt(back)
    }

    override fun nth(index: ULong): E? {
        if (index >= len()) {
            front = back
            return null
        }
        front += index.toInt()
        return variantAt(front++)
    }

    override fun nthBack(index: ULong): E? {
        var remaining = index
        while (remaining > 0uL) {
            if (nextBack() == null) return null
            remaining--
        }
        return nextBack()
    }

    override fun len(): ULong = (back - front).toULong()

    override fun sizeHint(): Pair<ULong, ULong> = len() to len()

    override fun clone(): EnumIterator<E> = EnumIterator(variantCount, variantAt, front, back)

    public companion object {
        /** Traverses a snapshot whose elements keep their original type and identity. */
        public fun <E : Any> from(values: List<E>): EnumIterator<E> {
            val snapshot = values.toList()
            return EnumIterator(snapshot.size) { snapshot[it] }
        }
    }
}
