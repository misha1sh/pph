package com.github.servb.pph.pheroes.game

import com.github.servb.pph.gxlib.*
import com.github.servb.pph.pheroes.common.GfxId
import com.github.servb.pph.pheroes.common.IsoMetric
import com.github.servb.pph.pheroes.common.TextResId
import com.github.servb.pph.pheroes.common.common.CalcCellSeqGame
import com.github.servb.pph.pheroes.common.common.PlayerId
import com.github.servb.pph.pheroes.common.common.SurfaceType
import com.github.servb.pph.pheroes.common.creature.Perk
import com.github.servb.pph.pheroes.common.iSortArray
import com.github.servb.pph.pheroes.common.magic.SpellDisposition
import com.github.servb.pph.util.asPoint
import com.github.servb.pph.util.asSize
import com.github.servb.pph.util.helpertype.*
import com.soywiz.korio.lang.format
import com.soywiz.korma.geom.*
import kotlin.properties.Delegates

abstract class BattleEvent {
    var engine: iBattleEngine;

    constructor(engine: iBattleEngine){
        this.engine = engine
    }
    abstract fun Process(cell: PointInt)
}

class ShootEvent: BattleEvent {
    var target : iBattleView.iShootEntry
    constructor(engine: iBattleEngine, target: iBattleView.iShootEntry): super(engine){
        this.target = target
    }

    override fun Process(cell: PointInt) {
        if (target != null) {
            engine.Shot(target.m_pos, target.m_penalty)
        }
    }
}

class MeleeEvent: BattleEvent {
    var target : iBattleView.iMeleeEntry
    constructor(engine: iBattleEngine, target: iBattleView.iMeleeEntry): super(engine){
        this.target = target
    }

    override fun Process(cell: PointInt) {
        if (target != null) {
            engine.Melee(target.m_pos, target.dir)
        }
    }
}

class MoveEvent: BattleEvent {
    var target : iBattleView.iMoveEntry
    constructor(engine: iBattleEngine, target: iBattleView.iMoveEntry): super(engine){
        this.target = target
    }

    override fun Process(cell: PointInt) {
        if (target != null) {
            engine.Move(target.m_pos, target.orient)
        }
    }
}

