package com.github.servb.pph.config

class CreatureTypes {

    var UNKNOWN: CreatureType
    var PEASANT: CreatureType
    var ARCHER: CreatureType
    var PIKEMAN: CreatureType
    var MONK: CreatureType
    var CAVALRY: CreatureType
    var PALADIN: CreatureType
    var GOBLIN: CreatureType
    var ORC: CreatureType
    var WARG_RIDER: CreatureType
    var OGRE: CreatureType
    var TROLL: CreatureType
    var CYCLOP: CreatureType
    var YOUNG_MAGE: CreatureType
    var WHITE_WOLF: CreatureType
    var LIVING_ARMOR: CreatureType
    var PEGASUS: CreatureType
    var MAGE: CreatureType
    var THOR: CreatureType
    var CENTAUR: CreatureType
    var GARGOYLE: CreatureType
    var GRIFFIN: CreatureType
    var MINOTAUR: CreatureType
    var HYDRA: CreatureType
    var RED_DRAGON: CreatureType
    var SPRITE: CreatureType
    var DWARF: CreatureType
    var ELF: CreatureType
    var DRUID: CreatureType
    var UNICORN: CreatureType
    var FIREBIRD: CreatureType
    var SKELETON: CreatureType
    var ZOMBIE: CreatureType
    var LICH: CreatureType
    var VAMPIRE: CreatureType
    var BLACK_KNIGHT: CreatureType
    var PLAGUE: CreatureType
    var ROGUE: CreatureType
    var NOMAD: CreatureType
    var GHOST: CreatureType
    var GENIE: CreatureType
    var MEDUSA: CreatureType
    var EARTH_ELEMENTAL: CreatureType
    var AIR_ELEMENTAL: CreatureType
    var FIRE_ELEMENTAL: CreatureType
    var WATER_ELEMENTAL: CreatureType

    var RANDOM: CreatureType
    var RANDOM_L1: CreatureType
    var RANDOM_L2: CreatureType
    var RANDOM_L3: CreatureType
    var RANDOM_L4: CreatureType
    var RANDOM_L5: CreatureType
    var RANDOM_L6: CreatureType

    var typeByName: HashMap<String, CreatureType> = HashMap()
    var array: Array<CreatureType?>

