package com.github.servb.pph.pheroes.game

import com.soywiz.korma.geom.IPointInt
import com.soywiz.korma.geom.PointInt

abstract class BattleEvent {
    var engine: iBattleEngine;

    constructor(engine: iBattleEngine) {
        this.engine = engine
    }

    abstract fun Process()
}

class ShootEvent : BattleEvent {
    var target: iBattleView.iShootEntry

    constructor(engine: iBattleEngine, target: iBattleView.iShootEntry) : super(engine) {
        this.target = target
    }

    override fun Process() {
        if (target != null) {
            engine.Shot(target.m_pos, target.m_penalty)
        }
    }
}

class MeleeEvent : BattleEvent {
    var target: iBattleView.iMeleeEntry

    constructor(engine: iBattleEngine, target: iBattleView.iMeleeEntry) : super(engine) {
        this.target = target
    }

    override fun Process() {
        if (target != null) {
            engine.Melee(target.m_pos, target.dir)
        }
    }
}

class MoveEvent : BattleEvent {
    var target: iBattleView.iMoveEntry

    constructor(engine: iBattleEngine, target: iBattleView.iMoveEntry) : super(engine) {
        this.target = target
    }

    override fun Process() {
        if (target != null) {
            engine.Move(target.m_pos, target.orient)
        }
    }
}

class EventsFabric(private val engine: iBattleEngine) {
    fun createEntry(battleMode: BattleNavMode, pos: IPointInt?, dir: UShort?, penalty: Int?, orient: iBattleGroup.ORIENT?): iBattleView.Entry? {
        return when {
            battleMode == BattleNavMode.MELEE && pos != null && dir != null -> iBattleView.iMeleeEntry(pos, dir)
            battleMode == BattleNavMode.SHOOT && pos != null && penalty != null -> iBattleView.iShootEntry(pos, penalty)
            battleMode == BattleNavMode.MOVE && pos != null && orient != null -> iBattleView.iMoveEntry(pos, orient)
            else -> null
        }
    }

    fun createFromEntry(entry: iBattleView.Entry): BattleEvent {
        return when (entry) {
            is iBattleView.iMoveEntry -> MoveEvent(engine, entry)
            is iBattleView.iMeleeEntry -> MeleeEvent(engine, entry)
            is iBattleView.iShootEntry -> ShootEvent(engine, entry)
            else -> throw IllegalArgumentException("Unknown entry type $entry")
        }
    }
}

