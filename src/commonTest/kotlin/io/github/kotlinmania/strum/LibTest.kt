// port-lint: ignore
// Kotlin smoke tests for the ParseError enum and trait surface in src/lib.rs.
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {

    @Test
    fun parseErrorDisplay() {
        assertEquals("Matching variant not found", ParseError.VARIANT_NOT_FOUND.toString())
    }

    @Test
    fun parseErrorDescription() {
        val expected =
            "Unable to find a variant of the given enum matching the string given. " +
                "Matching can be extended with the Serialize attribute and is case sensitive."
        assertEquals(expected, ParseError.VARIANT_NOT_FOUND.description())
    }

    @Test
    fun parseErrorEquality() {
        assertEquals(ParseError.VARIANT_NOT_FOUND, ParseError.VARIANT_NOT_FOUND)
    }

    @Test
    fun intoEnumIteratorRoundTrip() {
        val color = TestColor.RED
        val seen = mutableListOf<TestColor>()
        for (e in color.iter()) seen.add(e)
        assertEquals(listOf(TestColor.RED, TestColor.GREEN, TestColor.BLUE), seen)
    }

    @Test
    fun variantMetadataReportsVariant() {
        val red: VariantMetadata = TestColor.RED
        assertEquals(3, red.variantCount)
        assertEquals(listOf("RED", "GREEN", "BLUE"), red.variantNames)
        assertEquals("RED", red.variantName())
    }

    @Test
    fun enumCountAndVariantNamesAndArray() {
        val ec: EnumCount = TestColor.RED
        val vn: VariantNames = TestColor.RED
        val va: VariantArray<TestColor> = TestColor.RED
        assertEquals(3, ec.count)
        assertEquals(listOf("RED", "GREEN", "BLUE"), vn.variantNames)
        assertEquals(listOf(TestColor.RED, TestColor.GREEN, TestColor.BLUE), va.variants)
    }

    @Test
    fun intoDiscriminantReturnsExpected() {
        val d: IntoDiscriminant<String> = TestColor.GREEN
        assertEquals("GREEN", d.discriminant())
    }
}

private enum class TestColor :
    IntoEnumIterator<TestColor>,
    VariantIterator<TestColor>,
    VariantMetadata,
    EnumCount,
    VariantNames,
    VariantArray<TestColor>,
    IntoDiscriminant<String> {
    RED, GREEN, BLUE;

    override fun iter(): Iterator<TestColor> = entries.iterator()

    override val variantCount: Int = 3
    override val variantNames: List<String> = listOf("RED", "GREEN", "BLUE")
    override fun variantName(): String = name

    override val count: Int = 3
    override val variants: List<TestColor> get() = entries.toList()

    override fun discriminant(): String = name
}
