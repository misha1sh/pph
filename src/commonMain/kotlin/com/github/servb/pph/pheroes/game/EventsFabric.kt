package com.github.servb.pph.pheroes.game

class EventsFabric {
    fun create(battleMode: BattleNavMode): Any {
        return when (battleMode) {
            BattleNavMode.COUNT -> {}
            BattleNavMode.INFO -> {}
            BattleNavMode.MELEE -> {}
            BattleNavMode.SHOOT -> {}
            else -> throw RuntimeException()
        }
    }
}