@file:OptIn(kotlin.experimental.ExperimentalObjCRefinement::class)

package io.github.kotlinmania.strum

import kotlin.native.HiddenFromObjC

/**
 * Case styles supported by Strum for `serialize_all` and string conversions.
 */
@HiddenFromObjC
public enum class CaseStyle {
    CAMEL_CASE,
    PASCAL_CASE,
    KEBAB_CASE,
    SNAKE_CASE,
    SCREAMING_SNAKE_CASE,
    SCREAMING_KEBAB_CASE,
    LOWERCASE,
    UPPERCASE,
    TITLE_CASE,
    TRAIN_CASE,
    ;

    /**
     * Converts the given [input] string to this case style.
     */
    public fun convert(input: String): String {
        val words = splitWords(input)
        if (words.isEmpty()) return ""

        return when (this) {
            CAMEL_CASE -> {
                words
                    .mapIndexed { index, word ->
                        if (index == 0) {
                            word.lowercase()
                        } else {
                            capitalizeWord(word)
                        }
                    }.joinToString("")
            }

            PASCAL_CASE -> {
                words.joinToString("") { capitalizeWord(it) }
            }

            KEBAB_CASE -> {
                words.joinToString("-") { it.lowercase() }
            }

            SNAKE_CASE -> {
                words.joinToString("_") { it.lowercase() }
            }

            SCREAMING_SNAKE_CASE -> {
                words.joinToString("_") { it.uppercase() }
            }

            SCREAMING_KEBAB_CASE -> {
                words.joinToString("-") { it.uppercase() }
            }

            LOWERCASE -> {
                words.joinToString("") { it.lowercase() }
            }

            UPPERCASE -> {
                words.joinToString("") { it.uppercase() }
            }

            TITLE_CASE -> {
                words.joinToString(" ") { capitalizeWord(it) }
            }

            TRAIN_CASE -> {
                words.joinToString("-") { capitalizeWord(it) }
            }
        }
    }

    public companion object {
        /**
         * Parses a case style name as used in `serialize_all = "..."`.
         */
        public fun fromStyleName(name: String): CaseStyle? =
            when (name.trim()) {
                "camelCase", "mixed_case" -> {
                    CAMEL_CASE
                }

                // In upstream strum, "camel_case" historically behaved as PascalCase:
                "camel_case", "PascalCase", "pascal_case" -> {
                    PASCAL_CASE
                }

                "kebab-case", "kebab_case" -> {
                    KEBAB_CASE
                }

                "snake_case", "lower_underscore" -> {
                    SNAKE_CASE
                }

                "SCREAMING_SNAKE_CASE", "screaming_snake_case" -> {
                    SCREAMING_SNAKE_CASE
                }

                "SCREAMING-KEBAB-CASE", "SCREAMING_KEBAB_CASE", "screaming_kebab_case", "screaming-kebab-case" -> {
                    SCREAMING_KEBAB_CASE
                }

                "lowercase" -> {
                    LOWERCASE
                }

                "UPPERCASE", "uppercase" -> {
                    UPPERCASE
                }

                "title_case", "title case", "Title Case" -> {
                    TITLE_CASE
                }

                "Train-Case", "Train_Case", "train_case" -> {
                    TRAIN_CASE
                }

                else -> {
                    null
                }
            }
    }
}

/**
 * Converts [input] according to the case style named by [styleName].
 * If [styleName] is unrecognized or blank, returns [input] unchanged.
 */
@HiddenFromObjC
public fun convertCase(input: String, styleName: String): String {
    val style = CaseStyle.fromStyleName(styleName) ?: return input
    return style.convert(input)
}

private enum class CharKind {
    UPPER,
    LOWER,
    DIGIT,
    OTHER,
}

internal fun splitWords(input: String): List<String> {
    if (input.isEmpty()) return emptyList()
    val words = mutableListOf<String>()
    val current = StringBuilder()
    var prevKind: CharKind? = null

    for (i in input.indices) {
        val c = input[i]
        if (c == '_' || c == '-' || c.isWhitespace()) {
            if (current.isNotEmpty()) {
                words.add(current.toString())
                current.clear()
            }
            prevKind = null
            continue
        }

        val kind =
            when {
                c.isUpperCase() -> CharKind.UPPER
                c.isLowerCase() -> CharKind.LOWER
                c.isDigit() -> CharKind.DIGIT
                else -> CharKind.OTHER
            }

        if (prevKind != null) {
            val nextChar = input.getOrNull(i + 1)
            val isNextLower = nextChar != null && nextChar.isLowerCase()

            if ((prevKind == CharKind.LOWER || prevKind == CharKind.DIGIT) && kind == CharKind.UPPER) {
                words.add(current.toString())
                current.clear()
            } else if (prevKind == CharKind.UPPER && kind == CharKind.UPPER && isNextLower) {
                words.add(current.toString())
                current.clear()
            }
        }

        current.append(c)
        prevKind = kind
    }

    if (current.isNotEmpty()) {
        words.add(current.toString())
    }
    return words
}

private fun capitalizeWord(word: String): String {
    if (word.isEmpty()) return ""
    return word.first().uppercase() + word.drop(1).lowercase()
}
