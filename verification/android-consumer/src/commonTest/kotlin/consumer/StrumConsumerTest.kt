package consumer

import io.github.kotlinmania.strum.CaseStyle
import io.github.kotlinmania.strum.StrumEntry
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
}
