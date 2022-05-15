package game.logic.players

import game.logic.players.PlayerId
import game.logic.players.PlayerType

open class iPlayer {

    open fun PlayerType(): PlayerType = PlayerType.HUMAN

    fun PlayerId(): PlayerId = PlayerId.CYAN  // todo
}