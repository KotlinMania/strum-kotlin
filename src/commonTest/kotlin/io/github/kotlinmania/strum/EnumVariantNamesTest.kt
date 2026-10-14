// port-lint: tests enum_variant_names.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class EnumVariantNamesTest {
    private enum class SimpleColor : VariantNames {
        RED,
        BLUE,
        YELLOW,
        ;

        override val variantNames: List<String> get() = VARIANTS

        companion object {
            val VARIANTS: List<String> = listOf("RED", "BLUE", "YELLOW")
        }
    }

    @Test
    fun simpleVariantNames() {
        assertEquals(listOf("RED", "BLUE", "YELLOW"), SimpleColor.VARIANTS)
        assertEquals(listOf("RED", "BLUE", "YELLOW"), SimpleColor.RED.variantNames)
        assertEquals(listOf("RED", "BLUE", "YELLOW"), enumVariantNames<SimpleColor>())
    }

    private enum class KebabColor {
        RED,
        BLUE,
        YELLOW,
        REBECCA_PURPLE,
        ;

        companion object {
            val VARIANTS: List<String> =
                entries.map { CaseStyle.KEBAB_CASE.convert(it.name) }
        }
    }

    @Test
    fun plainKebab() {
        assertEquals(
            listOf("red", "blue", "yellow", "rebecca-purple"),
            KebabColor.VARIANTS,
        )
    }

    private enum class ExtendedColor {
        DEEP_PINK,
        GREEN_YELLOW,
        CORNFLOWER_BLUE,
        OTHER,
        ;

        companion object {
            val VARIANTS: List<String> =
                entries.map { CaseStyle.KEBAB_CASE.convert(it.name) }
        }
    }

    @Test
    fun nonPlainCamel() {
        assertEquals(
            listOf("deep-pink", "green-yellow", "cornflower-blue", "other"),
            ExtendedColor.VARIANTS,
        )
    }

    @Test
    fun genericVariantNamesFunction() {
        fun <T : VariantNames> getVariants(instance: T): List<String> = instance.variantNames

        assertEquals(
            listOf("RED", "BLUE", "YELLOW"),
            getVariants(SimpleColor.RED),
        )
    }
}
