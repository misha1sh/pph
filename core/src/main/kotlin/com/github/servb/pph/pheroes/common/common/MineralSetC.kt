package com.github.servb.pph.pheroes.common.common

import java.util.*

interface MineralSetC {
    val quant: IntArray
}

fun MineralSetC.Has(ms: MineralSetC): Int {
    var cnt = 0
    for (xx in 0 until MineralType.COUNT.v) {
        if (quant[xx] < ms.quant[xx]) {
            return 0
        }
        if (ms.quant[xx] != 0) {
            cnt = if (cnt != 0) Math.min(cnt, quant[xx] / ms.quant[xx]) else quant[xx] / ms.quant[xx]
        }
    }
    return cnt
}

operator fun MineralSetC.times(`val`: Int): MineralSetC {
    val result = MineralSet()
    for (xx in 0 until MineralType.COUNT.v) {
        result.quant[xx] = quant[xx] * `val`
    }
    return result
}

fun MineralSetC.Empty(): Boolean {
    for (xx in 0 until MineralType.COUNT.v) {
        if (quant[xx] != 0) {
            return false
        }
    }
    return true
}

fun MineralSetC.DeficientAmount(other: MineralSetC): MineralSetC {
    val result = MineralSet()
    for (xx in 0 until MineralType.COUNT.v) {
        if (other.quant[xx] > quant[xx]) {
            result.quant[xx] = other.quant[xx] - quant[xx]
        }
    }
    return result
}

fun MineralSetC.Intersect(other: MineralSetC): MineralSetC {
    val result = MineralSet()
    for (xx in 0 until MineralType.COUNT.v) {
        result.quant[xx] = Math.min(quant[xx], other.quant[xx])
    }
    return result
}

class MineralSet : MineralSetC {

    override var quant: IntArray

    constructor() {
        quant = IntArray(MineralType.COUNT.v)
    }

    constructor(gold: Int, ore: Int, wood: Int, mercury: Int, gem: Int, crystal: Int, sulfur: Int) {
        this.quant = intArrayOf(gold, ore, wood, mercury, gem, crystal, sulfur)
    }

    constructor(quant: IntArray) {
        this.quant = quant.clone()
    }

    fun Reset() {
        Arrays.fill(quant, 0)
    }

    fun Normalize() {
        for (xx in 0 until MineralType.COUNT.v) {
            if (quant[xx] < 0) {
                quant[xx] = 0
            }
        }
    }

    fun setTo(ms: MineralSetC) {
        for (xx in 0 until MineralType.COUNT.v) {
            quant[xx] = ms.quant[xx]
        }
    }

    operator fun plusAssign(ms: MineralSetC) {
        for (xx in 0 until MineralType.COUNT.v) {
            quant[xx] += ms.quant[xx]
        }
    }

    operator fun minusAssign(ms: MineralSetC) {
        for (xx in 0 until MineralType.COUNT.v) {
            quant[xx] -= ms.quant[xx]
        }
    }
}
