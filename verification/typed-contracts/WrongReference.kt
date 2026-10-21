package contract
import io.github.kotlinmania.strum.AsStaticRef
class WrongReference : AsStaticRef<Int> {
    override fun asStatic(): String = "wrong"
}
