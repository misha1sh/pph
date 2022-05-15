package game.logic.players

import com.github.servb.pph.util.helpertype.CountValueEnum
import com.github.servb.pph.util.helpertype.UniqueValueEnum

enum class PlayerType(override val v: Int) : UniqueValueEnum, CountValueEnum {
    UNDEFINED(0),
    HUMAN(1),
    COMPUTER(2),
    COUNT(3);
}
