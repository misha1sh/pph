package game.logic.battle.engine

import com.github.servb.pph.pheroes.game.*
import com.soywiz.korma.geom.IPointInt
import game.views.control.BattleNavMode
import game.views.control.iBattleView
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EventsFabricTest : StringSpec({
    "createMeleeEntry" {
        val wrapper = iInteractBattle()
        val fabric = EventsFabric(wrapper.Engine())

        var i = 10;
        val entry = fabric.createEntry(
                battleMode = BattleNavMode.MELEE,
                pos = IPointInt.invoke(1, 2),
                dir = UShort.MAX_VALUE,
                orient = null,
                penalty = null
        );

        (entry is iBattleView.iMeleeEntry) shouldBe true

        val melee = entry as iBattleView.iMeleeEntry

        melee.m_pos shouldBe IPointInt.invoke(1, 2)
        melee.dir shouldBe UShort.MAX_VALUE
    }

    "createShootEntry" {
        val wrapper = iInteractBattle()
        val fabric = EventsFabric(wrapper.Engine())

        var i = 10;
        val entry = fabric.createEntry(
                battleMode = BattleNavMode.SHOOT,
                pos = IPointInt.invoke(1, 2),
                dir = null,
                orient = null,
                penalty = 10
        );

        (entry is iBattleView.iShootEntry) shouldBe true

        val shoot = entry as iBattleView.iShootEntry
        shoot.m_pos shouldBe IPointInt.invoke(1, 2)
        shoot.m_penalty shouldBe 10
    }

    "createMoveEntry" {
        val wrapper = iInteractBattle()
        val fabric = EventsFabric(wrapper.Engine())

        var i = 10;
        val entry = fabric.createEntry(
                battleMode = BattleNavMode.MOVE,
                pos = IPointInt.invoke(1, 2),
                dir = null,
                orient = iBattleGroup.ORIENT.Right,
                penalty = null
        );

        (entry is iBattleView.iMoveEntry) shouldBe true

        val move = entry as iBattleView.iMoveEntry
        move.m_pos shouldBe IPointInt.invoke(1, 2)
        move.orient shouldBe iBattleGroup.ORIENT.Right
    }

})