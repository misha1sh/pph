import com.github.servb.pph.pheroes.common.creature.Perk
import com.github.servb.pph.pheroes.game.*
import com.github.servb.pph.util.Mutable
import com.github.servb.pph.util.SizeT
import com.github.servb.pph.util.helpertype.UniqueValueEnum
import com.github.servb.pph.util.helpertype.or
import com.soywiz.korma.geom.IPointInt
import com.soywiz.korma.geom.PointInt

private val ANI_SPEED_COEF = listOf(2.0, 1.5, 1.0, 0.75, 0.5)

abstract class iBattleAct {

    enum class Type(override val v: Int) : UniqueValueEnum {
        Move(0),  // Move to specified cell (async)
        Rotate(1),  // Change group's orientation (async)
        Attack(2),  // Melee attack specified cell
        Shoot(3),  // Shoot to specified cell
        Delay(4),  // Delay
        GoodMorale(5),  // Good morale
        BadMorale(6),  // Bad morale
        GoodLuck(7),  // Good luck
        BadLuck(8),  // Bad luck
        CastSpell(9),  // Casting spell to specified cell
        Catapult(10),  // Catapult shot
        Turret(11),  // Turret shot
        Gate(12),  // Open/Close gate
    }

    val m_delay: Double

    constructor(delay: Double) {
        m_delay = delay * ANI_SPEED_COEF[gSettings.GetEntryValue(ConfigEntryType.COMBATSPEED)]
    }

    abstract fun IsValid(): Boolean
    open fun Normalize(): Boolean = true
    abstract fun BeginAct()
    abstract fun EndAct()
}

class iBattleAct_Move : iBattleAct {

    val m_bridgeFlag: UByte
    val m_pActor: iBattleGroup
    val m_target: IPointInt

    constructor(pActor: iBattleGroup, target: IPointInt) : super(0.3) {
        m_pActor = pActor
        m_target = target
        m_bridgeFlag = 0u
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.SetState(iBattleGroup.STATE.Moving)
    }

    override fun EndAct() {
        m_pActor.Place(m_target)
        m_pActor.SetState(iBattleGroup.STATE.Idle)
    }
}

class iBattleAct_Rotate : iBattleAct {

    val m_pActor: iBattleGroup

    constructor(pActor: iBattleGroup) : super(0.3) {
        m_pActor = pActor
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.SetState(iBattleGroup.STATE.Rotating)
    }

    override fun EndAct() {
        m_pActor.Rotate()
        m_pActor.SetState(iBattleGroup.STATE.Idle)
    }
}

class iBattleAct_Attack : iBattleAct {

    val m_luck: iBattleGroup.MORLUCK_MOD
    val m_bRetail: Boolean
    val m_penalty: Int
    val m_pActor: iBattleGroup
    val m_cell: IPointInt
    val m_targetList: MutableList<iBattleGroup> = mutableListOf()
    val m_joustBonus: SizeT

    constructor(
            pActor: iBattleGroup,
            cell: IPointInt,
            pTarget: iBattleGroup,
            penalty: Int,
            joustBonus: SizeT,
            bRetail: Boolean,
            luck: iBattleGroup.MORLUCK_MOD
    ) : super(0.6) {
        m_pActor = pActor
        m_cell = cell
        m_penalty = penalty
        m_joustBonus = joustBonus
        m_bRetail = bRetail
        m_luck = luck
        m_targetList.add(pTarget)
    }

    override fun Normalize(): Boolean {
        m_targetList.retainAll { it.IsAlive() }
        return m_targetList.isNotEmpty()
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.SetState(iBattleGroup.STATE.Melee)
        m_targetList.forEach { it.SetState(iBattleGroup.STATE.RecDamage) }
    }

    override fun EndAct() {
        m_targetList.forEach {
            m_pActor.Attack(it, 0, m_penalty, m_joustBonus, m_luck)
            if (it.IsAlive()) {
                it.SetState(iBattleGroup.STATE.Idle)
            }
        }
        m_pActor.SetState(iBattleGroup.STATE.Idle)
        if (m_bRetail) {
            m_pActor.SetRetailed()
        }
    }
}

class iBattleAct_Shoot : iBattleAct {

    val m_luck: iBattleGroup.MORLUCK_MOD
    val m_penalty: Int
    val m_pActor: iBattleGroup
    val m_cell: IPointInt
    val m_targetList: List<iBattleGroup>

