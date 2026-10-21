package contract
import io.github.kotlinmania.strum.VariantArray
class WrongVariants : VariantArray<Int> {
    override val variants: List<String> = listOf("wrong")
}
