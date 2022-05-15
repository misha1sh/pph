package com.github.servb.pph.pheroes.game

import com.github.servb.pph.network.LocalNetManager
import com.soywiz.korma.geom.IPointInt
import kotlinx.coroutines.channels.Channel

class BattleEngineEventsController(private val engine: iBattleEngine) {
    private val inputMessages = Channel<Any>(Channel.UNLIMITED)
    private val eventsFabric = EventsFabric(engine)

    fun SendMessage(message: Any) {
        gGame.getLocalNetManager()?.sendMessage(message)
        inputMessages.trySend(message).getOrThrow()
    }

    private fun ProcessMessage(message: Any) {
        when (message) {
            is iBattleView.Entry -> {
                val event = eventsFabric.createFromEntry(message)
                engine.BattleNavEvents().trySend(event).getOrThrow()
            }
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