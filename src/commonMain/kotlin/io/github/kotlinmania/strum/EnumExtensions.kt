@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Returns an iterator over all entries of the enum [E].
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> enumIter(): Iterator<E> = enumValues<E>().iterator()

/**
 * Returns the count of variants in enum [E].
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> enumCount(): Int = enumValues<E>().size

/**
 * Returns the list of variant names of enum [E].
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> enumVariantNames(): List<String> = enumValues<E>().map { it.name }

/**
 * Returns an array containing all variants of enum [E].
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> enumVariantArray(): Array<E> = enumValues<E>()

/**
 * Returns the enum variant corresponding to the given [ordinal], or `null` if [ordinal] is out of bounds.
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> fromRepr(ordinal: Int): E? =
    enumValues<E>().getOrNull(ordinal)

/**
 * Parses an enum variant of type [E] from the string [value].
 *
 * Matching checks [Enum.name], and if [E] implements [EnumMessage], also checks
 * [EnumMessage.getSerializations].
 *
 * @param value The string to parse.
 * @param ignoreCase When true, string comparisons are case-insensitive.
 * @return The matching enum variant.
 * @throws ParseException if no variant matches [value].
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> fromString(value: String, ignoreCase: Boolean = false): E {
    val result = fromStringOrNull<E>(value, ignoreCase)
    if (result != null) return result
    throw ParseException(ParseError.VARIANT_NOT_FOUND)
}

/**
 * Parses an enum variant of type [E] from the string [value], returning `null` if not found.
 *
 * Matching checks [Enum.name], and if [E] implements [EnumMessage], also checks
 * [EnumMessage.getSerializations].
 *
 * @param value The string to parse.
 * @param ignoreCase When true, string comparisons are case-insensitive.
 * @return The matching enum variant, or `null` if no variant matches.
 */
@HiddenFromObjC
public inline fun <reified E : Enum<E>> fromStringOrNull(value: String, ignoreCase: Boolean = false): E? {
    for (entry in enumValues<E>()) {
        if (entry.name.equals(value, ignoreCase = ignoreCase)) {
            return entry
        }
        if (entry is EnumMessage) {
            for (serialization in entry.getSerializations()) {
                if (serialization.equals(value, ignoreCase = ignoreCase)) {
                    return entry
                }
            }
        }
    }
    return null
}
