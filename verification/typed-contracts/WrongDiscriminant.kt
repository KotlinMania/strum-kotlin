package contract
import io.github.kotlinmania.strum.IntoDiscriminant
class WrongDiscriminant : IntoDiscriminant<Int> {
    override fun discriminant(): String = "wrong"
}
