package com.github.servb.pph.config

import com.github.servb.pph.pheroes.common.common.IdeologyType
import com.github.servb.pph.pheroes.common.common.MineralSet
import com.github.servb.pph.pheroes.common.common.MineralSetC
import com.github.servb.pph.pheroes.common.creature.TransportationType
import com.soywiz.korio.lang.InvalidArgumentException


fun createMineralSet(yaml: LinkedHashMap<String, Any?>?): MineralSet?{
    if(yaml == null) return null

    return MineralSet(
        yaml["gold"] as Int,
        yaml["ore"] as Int,
        yaml["wood"] as Int,
        yaml["mercury"] as Int,
        yaml["gem"] as Int,
        yaml["crystal"] as Int,
        yaml["sulfur"] as Int
    )
}

fun createIdeologyType(str: String): IdeologyType {
    if (str == "neutral") return IdeologyType.NEUTRAL
    else if (str == "good") return IdeologyType.GOOD
    else if (str == "evil") return IdeologyType.EVIL
    else if (str == "count") return IdeologyType.COUNT
    else{
        throw InvalidArgumentException()
    }
}

fun createTransportationType(str: String): TransportationType {
    if(str == "WALK") return TransportationType.WALK
    else if (str == "FLY") return TransportationType.FLY
    else throw InvalidArgumentException()
}

fun createCreatureDescriptor(yaml: LinkedHashMap<String, Any?>): CreatureDescriptor{
    return CreatureDescriptor(
        level = yaml["level"] as Int,
        nation = Data.nationTypes!!.nationTypeByName[yaml["nation"] as String]!!,
        attack = yaml["attack"] as Int,
        defence = yaml["defence"] as Int,
        speed = yaml["speed"] as Int,
        size = yaml["size"] as Int,
        createTransportationType(yaml["transType"] as String),
        shots = yaml["shots"] as Int,
        hits = yaml["hits"] as Int,
        damage_min = yaml["damage_min"] as Int,
        damage_max = yaml["damage_max"] as Int,
        cost = createMineralSet(yaml["cost"] as LinkedHashMap<String, Any?>) as MineralSetC,
        growth = yaml["growth"] as Int,
        pidx = yaml["pidx"] as Int,
        perks = Data.perks!!.perkByName[yaml["perks"] as String]!!.v
    )
}