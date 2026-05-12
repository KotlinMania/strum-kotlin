// port-lint: source src/additional_attributes.rs
/**
 * # Documentation for Additional Attributes
 *
 * ## Attributes on Enums
 *
 * Strum supports several custom attributes to modify the generated code. At
 * the enum level, the following attributes are supported:
 *
 * - `@Strum(serializeAll="caseStyle")` attribute can be used to change the case
 *   used when serializing to and deserializing from strings. This feature is
 *   enabled by [withoutboats/heck](https://github.com/withoutboats/heck) and
 *   supported case styles are:
 *
 *   - `camelCase`
 *   - `PascalCase`
 *   - `kebab-case`
 *   - `snake_case`
 *   - `SCREAMING_SNAKE_CASE`
 *   - `SCREAMING-KEBAB-CASE`
 *   - `lowercase`
 *   - `UPPERCASE`
 *   - `title_case`
 *   - `mixed_case`
 *   - `Train-Case`
 *
 *   ```
 *   // import io.github.kotlinmania.strum.macros.Display
 *
 *   // @Strum(serializeAll="snake_case")
 *   enum class Brightness {
 *       DarkBlack,
 *       Dim,
 *       // @Strum(serialize="bright")
 *       BrightWhite;
 *   }
 *
 *   check("dark_black" == Brightness.DarkBlack.toString())
 *   check("dim" == Brightness.Dim.toString())
 *   check("bright" == Brightness.BrightWhite.toString())
 *   ```
 *
 * - You can also apply the `@Strum(asciiCaseInsensitive=true)` attribute to the
 *   enum, and this has the same effect of applying it to every variant.
 *
 * ## Attributes on Variants
 *
 * Custom attributes are applied to a variant by adding `@Strum(parameter="value")`
 * to the variant.
 *
 * - `serialize="..."`: Changes the text that the parse helper looks for when parsing
 *    a string. This attribute can be applied multiple times to an element and the
 *    enum variant will be parsed if any of them match.
 *
 * - `toString="..."`: Similar to `serialize`. This value will be included when using
 *    the parse helper. More importantly, this specifies what text to use when calling
 *    `variant.toString()` with the `Display` derivation, or when calling
 *    `variant.asRef()` with `AsRefStr`.
 *
 * - `default`: Applied to a single variant of an enum. The variant must be a
 *    Tuple-like variant with a single piece of data that can be created from a
 *    `String` i.e. `T` providing a `from(String)` constructor.
 *    The generated code will now return the variant with the input string captured
 *    as shown below instead of failing.
 *
 *     ```
 *     // Replaces this:
 *     // else -> throw ParseError.VARIANT_NOT_FOUND
 *     // With this in generated code:
 *     // default -> Variant(default)
 *     ```
 *     The plugin will fail if the data doesn't have a `from(String)` constructor.
 *     You can only have one `default` on your enum.
 *
 * - `transparent`: Signals that the inner field's implementation should be used,
 *    instead of generating one for this variant. Only applicable to enum variants
 *    with a single field. Compatible with the `AsRefStr`, `Display` and
 *    `IntoStaticStr` derive macros. Note that `IntoStaticStr` has a few
 *    restrictions, the value must be program-lifetime constant and `constIntoStr`
 *    is not supported in combination with `transparent` because transparent relies
 *    on a call on `from(variant)`.
 *
 * - `disabled`: removes variant from generated code.
 *
 * - `asciiCaseInsensitive`: makes the comparison to this variant case insensitive
 *   (ASCII only). If the whole enum is marked `asciiCaseInsensitive`, you can
 *   specify `asciiCaseInsensitive=false` to disable case insensitivity on this
 *   variant.
 *
 * - `message="..."`: Adds a message to enum variant. This is used in conjunction
 *    with the `EnumMessage` trait to associate a message with a variant. If
 *    `detailedMessage` is not provided, then `message` will also be returned when
 *    `getDetailedMessage` is called.
 *
 * - `detailedMessage="..."`: Adds a more detailed message to a variant. If this
 *    value is omitted, then `message` will be used in its place.
 *
 * - Structured documentation, as in `/// ...`: If using `EnumMessage`, is
 *    accessible via `getDocumentation()`.
 *
 * - `props(key="value")`: Enables associating additional information with a given
 *    variant.
 */
package io.github.kotlinmania.strum.additionalattributes
