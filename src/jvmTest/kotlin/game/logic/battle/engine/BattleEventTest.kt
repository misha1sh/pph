package com.github.servb.pph.pheroes.game.logic.engine

import com.github.servb.pph.pheroes.game.*
import com.soywiz.korma.geom.IPointInt
import com.soywiz.korma.geom.PointInt
import game.views.control.BattleNavMode
import game.views.control.iBattleView
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FakeBattleEngine : iBattleEngine {
    constructor(pWrapper: iBattleWrapper) : super(pWrapper, true) {
    }
    var shot = false
    var shotPos: IPointInt? = null
    var shotPenalty: Int? = null
    override fun Shot(pos: IPointInt, penalty: Int) {
        shot = true;
        shotPos = pos
        shotPenalty = penalty
    }

    var move = false
    var movePos: IPointInt? = null
    var moveOrient: iBattleGroup.ORIENT? = null
    override fun Move(pos: IPointInt, orient: iBattleGroup.ORIENT)  {
        move = true;
        movePos = pos
        moveOrient = orient
    }

    var melee = false
    var meleePos: IPointInt? = null
    var meleeDir: UShort? = null
    override fun Melee(pos: IPointInt, mdir: UShort) {
        melee = true;
        meleePos = pos
        meleeDir = mdir
    }
}

class FakeBattleWrapper : iBattleWrapper {
    constructor() : super(false) {
        m_engine = FakeBattleEngine(this)
    }

    override suspend fun OnBeginBattle() {
    }
    override fun OnStart() {
    }
    override suspend fun OnEndBattle() {
    }
    override fun AddLogEvent(msg: String) {
    }
    override fun AddCellEvent(msg: String, pos: IPointInt) {
    }
}


class BattleEventTest : StringSpec({
    "ShootEvent" {
        val battle = FakeBattleWrapper()
        val engine = battle.Engine() as FakeBattleEngine

        val fabric = EventsFabric(engine)
        val entry = fabric.createEntry(BattleNavMode.SHOOT, PointInt(0, 1), 2.toUShort(),
                    3, iBattleGroup.ORIENT.Left)
        (entry is iBattleView.iShootEntry) shouldBe true

        val event = fabric.createFromEntry(entry!!)
        (event is ShootEvent) shouldBe true

        event.Process()
        engine.shot shouldBe true
        engine.shotPos shouldBe PointInt(0, 1)
        engine.shotPenalty shouldBe 3
    }

    "MoveEvent" {
        val battle = FakeBattleWrapper()
        val engine = battle.Engine() as FakeBattleEngine

        val fabric = EventsFabric(engine)
        val entry = fabric.createEntry(BattleNavMode.MOVE, PointInt(0, 1), 2.toUShort(),
            3, iBattleGroup.ORIENT.Left)
        (entry is iBattleView.iMoveEntry) shouldBe true

        val event = fabric.createFromEntry(entry!!)
        (event is MoveEvent) shouldBe true

        event.Process()
        engine.move shouldBe true
        engine.movePos shouldBe PointInt(0, 1)
        engine.moveOrient shouldBe iBattleGroup.ORIENT.Left
    }

    "MeleeEvent" {
        val battle = FakeBattleWrapper()
        val engine = battle.Engine() as FakeBattleEngine

        val fabric = EventsFabric(engine)
        val entry = fabric.createEntry(BattleNavMode.MELEE, PointInt(0, 1), 2.toUShort(),
            3, iBattleGroup.ORIENT.Left)
        (entry is iBattleView.iMeleeEntry) shouldBe true

        val event = fabric.createFromEntry(entry!!)
        (event is MeleeEvent) shouldBe true

        event.Process()
        engine.melee shouldBe true
        engine.meleePos shouldBe PointInt(0, 1)
        engine.meleeDir shouldBe 2
    }

})