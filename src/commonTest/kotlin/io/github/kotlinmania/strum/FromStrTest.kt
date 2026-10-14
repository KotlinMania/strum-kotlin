// port-lint: tests from_str.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class FromStrTest {
    private enum class Week {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
    }

    @Test
    fun weekFound() {
        assertEquals(Week.SUNDAY, fromString<Week>("SUNDAY"))
        assertEquals(Week.MONDAY, fromString<Week>("MONDAY"))
        assertEquals(Week.TUESDAY, fromString<Week>("TUESDAY"))
        assertEquals(Week.WEDNESDAY, fromString<Week>("WEDNESDAY"))
        assertEquals(Week.THURSDAY, fromString<Week>("THURSDAY"))
        assertEquals(Week.FRIDAY, fromString<Week>("FRIDAY"))
        assertEquals(Week.SATURDAY, fromString<Week>("SATURDAY"))
    }

    @Test
    fun weekNotFound() {
        val ex =
            assertFailsWith<ParseException> {
                fromString<Week>("Humpday")
            }
        assertEquals(ParseError.VARIANT_NOT_FOUND, ex.error)
        assertNull(fromStringOrNull<Week>("Humpday"))
    }

    private enum class Color(
        private val serializations: List<String> = emptyList(),
    ) : EnumMessage {
        RED,
        BLUE,

        @Strum(serialize = ["y", "yellow"])
        YELLOW(listOf("y", "yellow")),

        @Strum(toStr = "purp")
        PURPLE(listOf("purp")),

        @Strum(serialize = ["blk", "Black"], asciiCaseInsensitive = true)
        BLACK(listOf("blk", "Black")),
        ;

        override fun getMessage(): String? = null

        override fun getDetailedMessage(): String? = null

        override fun getDocumentation(): String? = null

        override fun getSerializations(): List<String> = serializations
    }

    @Test
    fun colorSimple() {
        assertEquals(Color.RED, fromString<Color>("RED"))
        assertEquals(Color.BLUE, fromString<Color>("BLUE"))
    }

    @Test
    fun colorSerialize() {
        assertEquals(Color.YELLOW, fromString<Color>("y"))
        assertEquals(Color.YELLOW, fromString<Color>("yellow"))
    }

    @Test
    fun colorToString() {
        assertEquals(Color.PURPLE, fromString<Color>("purp"))
    }

    @Test
    fun colorAsciiCaseInsensitive() {
        assertEquals(Color.BLACK, fromString<Color>("BLK", ignoreCase = true))
        assertEquals(Color.BLACK, fromString<Color>("bLaCk", ignoreCase = true))
    }

    private enum class CaseInsensitiveEnum {
        NO_ATTR,
        NO_CASE_INSENSITIVE,
        CASE_INSENSITIVE,
    }

    @Test
    fun caseInsensitiveEnumTest() {
        assertEquals(
            CaseInsensitiveEnum.NO_ATTR,
            fromString<CaseInsensitiveEnum>("no_attr", ignoreCase = true),
        )
        assertEquals(
            CaseInsensitiveEnum.CASE_INSENSITIVE,
            fromString<CaseInsensitiveEnum>("case_insensitive", ignoreCase = true),
        )
        assertFailsWith<ParseException> {
            fromString<CaseInsensitiveEnum>("no_attr", ignoreCase = false)
        }
    }
}
