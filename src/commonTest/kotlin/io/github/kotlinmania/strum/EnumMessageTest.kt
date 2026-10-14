// port-lint: tests enum_message.rs
package io.github.kotlinmania.strum

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EnumMessageTest {
    private enum class Pets(
        private val entry: StrumEntry,
    ) : EnumMessage by entry {
        @Strum(message = "I'm a dog")
        DOG(StrumEntry(message = "I'm a dog")),

        /**
         * I eat birds.
         *
         * And fish.
         */
        @Strum(
            message = "I'm a cat",
            detailedMessage = "I'm a very exquisite striped cat",
        )
        CAT(
            StrumEntry(
                message = "I'm a cat",
                detailedMessage = "I'm a very exquisite striped cat",
                documentation = "I eat birds.\n\nAnd fish.\n",
            ),
        ),

        /** I'm a fish. */
        @Strum(detailedMessage = "My fish is named Charles McFish")
        FISH(
            StrumEntry(
                detailedMessage = "My fish is named Charles McFish",
                documentation = "I'm a fish.",
            ),
        ),

        /** I'm a bird. */
        BIRD(
            StrumEntry(
                documentation = "I'm a bird.",
            ),
        ),

        /** This comment is not collected because it is explicitly disabled. */
        @Strum(disabled = true)
        HAMSTER(StrumEntry()),
    }

    @Test
    fun simpleMessage() {
        assertEquals("I'm a dog", Pets.DOG.getMessage())
        assertEquals("I'm a dog", Pets.DOG.getDetailedMessage())
    }

    @Test
    fun detailedMessage() {
        assertEquals("I'm a cat", Pets.CAT.getMessage())
        assertEquals("I'm a very exquisite striped cat", Pets.CAT.getDetailedMessage())
    }

    @Test
    fun onlyDetailedMessage() {
        assertNull(Pets.FISH.getMessage())
        assertEquals("My fish is named Charles McFish", Pets.FISH.getDetailedMessage())
    }

    @Test
    fun documentation() {
        assertEquals("I eat birds.\n\nAnd fish.\n", Pets.CAT.getDocumentation())
        assertEquals("I'm a fish.", Pets.FISH.getDocumentation())
        assertEquals("I'm a bird.", Pets.BIRD.getDocumentation())
    }

    @Test
    fun noDocumentation() {
        assertNull(Pets.DOG.getDocumentation())
    }

    @Test
    fun disabledDocumentation() {
        assertNull(Pets.HAMSTER.getDocumentation())
    }

    @Test
    fun noMessage() {
        assertNull(Pets.BIRD.getMessage())
        assertNull(Pets.BIRD.getDetailedMessage())
    }

    @Test
    fun disabledMessage() {
        assertNull(Pets.HAMSTER.getMessage())
        assertNull(Pets.HAMSTER.getDetailedMessage())
    }

    @Strum(serializeAll = "kebab_case")
    private enum class Brightness(
        private val serializations: List<String>,
    ) : EnumMessage {
        DARK_BLACK(listOf("dark-black")),
        DIM(listOf("dim")),

        @Strum(serialize = ["bright"])
        BRIGHT_WHITE(listOf("bright")),
        ;

        override fun getMessage(): String? = null

        override fun getDetailedMessage(): String? = null

        override fun getDocumentation(): String? = null

        override fun getSerializations(): List<String> = serializations
    }

    @Test
    fun getSerializations() {
        assertEquals(listOf("dark-black"), Brightness.DARK_BLACK.getSerializations())
        assertEquals(listOf("dim"), Brightness.DIM.getSerializations())
        assertEquals(listOf("bright"), Brightness.BRIGHT_WHITE.getSerializations())
    }
}