    constructor(
            pActor: iBattleGroup,
            cell: IPointInt,
            pTarget: iBattleGroup,
            penalty: Int,
            luck: iBattleGroup.MORLUCK_MOD
    ) : super(0.5) {
        m_pActor = pActor
        m_cell = cell
        m_penalty = penalty
        m_luck = luck
        m_targetList = listOf(pTarget)
    }

    override fun Normalize(): Boolean {
        // todo
        return m_targetList.isNotEmpty()
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.SetState(iBattleGroup.STATE.Shooting)
        m_targetList.forEach { it.SetState(iBattleGroup.STATE.RecDamage) }
    }

    override fun EndAct() {
        var aflag = iBattleGroup.AttackFlags.RangeAttack.v
        m_targetList.forEachIndexed { xx, it ->
            m_pActor.Attack(it, aflag, m_penalty, 0, m_luck)
            if (it.IsAlive()) {
                it.SetState(iBattleGroup.STATE.Idle)
            }
            if (xx == 0 && m_pActor.HasPerk(Perk.LICHSHOOT)) {
                aflag = aflag or Perk.LICHSHOOT
            }
        }
        m_pActor.SetState(iBattleGroup.STATE.Idle)
    }
}

class iBattleAct_Delay : iBattleAct {

    constructor() : super(0.9)

    override fun IsValid(): Boolean = true

    override fun BeginAct() {
        // nop
    }

    override fun EndAct() {
        // nop
    }
}

class iBattleAct_GoodMorale : iBattleAct {

    val m_pActor: iBattleGroup

    constructor(pActor: iBattleGroup) : super(0.9) {
        m_pActor = pActor
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.GoodMorale()
    }

    override fun EndAct() {
        // nop
    }
}

class iBattleAct_BadMorale  // todo

class iBattleAct_GoodLuck : iBattleAct {

    val m_pActor: iBattleGroup

    constructor(pActor: iBattleGroup) : super(0.9) {
        m_pActor = pActor
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.GoodLuck()
    }

    override fun EndAct() {
        // nop
    }
}

class iBattleAct_BadLuck : iBattleAct {

    val m_pActor: iBattleGroup

    constructor(pActor: iBattleGroup) : super(0.9) {
        m_pActor = pActor
    }

    override fun IsValid(): Boolean = m_pActor.IsAlive()

    override fun BeginAct() {
        m_pActor.BadMorale()
    }

    override fun EndAct() {
        // nop
    }
}

class iBattleAct_CastSpell  // todo
class iBattleAct_Catapult  // todo
class iBattleAct_Turret  // todo
class iBattleAct_Gate  // todo
class iBattleAct_MoatDmg  // todo

typealias iActList = ArrayDeque<iBattleAct>

class iBattleActList {

    private val m_ActList: iActList = ArrayDeque()

    fun EndDir(pActor: iBattleGroup, dir: UByte): UByte {
        var ans = dir
        m_ActList.forEach {
            if (it is iBattleAct_Rotate && it.m_pActor == pActor) {
                ans = when (ans) {
                    0u.toUByte() -> 1u
                    else -> 0u
                }
            }
        }
        return ans
    }

    fun EndDirPos(pActor: iBattleGroup, pos: PointInt, dir: Mutable<UByte>) {
        m_ActList.forEach {
            when {
                it is iBattleAct_Rotate && it.m_pActor == pActor -> {
                    pos.x += TAIL_OFFSET[dir.element.toInt()]
                    dir.element = when (dir.element) {
                        0u.toUByte() -> 1u
                        else -> 0u
                    }
                }
                it is iBattleAct_Move && it.m_pActor == pActor -> {
                    pos.setTo(it.m_target)
                }
            }
        }
    }

    fun Reset(): Unit = m_ActList.clear()
    fun Count(): SizeT = m_ActList.size
    fun AddAction(pAct: iBattleAct): Unit = m_ActList.addLast(pAct)
    fun PushAction(pAct: iBattleAct): Unit = m_ActList.addFirst(pAct)
    fun InsertActionBefore(it: Int, pAct: iBattleAct): Unit = m_ActList.add(it, pAct)
    fun InsertActionAfter(it: Int, pAct: iBattleAct): Unit = m_ActList.add(it + 1, pAct)
    fun StepAction(): iBattleAct? = m_ActList.removeFirstOrNull()
    fun First(): iBattleAct? = m_ActList.firstOrNull()
}
