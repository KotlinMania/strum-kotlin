// port-lint: tests enum_variant_array.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class EnumVariantArrayTest {
    private enum class Operation : VariantArray {
        ADD,
        SUB,
        MUL,
        DIV,
        ;

        override val variants: List<Operation> get() = VARIANTS

        companion object {
            val VARIANTS: List<Operation> = entries.toList()
        }
    }

    @Test
    fun simpleVariantArray() {
        assertEquals(
            listOf(Operation.ADD, Operation.SUB, Operation.MUL, Operation.DIV),
            Operation.VARIANTS,
        )
        assertEquals(
            listOf(Operation.ADD, Operation.SUB, Operation.MUL, Operation.DIV),
            Operation.ADD.variants,
        )
        assertContentEquals(
            arrayOf(Operation.ADD, Operation.SUB, Operation.MUL, Operation.DIV),
            enumVariantArray<Operation>(),
        )
    }

    private enum class WeekDay(
        val value: Int,
    ) : VariantArray {
        SUNDAY(0),
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        ;

        override val variants: List<WeekDay> get() = VARIANTS

        companion object {
            val VARIANTS: List<WeekDay> = entries.toList()
        }
    }

    @Test
    fun variantsWithValues() {
        assertEquals(
            listOf(
                WeekDay.SUNDAY,
                WeekDay.MONDAY,
                WeekDay.TUESDAY,
                WeekDay.WEDNESDAY,
                WeekDay.THURSDAY,
                WeekDay.FRIDAY,
                WeekDay.SATURDAY,
            ),
            WeekDay.VARIANTS,
        )
    }
}