    constructor(yaml: LinkedHashMap<String, Any?>) {
        UNKNOWN = CreatureType(yaml["UNKNOWN"] as LinkedHashMap<String, Any?>)
        typeByName["UNKNOWN"] = UNKNOWN

        PEASANT = CreatureType(yaml["PEASANT"] as LinkedHashMap<String, Any?>)
        typeByName["PEASANT"] = PEASANT

        ARCHER = CreatureType(yaml["ARCHER"] as LinkedHashMap<String, Any?>)
        typeByName["ARCHER"] = ARCHER

        PIKEMAN = CreatureType(yaml["PIKEMAN"] as LinkedHashMap<String, Any?>)
        typeByName["PIKEMAN"] = PIKEMAN

        MONK = CreatureType(yaml["MONK"] as LinkedHashMap<String, Any?>)
        typeByName["MONK"] = MONK

        CAVALRY = CreatureType(yaml["CAVALRY"] as LinkedHashMap<String, Any?>)
        typeByName["CAVALRY"] = CAVALRY

        PALADIN = CreatureType(yaml["PALADIN"] as LinkedHashMap<String, Any?>)
        typeByName["PALADIN"] = PALADIN

        GOBLIN = CreatureType(yaml["GOBLIN"] as LinkedHashMap<String, Any?>)
        typeByName["GOBLIN"] = GOBLIN

        ORC = CreatureType(yaml["ORC"] as LinkedHashMap<String, Any?>)
        typeByName["ORC"] = ORC

        WARG_RIDER = CreatureType(yaml["WARG_RIDER"] as LinkedHashMap<String, Any?>)
        typeByName["WARG_RIDER"] = WARG_RIDER

        OGRE = CreatureType(yaml["OGRE"] as LinkedHashMap<String, Any?>)
        typeByName["OGRE"] = OGRE

        TROLL = CreatureType(yaml["TROLL"] as LinkedHashMap<String, Any?>)
        typeByName["TROLL"] = TROLL

        CYCLOP = CreatureType(yaml["CYCLOP"] as LinkedHashMap<String, Any?>)
        typeByName["CYCLOP"] = CYCLOP

        YOUNG_MAGE = CreatureType(yaml["YOUNG_MAGE"] as LinkedHashMap<String, Any?>)
        typeByName["YOUNG_MAGE"] = YOUNG_MAGE

        WHITE_WOLF = CreatureType(yaml["WHITE_WOLF"] as LinkedHashMap<String, Any?>)
        typeByName["WHITE_WOLF"] = WHITE_WOLF

        LIVING_ARMOR = CreatureType(yaml["LIVING_ARMOR"] as LinkedHashMap<String, Any?>)
        typeByName["LIVING_ARMOR"] = LIVING_ARMOR

        PEGASUS = CreatureType(yaml["PEGASUS"] as LinkedHashMap<String, Any?>)
        typeByName["PEGASUS"] = PEGASUS

        MAGE = CreatureType(yaml["MAGE"] as LinkedHashMap<String, Any?>)
        typeByName["MAGE"] = MAGE

        THOR = CreatureType(yaml["THOR"] as LinkedHashMap<String, Any?>)
        typeByName["THOR"] = THOR

        CENTAUR = CreatureType(yaml["CENTAUR"] as LinkedHashMap<String, Any?>)
        typeByName["CENTAUR"] = CENTAUR

        GARGOYLE = CreatureType(yaml["GARGOYLE"] as LinkedHashMap<String, Any?>)
        typeByName["GARGOYLE"] = GARGOYLE

        GRIFFIN = CreatureType(yaml["GRIFFIN"] as LinkedHashMap<String, Any?>)
        typeByName["GRIFFIN"] = GRIFFIN

        MINOTAUR = CreatureType(yaml["MINOTAUR"] as LinkedHashMap<String, Any?>)
        typeByName["MINOTAUR"] = MINOTAUR

        HYDRA = CreatureType(yaml["HYDRA"] as LinkedHashMap<String, Any?>)
        typeByName["HYDRA"] = HYDRA

        RED_DRAGON = CreatureType(yaml["RED_DRAGON"] as LinkedHashMap<String, Any?>)
        typeByName["RED_DRAGON"] = RED_DRAGON

        SPRITE = CreatureType(yaml["SPRITE"] as LinkedHashMap<String, Any?>)
        typeByName["SPRITE"] = SPRITE

        DWARF = CreatureType(yaml["DWARF"] as LinkedHashMap<String, Any?>)
        typeByName["DWARF"] = DWARF

        ELF = CreatureType(yaml["ELF"] as LinkedHashMap<String, Any?>)
        typeByName["ELF"] = ELF

        DRUID = CreatureType(yaml["DRUID"] as LinkedHashMap<String, Any?>)
        typeByName["DRUID"] = DRUID

        UNICORN = CreatureType(yaml["UNICORN"] as LinkedHashMap<String, Any?>)
        typeByName["UNICORN"] = UNICORN

        FIREBIRD = CreatureType(yaml["FIREBIRD"] as LinkedHashMap<String, Any?>)
        typeByName["FIREBIRD"] = FIREBIRD

        SKELETON = CreatureType(yaml["SKELETON"] as LinkedHashMap<String, Any?>)
        typeByName["SKELETON"] = SKELETON

        ZOMBIE = CreatureType(yaml["ZOMBIE"] as LinkedHashMap<String, Any?>)
        typeByName["ZOMBIE"] = ZOMBIE

        LICH = CreatureType(yaml["LICH"] as LinkedHashMap<String, Any?>)
        typeByName["LICH"] = LICH

        VAMPIRE = CreatureType(yaml["VAMPIRE"] as LinkedHashMap<String, Any?>)
        typeByName["VAMPIRE"] = VAMPIRE

        BLACK_KNIGHT = CreatureType(yaml["BLACK_KNIGHT"] as LinkedHashMap<String, Any?>)
        typeByName["BLACK_KNIGHT"] = BLACK_KNIGHT

        PLAGUE = CreatureType(yaml["PLAGUE"] as LinkedHashMap<String, Any?>)
        typeByName["PLAGUE"] = PLAGUE

        ROGUE = CreatureType(yaml["ROGUE"] as LinkedHashMap<String, Any?>)
        typeByName["ROGUE"] = ROGUE

        NOMAD = CreatureType(yaml["NOMAD"] as LinkedHashMap<String, Any?>)
        typeByName["NOMAD"] = NOMAD

        GHOST = CreatureType(yaml["GHOST"] as LinkedHashMap<String, Any?>)
        typeByName["GHOST"] = GHOST

        GENIE = CreatureType(yaml["GENIE"] as LinkedHashMap<String, Any?>)
        typeByName["GENIE"] = GENIE

        MEDUSA = CreatureType(yaml["MEDUSA"] as LinkedHashMap<String, Any?>)
        typeByName["MEDUSA"] = MEDUSA

        EARTH_ELEMENTAL = CreatureType(yaml["EARTH_ELEMENTAL"] as LinkedHashMap<String, Any?>)
        typeByName["EARTH_ELEMENTAL"] = EARTH_ELEMENTAL

        AIR_ELEMENTAL = CreatureType(yaml["AIR_ELEMENTAL"] as LinkedHashMap<String, Any?>)
        typeByName["AIR_ELEMENTAL"] = AIR_ELEMENTAL

        FIRE_ELEMENTAL = CreatureType(yaml["FIRE_ELEMENTAL"] as LinkedHashMap<String, Any?>)
        typeByName["FIRE_ELEMENTAL"] = FIRE_ELEMENTAL

        WATER_ELEMENTAL = CreatureType(yaml["WATER_ELEMENTAL"] as LinkedHashMap<String, Any?>)
        typeByName["WATER_ELEMENTAL"] = WATER_ELEMENTAL

        RANDOM = CreatureType(yaml["RANDOM"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM"] = RANDOM

        RANDOM_L1 = CreatureType(yaml["RANDOM_L1"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L1"] = RANDOM_L1

        RANDOM_L2 = CreatureType(yaml["RANDOM_L2"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L2"] = RANDOM_L2

        RANDOM_L3 = CreatureType(yaml["RANDOM_L3"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L3"] = RANDOM_L3

        RANDOM_L4 = CreatureType(yaml["RANDOM_L4"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L4"] = RANDOM_L4

        RANDOM_L5 = CreatureType(yaml["RANDOM_L5"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L5"] = RANDOM_L5

        RANDOM_L6 = CreatureType(yaml["RANDOM_L6"] as LinkedHashMap<String, Any?>)
        typeByName["RANDOM_L6"] = RANDOM_L6


        array = arrayOf(
            UNKNOWN,
            PEASANT,
            ARCHER,
            PIKEMAN,
            MONK,
            CAVALRY,
            PALADIN,
            GOBLIN,
            ORC,
            WARG_RIDER,
            OGRE,
            TROLL,
            CYCLOP,
            YOUNG_MAGE,
            WHITE_WOLF,
            LIVING_ARMOR,
            PEGASUS,
            MAGE,
            THOR,
            CENTAUR,
            GARGOYLE,
            GRIFFIN,
            MINOTAUR,
            HYDRA,
            RED_DRAGON,
            SPRITE,
            DWARF,
            ELF,
            DRUID,
            UNICORN,
            FIREBIRD,
            SKELETON,
            ZOMBIE,
            LICH,
            VAMPIRE,
            BLACK_KNIGHT,
            PLAGUE,
            ROGUE,
            NOMAD,
            GHOST,
            GENIE,
            MEDUSA,
            EARTH_ELEMENTAL,
            AIR_ELEMENTAL,
            FIRE_ELEMENTAL,
            WATER_ELEMENTAL,
        )

    }

    fun count(): Int{
        return array.size - 1;
    }

    operator fun get(indx: Int): CreatureType?{
        return array[indx]
    }

}