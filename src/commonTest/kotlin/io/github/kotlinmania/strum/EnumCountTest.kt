// port-lint: tests enum_count.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class EnumCountTest {
    private enum class Week :
        EnumCount,
        IntoEnumIterator<Week, EnumIterator<Week>> {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        ;

        override val count: Int get() = COUNT

        override fun iter(): EnumIterator<Week> = EnumIterator.from(entries)

        companion object {
            const val COUNT: Int = 7
        }
    }

    private enum class Pets :
        EnumCount,
        IntoEnumIterator<Pets, EnumIterator<Pets>> {
        DOG,
        CAT,
        FISH,
        BIRD,

        @Strum(disabled = true)
        HAMSTER,
        ;

        override val count: Int get() = COUNT

        override fun iter(): EnumIterator<Pets> =
            EnumIterator.from(entries.filter { it != HAMSTER })

        companion object {
            const val COUNT: Int = 4
        }
    }

    @Test
    fun simpleTest() {
        assertEquals(7, Week.COUNT)
        assertEquals(Week.COUNT, Week.SUNDAY.count)
        assertEquals(Week.COUNT, enumCount<Week>())
        val iter = Week.SUNDAY.iter()
        val list = mutableListOf<Week>()
        for (item in iter) {
            list.add(item)
        }
        assertEquals(Week.COUNT, list.size)
    }

    @Test
    fun disabledTest() {
        assertEquals(4, Pets.COUNT)
        assertEquals(Pets.COUNT, Pets.DOG.count)
        val iter = Pets.DOG.iter()
        val list = mutableListOf<Pets>()
        for (item in iter) {
            list.add(item)
        }
        assertEquals(Pets.COUNT, list.size)
    }
}
