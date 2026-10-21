package consumer

import io.github.kotlinmania.strum.CaseStyle
import io.github.kotlinmania.strum.StrumEntry
import io.github.kotlinmania.strum.EnumIterator
import kotlin.test.assertSame
import kotlin.test.Test
import kotlin.test.assertEquals

class StrumConsumerTest {
    @Test
    fun inlineEnumParserCrossesPublicationBoundary() {
        assertEquals(ConsumerColor.Red, parseColor("Red"))
        assertEquals(ConsumerColor.Blue, parseColor("Blue"))
    }

    @Test
    fun runtimeDiagnosticAndCaseConversionCrossPublicationBoundary() {
        assertEquals("Matching variant not found", parseErrorMessage())
        assertEquals("hello_world", CaseStyle.SNAKE_CASE.convert("HelloWorld"))
    }

    @Test
    fun entryMetadataCrossesPublicationBoundary() {
        val entry = StrumEntry(message = "message", serializations = listOf("first", "second"), properties = mapOf("count" to 7L))
        assertEquals("message", entry.getMessage())
        assertEquals(listOf("first", "second"), entry.getSerializations())
        assertEquals(7L, entry.getInt("count"))
    }

    @Test
    fun typedContractsCrossPublicationBoundary() {
        val iterator: EnumIterator<ConsumerColor> = ConsumerColor.Red.iter()
        val copy: EnumIterator<ConsumerColor> = iterator.clone()
        assertEquals(ConsumerColor.Red, iterator.next())
        assertEquals(ConsumerColor.Blue, copy.nextBack())
        assertEquals(1uL, iterator.len())
        assertEquals(1uL, copy.len())
        val discriminant: Int = ConsumerColor.Blue.discriminant()
        val variants: List<ConsumerColor> = ConsumerColor.Red.variants
        val reference: ConsumerColor = ConsumerColor.Red.asStatic()
        assertEquals(1, discriminant)
        assertEquals(listOf(ConsumerColor.Red, ConsumerColor.Blue), variants)
        assertSame(ConsumerColor.Red, reference)
    }
}
