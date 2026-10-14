// port-lint: tests serialize_all.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class SerializeAllTest {
    @Test
    fun testSerializeAllTitleCase() {
        assertEquals("Dark Black", CaseStyle.TITLE_CASE.convert("DarkBlack"))
        assertEquals("Dark Black", CaseStyle.TITLE_CASE.convert("dark_black"))
        assertEquals("Dark Black", convertCase("DarkBlack", "title_case"))
    }

    @Test
    fun testSerializeAllUpperCase() {
        assertEquals("DARKBLACK", CaseStyle.UPPERCASE.convert("DarkBlack"))
        assertEquals("DARKBLACK", CaseStyle.UPPERCASE.convert("dark_black"))
        assertEquals("DARKBLACK", convertCase("DarkBlack", "UPPERCASE"))
    }

    @Test
    fun testSerializeAllWrittenInSnakeCaseCamelCase() {
        // Soft-deprecated behavior in upstream: "camel_case" maps to PascalCase
        val style = CaseStyle.fromStyleName("camel_case")
        assertEquals(CaseStyle.PASCAL_CASE, style)
        assertEquals("CamelCase", style?.convert("camel_case"))
    }

    @Test
    fun testSerializeAllCamelCase() {
        assertEquals("camelCase", CaseStyle.CAMEL_CASE.convert("CamelCase"))
        assertEquals("camelCase", CaseStyle.CAMEL_CASE.convert("camel_case"))
        assertEquals("darkBlack", CaseStyle.CAMEL_CASE.convert("DarkBlack"))
        assertEquals("camelCase", convertCase("CamelCase", "camelCase"))
    }

    @Test
    fun testSerializeAllKebabCase() {
        assertEquals("dark-black", CaseStyle.KEBAB_CASE.convert("DarkBlack"))
        assertEquals("rebecca-purple", CaseStyle.KEBAB_CASE.convert("RebeccaPurple"))
        assertEquals("cornflower-blue", CaseStyle.KEBAB_CASE.convert("CornflowerBlue"))
        assertEquals("deep-pink", CaseStyle.KEBAB_CASE.convert("DeepPink"))
    }

    @Test
    fun testSerializeAllSnakeCase() {
        assertEquals("dark_black", CaseStyle.SNAKE_CASE.convert("DarkBlack"))
        assertEquals("bright_white", CaseStyle.SNAKE_CASE.convert("BrightWhite"))
        assertEquals("glow_level", CaseStyle.SNAKE_CASE.convert("glowLevel"))
    }

    @Test
    fun testSerializeAllScreamingSnakeCase() {
        assertEquals("DARK_BLACK", CaseStyle.SCREAMING_SNAKE_CASE.convert("DarkBlack"))
        assertEquals("BRIGHT_WHITE", CaseStyle.SCREAMING_SNAKE_CASE.convert("BrightWhite"))
    }

    @Test
    fun testSerializeAllScreamingKebabCase() {
        assertEquals("DARK-BLACK", CaseStyle.SCREAMING_KEBAB_CASE.convert("DarkBlack"))
        assertEquals("BRIGHT-WHITE", CaseStyle.SCREAMING_KEBAB_CASE.convert("BrightWhite"))
    }

    @Test
    fun testSerializeAllLowercase() {
        assertEquals("darkblack", CaseStyle.LOWERCASE.convert("DarkBlack"))
    }

    @Test
    fun testSerializeAllTrainCase() {
        assertEquals("Dark-Black", CaseStyle.TRAIN_CASE.convert("dark_black"))
        assertEquals("Dark-Black", CaseStyle.TRAIN_CASE.convert("DarkBlack"))
    }
}
