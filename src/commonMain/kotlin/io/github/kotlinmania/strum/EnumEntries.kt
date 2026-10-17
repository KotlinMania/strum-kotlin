@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Standard implementation of [EnumMessage] and [EnumProperty] for enum variants.
 */
@HiddenFromObjC
public open class StrumEntry(
    private val message: String? = null,
    private val detailedMessage: String? = null,
    private val documentation: String? = null,
    private val serializations: List<String> = emptyList(),
    private val properties: Map<String, Any> = emptyMap(),
) : EnumMessage,
    EnumProperty {
    override fun getMessage(): String? = message

    override fun getDetailedMessage(): String? = detailedMessage ?: message

    override fun getDocumentation(): String? = documentation

    override fun getSerializations(): List<String> = serializations

    override fun getStr(prop: String): String? =
        when (val v = properties[prop]) {
            is String -> v
            null -> null
            else -> v.toString()
        }

    override fun getInt(prop: String): Long? =
        when (val v = properties[prop]) {
            is Long -> v
            is Number -> v.toLong()
            is String -> v.toLongOrNull()
            else -> null
        }

    override fun getBool(prop: String): Boolean? =
        when (val v = properties[prop]) {
            is Boolean -> v
            is String -> v.toBooleanStrictOrNull()
            else -> null
        }
}

/**
 * Creates an [EnumProperty] from a map of property keys and values.
 */
@HiddenFromObjC
public fun Map<String, Any>.asEnumProperty(): EnumProperty =
    object : EnumProperty {
        override fun getStr(prop: String): String? =
            when (val v = this@asEnumProperty[prop]) {
                is String -> v
                null -> null
                else -> v.toString()
            }

        override fun getInt(prop: String): Long? =
            when (val v = this@asEnumProperty[prop]) {
                is Long -> v
                is Number -> v.toLong()
                is String -> v.toLongOrNull()
                else -> null
            }

        override fun getBool(prop: String): Boolean? =
            when (val v = this@asEnumProperty[prop]) {
                is Boolean -> v
                is String -> v.toBooleanStrictOrNull()
                else -> null
            }
    }

/**
 * Creates an [EnumProperty] from key-value pairs.
 */
@HiddenFromObjC
public fun enumPropertiesOf(vararg pairs: Pair<String, Any>): EnumProperty =
    pairs.toMap().asEnumProperty()
