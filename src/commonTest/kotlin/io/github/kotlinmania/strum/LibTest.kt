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
    fun variantIteratorRoundTrip() {
        val color = TestColor.BLUE
        assertEquals(listOf(TestColor.RED, TestColor.GREEN, TestColor.BLUE), color.iter().asSequence().toList())
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

    @Test
    fun enumMessageReturnsMessageDetailsDocumentationAndSerializations() {
        val dog: EnumMessage = TestPet.DOG
        val cat: EnumMessage = TestPet.CAT

        assertEquals("I have a dog", dog.getMessage())
        assertEquals("My dog's name is Spots", dog.getDetailedMessage())
        assertEquals(null, dog.getDocumentation())
        assertEquals(listOf("DOG"), dog.getSerializations())

        assertEquals("I don't have a cat", cat.getMessage())
        assertEquals("I don't have a cat", cat.getDetailedMessage())
        assertEquals("I am documented.", cat.getDocumentation())
        assertEquals(listOf("CAT"), cat.getSerializations())
    }

    @Test
    fun enumPropertyReturnsStringIntegerAndBoolProperties() {
        val history: EnumProperty = TestClass.HISTORY
        val mathematics: EnumProperty = TestClass.MATHEMATICS
        val science: EnumProperty = TestClass.SCIENCE

        assertEquals("Ms.Frizzle", history.getStr("Teacher"))
        assertEquals("201", history.getStr("Room"))
        assertEquals(16L, history.getInt("students"))
        assertEquals(true, history.getBool("mandatory"))

        assertEquals("Mr.Smith", mathematics.getStr("Teacher"))
        assertEquals("103", mathematics.getStr("Room"))
        assertEquals(10L, mathematics.getInt("students"))
        assertEquals(null, mathematics.getBool("mandatory"))

        assertEquals("2:30", science.getStr("Time"))
        assertEquals(true, science.getBool("mandatory"))
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

private enum class TestPet : EnumMessage {
    DOG,
    CAT;

    override fun getMessage(): String? = when (this) {
        DOG -> "I have a dog"
        CAT -> "I don't have a cat"
    }

    override fun getDetailedMessage(): String? = when (this) {
        DOG -> "My dog's name is Spots"
        CAT -> "I don't have a cat"
    }

    override fun getDocumentation(): String? = when (this) {
        DOG -> null
        CAT -> "I am documented."
    }

    override fun getSerializations(): List<String> = listOf(name)
}

private enum class TestClass : EnumProperty {
    HISTORY,
    MATHEMATICS,
    SCIENCE;

    override fun getStr(prop: String): String? = when (this) {
        HISTORY -> mapOf("Teacher" to "Ms.Frizzle", "Room" to "201")[prop]
        MATHEMATICS -> mapOf("Teacher" to "Mr.Smith", "Room" to "103")[prop]
        SCIENCE -> mapOf("Time" to "2:30")[prop]
    }

    override fun getInt(prop: String): Long? = when (this) {
        HISTORY -> mapOf("students" to 16L)[prop]
        MATHEMATICS -> mapOf("students" to 10L)[prop]
        SCIENCE -> null
    }

    override fun getBool(prop: String): Boolean? = when (this) {
        HISTORY -> mapOf("mandatory" to true)[prop]
        MATHEMATICS -> null
        SCIENCE -> mapOf("mandatory" to true)[prop]
    }
}
