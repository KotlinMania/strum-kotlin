@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Primary annotation for configuring Strum properties on an enum class or its entries.
 *
 * Can be applied at the enum class level (e.g. [serializeAll], [asciiCaseInsensitive])
 * or on specific enum entries (e.g. [serialize], [toString], [message], [props]).
 */
@HiddenFromObjC
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
public annotation class Strum(
    /** Case style used when serializing to and deserializing from strings (e.g. "snake_case", "camelCase"). */
    val serializeAll: String = "",
    /** When true, comparisons are case-insensitive (ASCII). */
    val asciiCaseInsensitive: Boolean = false,
    /** Additional serialization strings to match when parsing. */
    val serialize: Array<String> = [],
    /** Custom string representation for Display/ToString (corresponds to upstream `to_string`). */
    val toStr: String = "",
    /** Marks a default variant when parsing unknown strings. */
    val default: Boolean = false,
    /** Signals transparent delegation to an inner field. */
    val transparent: Boolean = false,
    /** Excludes this variant from iteration and metadata. */
    val disabled: Boolean = false,
    /** Message string associated with the variant for [EnumMessage]. */
    val message: String = "",
    /** Detailed message string associated with the variant for [EnumMessage]. */
    val detailedMessage: String = "",
    /** Key-value properties associated with the variant (format: "key=value"). */
    val props: Array<String> = [],
)

/**
 * Annotation for generating or configuring discriminant enums.
 */
@HiddenFromObjC
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
public annotation class StrumDiscriminants(
    /** Name of the generated discriminant enum. */
    val name: String = "",
    /** Visibility modifier for the generated discriminant. */
    val vis: String = "",
    /** Additional derives or traits for the discriminant. */
    val derive: Array<String> = [],
)

/**
 * Key-value property annotation for [EnumProperty].
 */
@HiddenFromObjC
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
public annotation class StrumProp(
    val key: String,
    val value: String,
)
