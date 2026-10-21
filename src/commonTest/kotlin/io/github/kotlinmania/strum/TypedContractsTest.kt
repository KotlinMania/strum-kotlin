package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertNull
import kotlin.test.assertSame

class TypedContractsTest {
    @Test
    fun mixedDirectionReportsExactRemainingLength() {
        val iterator = EnumIterator.from(listOf(10, 20, 30))
        assertEquals(3uL to 3uL, iterator.sizeHint())
        assertEquals(10, iterator.next())
        assertEquals(2uL, iterator.len())
        assertEquals(30, iterator.nextBack())
        assertEquals(1uL to 1uL, iterator.sizeHint())
        assertEquals(20, iterator.nextOrNull())
        assertEquals(0uL, iterator.len())
        assertNull(iterator.nextOrNull())
        assertNull(iterator.nextBack())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun clonesPreserveBothCursorsIndependently() {
        val iterator = EnumIterator.from(listOf(10, 20, 30, 40, 50))
        assertEquals(10, iterator.next())
        assertEquals(50, iterator.nextBack())
        val copy: EnumIterator<Int> = iterator.clone()
        assertEquals(20, iterator.next())
        assertEquals(20, copy.next())
        assertEquals(40, iterator.nextBack())
        assertEquals(1uL, iterator.len())
        assertEquals(2uL, copy.len())
        assertEquals(30, copy.next())
        assertEquals(40, copy.nextBack())
        assertEquals(30, iterator.next())
        assertFalse(copy.hasNext())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun emptyIteratorNeverInvokesFactoryAndStaysExhausted() {
        var calls = 0
        val iterator =
            EnumIterator(0) {
                calls++
                calls
            }
        repeat(100) {
            assertNull(iterator.nextOrNull())
            assertNull(iterator.nextBack())
            assertFalse(iterator.hasNext())
            assertEquals(0uL to 0uL, iterator.sizeHint())
        }
        assertFailsWith<NoSuchElementException> { iterator.next() }
        assertEquals(0, calls)
    }

    @Test
    fun skippingFromBothEndsPreservesFactoryEvaluation() {
        val visited = mutableListOf<Int>()
        val iterator =
            EnumIterator(5) {
                visited.add(it)
                it
            }
        assertEquals(2, iterator.nth(2))
        assertEquals(3, iterator.nthBack(1))
        assertEquals(listOf(2, 4, 3), visited)
        assertEquals(0uL, iterator.len())
        assertNull(iterator.nextOrNull())
    }

    @Test
    fun largeSkipsFreezeExhaustedCursorsWithoutOverflow() {
        val forward = EnumIterator.from(listOf(1, 2, 3))
        val reverse = forward.clone()
        repeat(100) {
            assertNull(forward.nth(ULong.MAX_VALUE))
            assertNull(reverse.nthBack(ULong.MAX_VALUE))
            assertNull(forward.nextBack())
            assertNull(reverse.nextOrNull())
            assertEquals(0uL, forward.len())
            assertEquals(0uL, reverse.len())
        }
    }

    @Test
    fun invalidCountsAndSkipIndicesAreRejected() {
        assertFailsWith<IllegalArgumentException> { EnumIterator(-1) { it } }
        val iterator = EnumIterator.from(listOf(1, 2))
        assertFailsWith<IllegalArgumentException> { iterator.nth(-1) }
        assertFailsWith<IllegalArgumentException> { iterator.nthBack(-1) }
        assertEquals(2uL, iterator.len())
    }

    @Test
    fun factoryConstructsFreshDataBearingValuesAfterClone() {
        val iterator = EnumIterator(1) { Payload(mutableListOf(it)) }
        val copy = iterator.clone()
        val first: Payload = iterator.next()
        val second: Payload = copy.next()
        assertNotSame(first, second)
        first.values.add(9)
        assertEquals(listOf(0), second.values)
    }

    @Test
    fun genericProviderCloneRetainsItsAssociatedIteratorType() {
        val iterator: EnumIterator<Signal> = cloneOf(Signal.FIRST)
        assertEquals(Signal.FIRST, iterator.next())
        assertEquals(Signal.SECOND, iterator.nextBack())
    }

    @Test
    fun discriminantAndReferenceKeepTheirConcreteTypes() {
        val metadata = Payload(mutableListOf(7))
        val value = TypedValue(metadata)
        val discriminant: IntoDiscriminant<Signal> = value
        val reference: AsStaticRef<Payload> = value
        assertEquals(Signal.SECOND, discriminant.discriminant())
        assertSame(metadata, reference.asStatic())
    }

    @Test
    fun variantValuesAndSnapshotsRetainElementIdentity() {
        val first = Payload(mutableListOf(1))
        val variants: VariantArray<Payload> = PayloadVariants(listOf(first))
        val element: Payload = variants.variants.single()
        assertSame(first, element)
        val mutableValues = mutableListOf(first)
        val iterator = EnumIterator.from(mutableValues)
        mutableValues.clear()
        assertEquals(1uL, iterator.len())
        assertSame(first, iterator.next())
    }

    private fun <E : Any, I : EnumIteratorContract<E, I>> cloneOf(provider: IntoEnumIterator<E, I>): I =
        provider.iter().clone()

    private enum class Signal : IntoEnumIterator<Signal, EnumIterator<Signal>> {
        FIRST,
        SECOND,
        ;

        override fun iter(): EnumIterator<Signal> = EnumIterator.from(entries)
    }

    private class Payload(
        val values: MutableList<Int>,
    )

    private class TypedValue(
        val metadata: Payload,
    ) : IntoDiscriminant<Signal>,
        AsStaticRef<Payload> {
        override fun discriminant(): Signal = Signal.SECOND

        override fun asStatic(): Payload = metadata
    }

    private class PayloadVariants(
        override val variants: List<Payload>,
    ) : VariantArray<Payload>
}
