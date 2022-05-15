package com.github.servb.pph.pheroes.game

import com.github.servb.pph.network.LocalNetManager
import com.github.servb.pph.network.ShotMessage
import com.soywiz.korma.geom.IPointInt
import kotlinx.coroutines.channels.Channel

class BattleController(private val engine: iBattleEngine) {
    private val inputMessages = Channel<Any>(Channel.UNLIMITED)

    fun ShotAction(pos: IPointInt, penalty: Int) {
        SendMessage(ShotMessage(pos, penalty))
    }

    fun SendMessage(message: Any) {
        gGame.getLocalNetManager()?.sendMessage(message)
        inputMessages.trySend(message).getOrThrow()
    }

    fun ProcessMessage(message: Any) {
        when (message) {
            is ShotMessage -> engine.Shot(message.pos, message.penalty)
            else -> throw IllegalArgumentException("Wrong type `$message`")
        }
    }

    fun ProcessMessages() {
        while (true) {
            val message: Any = gGame.getLocalNetManager()?.inputMessages()?.tryReceive()?.getOrNull()
                ?: break
            inputMessages.trySend(message).getOrThrow()
        }
        while (true) {
            val message: Any = inputMessages.tryReceive().getOrNull() ?: break
            ProcessMessage(message)
        }
    }
}