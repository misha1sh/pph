package com.github.servb.pph.config

import com.soywiz.kmem.NewFast32Buffer

class NationTypes {

    var NEUTRAL: NationType
    var HIGHMEN: NationType
    var BARBARIANS: NationType
    var WIZARDS: NationType
    var BEASTMEN: NationType
    var ELVES: NationType
    var UNDEADS: NationType


    var array: Array<NationType?>

    var nationTypeByName: HashMap<String, NationType> = HashMap()

    constructor(yaml: LinkedHashMap<String, Any?>){
        NEUTRAL = NationType(yaml["NEUTRAL"] as LinkedHashMap<String, Any?>)
        nationTypeByName["NEUTRAL"] = NEUTRAL

        HIGHMEN = NationType(yaml["HIGHMEN"] as LinkedHashMap<String, Any?>)
        nationTypeByName["HIGHMEN"] = HIGHMEN

        BARBARIANS = NationType(yaml["BARBARIANS"] as LinkedHashMap<String, Any?>)
        nationTypeByName["BARBARIANS"] = BARBARIANS

        WIZARDS = NationType(yaml["WIZARDS"] as LinkedHashMap<String, Any?>)
        nationTypeByName["WIZARDS"] = WIZARDS

        BEASTMEN = NationType(yaml["BEASTMEN"] as LinkedHashMap<String, Any?>)
        nationTypeByName["BEASTMEN"] = BEASTMEN

        ELVES = NationType(yaml["ELVES"] as LinkedHashMap<String, Any?>)
        nationTypeByName["ELVES"] = ELVES

        UNDEADS = NationType(yaml["UNDEADS"] as LinkedHashMap<String, Any?>)
        nationTypeByName["UNDEADS"] = UNDEADS

        array = arrayOf(NEUTRAL, HIGHMEN, BARBARIANS, WIZARDS, BEASTMEN, ELVES, UNDEADS)
    }

    fun count(): Int{
        return array.size
    }

    operator fun get(indx: Int): NationType?{
        return array[indx]
    }
}