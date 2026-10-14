// port-lint: tests display.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class DisplayTest {
    private enum class InnerColor {
        VIOLET,
        FUCHSIA,
        ;

        override fun toString(): String =
            when (this) {
                VIOLET -> "Violet"
                FUCHSIA -> "Fuchsia"
            }
    }

    private enum class Color(
        private val display: String,
    ) {
        @Strum(toStr = "RedRed")
        RED("RedRed"),

        @Strum(serialize = ["b"], toStr = "blue")
        BLUE("blue"),

        @Strum(serialize = ["y", "yellow"], toStr = "yellow")
        YELLOW("yellow"),
        ;

        override fun toString(): String = display
    }

    @Test
    fun toBlueString() {
        assertEquals("blue", Color.BLUE.toString())
    }

    @Test
    fun toYellowString() {
        assertEquals("yellow", Color.YELLOW.toString())
    }

    @Test
    fun toRedString() {
        assertEquals("RedRed", Color.RED.toString())
    }

    @Test
    fun toVioletString() {
        assertEquals("Violet", InnerColor.VIOLET.toString())
    }

    @Strum(serializeAll = "snake_case")
    private enum class Brightness {
        DARK_BLACK,
        DIM,

        @Strum(serialize = ["bright"])
        BRIGHT_WHITE,
        ;

        override fun toString(): String =
            when (this) {
                BRIGHT_WHITE -> "bright"
                else -> CaseStyle.SNAKE_CASE.convert(name)
            }
    }

    @Test
    fun brightnessToString() {
        assertEquals("dark_black", Brightness.DARK_BLACK.toString())
        assertEquals("dim", Brightness.DIM.toString())
        assertEquals("bright", Brightness.BRIGHT_WHITE.toString())
    }

    private class TransparentWrapper(
        @Strum(transparent = true)
        val value: String,
    ) {
        override fun toString(): String = value
    }

    @Test
    fun transparentString() {
        val wrapped = TransparentWrapper("string in here")
        assertEquals("string in here", wrapped.toString())
    }
}
