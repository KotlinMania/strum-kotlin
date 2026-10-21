package consumer

import io.github.kotlinmania.strum.ParseError
import io.github.kotlinmania.strum.fromString

public enum class ConsumerColor {
    Red,
    Blue,
}

public fun parseColor(value: String): ConsumerColor = fromString(value)

public fun parseErrorMessage(): String = ParseError.VARIANT_NOT_FOUND.fmt()
