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

        val expected = iBattleView.iMeleeEntry(IPointInt.invoke(1, 2), UShort.MAX_VALUE)
        entry shouldBe expected
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

        val expected = iBattleView.iShootEntry(IPointInt.invoke(1, 2), 10)
        entry shouldBe expected
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

        val expected = iBattleView.iMoveEntry(IPointInt.invoke(1, 2), iBattleGroup.ORIENT.Right)
        entry shouldBe expected
    }

})