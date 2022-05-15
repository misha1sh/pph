package com.github.servb.pph.config

class Perks {

    var NONE: Perk
    var DOUBLESHOT: Perk
    var DOUBLEATTACK: Perk
    var NOMELEEPENALTY: Perk
    var NONRETALATTACK: Perk
    var RETALTOALL: Perk
    var TWOHEXATTACK: Perk
    var ADJACENTATTACK: Perk
    var LICHSHOOT: Perk
    var UNDEAD: Perk

    var LIFELESS: Perk
    var REGENERATES: Perk
    var JOUSTING: Perk
    var AIRMAGICIMM: Perk
    var EARTHMAGICIMM: Perk
    var FIREMAGICIMM: Perk
    var WATERMAGICIMM: Perk
    var PROCRESIST40: Perk
    var QUARTERDMG: Perk
    var GHOST: Perk

    var DOHALF: Perk
    var DRAINLIFES: Perk
    var ALLMAGICIMM: Perk

    var perkByName: HashMap<String, Perk> = HashMap()

    constructor(yaml: LinkedHashMap<String, Any?>){

        NONE = Perk(yaml["NONE"] as LinkedHashMap<String, Any?>)
        perkByName["NONE"] = NONE

        DOUBLESHOT = Perk(yaml["DOUBLESHOT"] as LinkedHashMap<String, Any?>)
        perkByName["DOUBLESHOT"] = DOUBLESHOT

        DOUBLEATTACK = Perk(yaml["DOUBLEATTACK"] as LinkedHashMap<String, Any?>)
        perkByName["DOUBLEATTACK"] = DOUBLEATTACK

        NOMELEEPENALTY = Perk(yaml["NOMELEEPENALTY"] as LinkedHashMap<String, Any?>)
        perkByName["NOMELEEPENALTY"] = NOMELEEPENALTY

        NONRETALATTACK = Perk(yaml["NONRETALATTACK"] as LinkedHashMap<String, Any?>)
        perkByName["NONRETALATTACK"] = NONRETALATTACK

        RETALTOALL = Perk(yaml["RETALTOALL"] as LinkedHashMap<String, Any?>)
        perkByName["RETALTOALL"] = RETALTOALL

        TWOHEXATTACK = Perk(yaml["TWOHEXATTACK"] as LinkedHashMap<String, Any?>)
        perkByName["TWOHEXATTACK"] = TWOHEXATTACK

        ADJACENTATTACK = Perk(yaml["ADJACENTATTACK"] as LinkedHashMap<String, Any?>)
        perkByName["ADJACENTATTACK"] = ADJACENTATTACK

        LICHSHOOT = Perk(yaml["LICHSHOOT"] as LinkedHashMap<String, Any?>)
        perkByName["LICHSHOOT"] = LICHSHOOT

        UNDEAD = Perk(yaml["UNDEAD"] as LinkedHashMap<String, Any?>)
        perkByName["UNDEAD"] = UNDEAD

        LIFELESS = Perk(yaml["LIFELESS"] as LinkedHashMap<String, Any?>)
        perkByName["LIFELESS"] = LIFELESS

        REGENERATES = Perk(yaml["REGENERATES"] as LinkedHashMap<String, Any?>)
        perkByName["REGENERATES"] = REGENERATES

        JOUSTING = Perk(yaml["JOUSTING"] as LinkedHashMap<String, Any?>)
        perkByName["JOUSTING"] = JOUSTING

        AIRMAGICIMM = Perk(yaml["AIRMAGICIMM"] as LinkedHashMap<String, Any?>)
        perkByName["AIRMAGICIMM"] = AIRMAGICIMM

        EARTHMAGICIMM = Perk(yaml["EARTHMAGICIMM"] as LinkedHashMap<String, Any?>)
        perkByName["EARTHMAGICIMM"] = EARTHMAGICIMM

        FIREMAGICIMM = Perk(yaml["FIREMAGICIMM"] as LinkedHashMap<String, Any?>)
        perkByName["FIREMAGICIMM"] = FIREMAGICIMM

        WATERMAGICIMM = Perk(yaml["WATERMAGICIMM"] as LinkedHashMap<String, Any?>)
        perkByName["WATERMAGICIMM"] = WATERMAGICIMM

        PROCRESIST40 = Perk(yaml["PROCRESIST40"] as LinkedHashMap<String, Any?>)
        perkByName["PROCRESIST40"] = PROCRESIST40

        QUARTERDMG = Perk(yaml["QUARTERDMG"] as LinkedHashMap<String, Any?>)
        perkByName["QUARTERDMG"] = QUARTERDMG

        GHOST = Perk(yaml["GHOST"] as LinkedHashMap<String, Any?>)
        perkByName["GHOST"] = GHOST

        DOHALF = Perk(yaml["DOHALF"] as LinkedHashMap<String, Any?>)
        perkByName["DOHALF"] = DOHALF

        DRAINLIFES = Perk(yaml["DRAINLIFES"] as LinkedHashMap<String, Any?>)
        perkByName["DRAINLIFES"] = DRAINLIFES


        ALLMAGICIMM = Perk(AIRMAGICIMM.v or EARTHMAGICIMM.v or FIREMAGICIMM.v or WATERMAGICIMM.v, "Black draggons")
        perkByName["ALLMAGICIMM"] = ALLMAGICIMM
    }


}