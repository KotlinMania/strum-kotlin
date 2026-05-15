// port-lint: source src/lib.rs
/**
 * # Strum
 *
 * [![Build Status](https://travis-ci.org/Peternator7/strum.svg?branch=master)](https://travis-ci.org/Peternator7/strum)
 * [![Latest Version](https://img.shields.io/crates/v/strum.svg)](https://crates.io/crates/strum)
 * [![Rust Documentation](https://docs.rs/strum/badge.svg)](https://docs.rs/strum)
 *
 * Strum is a set of macros and traits for working with
 * enums and strings easier in Rust.
 *
 * The full version of the README can be found on [GitHub](https://github.com/Peternator7/strum).
 *
 * # Including Strum in Your Project
 *
 * Add `strum-kotlin` and `strum-macros-kotlin` to your dependencies. The
 * `strum-macros-kotlin` artifact provides the runtime helpers and codegen API
 * that stand in for the Rust `strum_macros` derive macros.
 */
package io.github.kotlinmania.strum

/**
 * The [ParseError] enum is a collection of all the possible reasons
 * an enum can fail to parse from a string.
 */
public enum class ParseError {
    VARIANT_NOT_FOUND;

    override fun toString(): String = when (this) {
        VARIANT_NOT_FOUND -> "Matching variant not found"
    }

    /**
     * Long-form description of the parse error, mirroring the
     * `std::error::Error::description` text in upstream.
     */
    public fun description(): String = when (this) {
        VARIANT_NOT_FOUND ->
            "Unable to find a variant of the given enum matching the string given. Matching " +
                "can be extended with the Serialize attribute and is case sensitive."
    }
}

/**
 * This trait designates that an `Enum` can be iterated over. It can
 * be auto generated using the [EnumIter](io.github.kotlinmania.strum.macros.EnumIter)
 * derive macro.
 *
 * # Example
 *
 * ```
 * // You need to bring the type into scope to use it!!!
 * // import io.github.kotlinmania.strum.macros.EnumIter
 * // import io.github.kotlinmania.strum.IntoEnumIterator
 *
 * enum class Color : IntoEnumIterator<Color> {
 *     Red, Green, Blue, Yellow;
 *     override fun iter(): Iterator<Color> = entries.iterator()
 * }
 *
 * // Iterate over the items in an enum and perform some function on them.
 * fun <E : IntoEnumIterator<E>> genericIterator(first: E, pred: (E) -> Unit) {
 *     for (e in first.iter()) {
 *         pred(e)
 *     }
 * }
 *
 * genericIterator(Color.Red) { color -> println(color) }
 * ```
 */
public interface IntoEnumIterator<T> {
    public fun iter(): Iterator<T>
}

public interface VariantIterator<T> {
    public fun iter(): Iterator<T>
}

public interface VariantMetadata {
    public val variantCount: Int
    public val variantNames: List<String>

    public fun variantName(): String
}

/**
 * Associates additional pieces of information with an Enum. This can be
 * autoimplemented by deriving `EnumMessage` and annotating your variants with
 * `@Strum(message="...")`.
 *
 * # Example
 *
 * ```
 * // You need to bring the type into scope to use it!!!
 * // import io.github.kotlinmania.strum.EnumMessage
 *
 * enum class Pet : EnumMessage {
 *     // @Strum(message="I have a dog")
 *     // @Strum(detailedMessage="My dog's name is Spots")
 *     Dog,
 *     /** I am documented. */
 *     // @Strum(message="I don't have a cat")
 *     Cat;
 *
 *     override fun getMessage(): String? = when (this) {
 *         Dog -> "I have a dog"
 *         Cat -> "I don't have a cat"
 *     }
 *
 *     override fun getDetailedMessage(): String? = when (this) {
 *         Dog -> "My dog's name is Spots"
 *         Cat -> null
 *     }
 *
 *     override fun getDocumentation(): String? = when (this) {
 *         Dog -> null
 *         Cat -> "I am documented."
 *     }
 *
 *     override fun getSerializations(): List<String> = listOf(name)
 * }
 *
 * val myPet = Pet.Dog
 * check("I have a dog" == myPet.getMessage())
 * ```
 */
public interface EnumMessage {
    public fun getMessage(): String?
    public fun getDetailedMessage(): String?

    /** Get the doc comment associated with a variant if it exists. */
    public fun getDocumentation(): String?
    public fun getSerializations(): List<String>
}

/**
 * `EnumProperty` is a trait that makes it possible to store additional information
 * with enum variants. This trait is designed to be used with the codegen API of the
 * same name in the `strum-macros-kotlin` crate. Currently, the string, integer and
 * bool literals are supported in attributes.
 *
 * # Example
 *
 * ```
 * // You need to bring the type into scope to use it!!!
 * // import io.github.kotlinmania.strum.EnumProperty
 *
 * enum class Class : EnumProperty {
 *     // @Strum(props=["Teacher=Ms.Frizzle", "Room=201", "students=16", "mandatory=true"])
 *     History,
 *     // @Strum(props=["Teacher=Mr.Smith"])
 *     // @Strum(props=["Room=103", "students=10"])
 *     Mathematics,
 *     // @Strum(props=["Time=2:30", "mandatory=true"])
 *     Science;
 *
 *     override fun getStr(prop: String): String? = /* derived */ null
 *     override fun getInt(prop: String): Long? = /* derived */ null
 *     override fun getBool(prop: String): Boolean? = /* derived */ null
 * }
 *
 * val history = Class.History
 * check("Ms.Frizzle" == history.getStr("Teacher"))
 * check(16L == history.getInt("students"))
 * check(history.getBool("mandatory") == true)
 * ```
 */
public interface EnumProperty {
    public fun getStr(prop: String): String?
    public fun getInt(prop: String): Long?
    public fun getBool(prop: String): Boolean?
}

/**
 * A cheap reference-to-reference conversion. Used to convert a value to a
 * reference value that lives for the lifetime of the program within generic
 * code.
 */
@Deprecated(
    "please use IntoStaticStr derive instead",
    level = DeprecationLevel.WARNING,
)
public interface AsStaticRef<T> {
    public fun asStatic(): T
}

/**
 * A trait for capturing the number of variants in Enum. This trait can be
 * autoderived by `strum-macros-kotlin`.
 */
public interface EnumCount {
    public val count: Int
}

/**
 * A trait for retrieving the names of each variant in Enum. This trait can
 * be autoderived by `strum-macros-kotlin`.
 */
public interface VariantNames {
    /** Names of the variants of this enum */
    public val variantNames: List<String>
}

/**
 * A trait for retrieving the enum generated by [EnumDiscriminants](io.github.kotlinmania.strum.macros.EnumDiscriminants)
 * from an associated Type on the original enumeration. This trait can be
 * autoderived by `strum-macros-kotlin`.
 */
public interface IntoDiscriminant<D> {
    public fun discriminant(): D
}

/**
 * A trait for retrieving a list containing all the variants in an Enum.
 * This trait can be autoderived by `strum-macros-kotlin`. For derived usage,
 * all the variants in the enumerator need to be unit-types, which means you
 * can't autoderive enums with inner data in one or more variants. Consider
 * using it alongside [EnumDiscriminants](io.github.kotlinmania.strum.macros.EnumDiscriminants)
 * if you require inner data but still want to have a static list of variants.
 */
public interface VariantArray<T> {
    public val variants: List<T>
}
