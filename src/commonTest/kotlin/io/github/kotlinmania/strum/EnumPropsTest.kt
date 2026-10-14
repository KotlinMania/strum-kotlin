// port-lint: tests enum_props.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EnumPropsTest {
    private enum class TestEnum(
        private val entry: StrumEntry,
    ) : EnumProperty by entry {
        @Strum(props = ["key=value"])
        A(StrumEntry(properties = mapOf("key" to "value"))),

        B(StrumEntry()),
    }

    @Test
    fun propTest() {
        val a = TestEnum.A
        assertEquals("value", a.getStr("key"))
    }

    @Test
    fun propTestNotFound() {
        val a = TestEnum.A
        assertNull(a.getStr("Not Found"))
    }

    @Test
    fun propTestNotFound2() {
        val b = TestEnum.B
        assertNull(b.getStr("key"))
    }

    private enum class TestGet(
        private val entry: StrumEntry,
    ) : EnumProperty by entry {
        @Strum(props = ["weight=42", "flat=true", "big=false"])
        A(
            StrumEntry(
                properties =
                    mapOf(
                        "weight" to 42L,
                        "flat" to true,
                        "big" to false,
                    ),
            ),
        ),

        @Strum(props = ["weight=-42", "flat=false"])
        B(
            StrumEntry(
                properties =
                    mapOf(
                        "weight" to -42L,
                        "flat" to false,
                    ),
            ),
        ),

        C(StrumEntry()),
    }

    @Test
    fun getBoolTest() {
        val boolKey = "flat"
        assertEquals(true, TestGet.A.getBool(boolKey))
        assertEquals(false, TestGet.B.getBool(boolKey))
        assertNull(TestGet.C.getBool(boolKey))
        assertNull(TestGet.A.getBool("weight"))
    }

    @Test
    fun getIntTest() {
        val intKey = "weight"
        assertEquals(42L, TestGet.A.getInt(intKey))
        assertEquals(-42L, TestGet.B.getInt(intKey))
        assertNull(TestGet.C.getInt(intKey))
        assertNull(TestGet.A.getInt("flat"))
    }
}
