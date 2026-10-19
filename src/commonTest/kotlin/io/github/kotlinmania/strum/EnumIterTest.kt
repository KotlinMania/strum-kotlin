// port-lint: tests enum_iter.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class EnumIterTest {
    private enum class Week : IntoEnumIterator {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        ;

        override fun iter(): List<Week> = entries
    }

    @Test
    fun weekIter() {
        val seen = mutableListOf<Week>()
        for (day in Week.SUNDAY.iter()) {
            seen.add(day)
        }
        assertEquals(7, seen.size)
        assertEquals(Week.SUNDAY, seen.first())
        assertEquals(Week.SATURDAY, seen.last())
    }

    @Test
    fun genericIterator() {
        val collected = mutableListOf<Week>()
        for (day in enumIter<Week>()) {
            collected.add(day)
        }
        assertEquals(7, collected.size)
        assertEquals(Week.SUNDAY, collected[0])
        assertEquals(Week.MONDAY, collected[1])
        assertEquals(Week.TUESDAY, collected[2])
        assertEquals(Week.WEDNESDAY, collected[3])
        assertEquals(Week.THURSDAY, collected[4])
        assertEquals(Week.FRIDAY, collected[5])
        assertEquals(Week.SATURDAY, collected[6])
    }
}
