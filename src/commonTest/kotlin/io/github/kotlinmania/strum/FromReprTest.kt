// port-lint: tests from_repr.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FromReprTest {
    private enum class StandardWeek {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
    }

    private enum class ExplicitReprWeek(
        val repr: Int,
    ) {
        SUNDAY(0),
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(7),
        SATURDAY(8),
        ;

        companion object {
            fun fromRepr(discriminant: Int): ExplicitReprWeek? =
                entries.firstOrNull { it.repr == discriminant }
        }
    }

    @Test
    fun standardFromReprTest() {
        assertEquals(StandardWeek.SUNDAY, fromRepr<StandardWeek>(0))
        assertEquals(StandardWeek.MONDAY, fromRepr<StandardWeek>(1))
        assertEquals(StandardWeek.SATURDAY, fromRepr<StandardWeek>(6))
        assertNull(fromRepr<StandardWeek>(7))
        assertNull(fromRepr<StandardWeek>(-1))
    }

    @Test
    fun explicitDiscriminantFromReprTest() {
        assertEquals(ExplicitReprWeek.SUNDAY, ExplicitReprWeek.fromRepr(0))
        assertEquals(ExplicitReprWeek.MONDAY, ExplicitReprWeek.fromRepr(1))
        assertNull(ExplicitReprWeek.fromRepr(6))
        assertEquals(ExplicitReprWeek.FRIDAY, ExplicitReprWeek.fromRepr(7))
        assertEquals(ExplicitReprWeek.SATURDAY, ExplicitReprWeek.fromRepr(8))
        assertNull(ExplicitReprWeek.fromRepr(9))
    }
}
