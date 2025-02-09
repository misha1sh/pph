package com.github.servb.pph.pheroes.common.common

import game.logic.battle.units.skills.FurtherSkill
import com.github.servb.pph.pheroes.common.creature.CreatureType
import com.github.servb.pph.util.helpertype.CountValueEnum
import com.github.servb.pph.util.helpertype.UniqueValueEnum

enum class HeroType(
        override val v: Int,
        val ideology: IdeologyType? = null,
        val ultimateArtifactStandardModifications: Set<Pair<FurtherSkill, Int>>? = null,
        val ultimateArtifactSpecialFlag: SpecialHeroFlag? = null,
        val primarySkill: List<Int>? = null,
        val secondarySkill: List<Int>? = null,
        val initialArmy0: Triple<CreatureType, Int, Int>? = null,
        val initialArmy1: Triple<CreatureType, Int, Int>? = null
) : UniqueValueEnum, CountValueEnum {
    KNIGHT(
            0,
            IdeologyType.GOOD,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 4),
                    Pair(FurtherSkill.KNOWLEDGE, 4),
                    Pair(FurtherSkill.LOGISTICS, 20),
                    Pair(FurtherSkill.SPEED, 2)
            ),
            SpecialHeroFlag.INVALID,
            listOf(25, 50, 5, 20),
            listOf(
                    2, 6, 6, 4, // Estates, Leadership, Luck, Diplomacy
                    1, 1, 0, 2, // Air, Earth, Fire, Water
                    1, 0, 0, 1, 2, 0, 4, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    3, 4, 3, // Scouting, Logistics, Pathfinding,
                    3, 3, 2, 4 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.PEASANT, 25, 50),
            Triple(CreatureType.ARCHER, 0, 7)
    ),

    BARBARIAN(
            1,
            IdeologyType.EVIL,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 4),
                    Pair(FurtherSkill.KNOWLEDGE, 4),
                    Pair(FurtherSkill.RESIST, 30)
            ),
            SpecialHeroFlag.NO_RANGE_PENALTY,
            listOf(60, 25, 5, 10),
            listOf(
                    2, 4, 4, 3, // Estates, Leadership, Luck, Diplomacy
                    1, 1, 2, 0, // Air, Earth, Fire, Water
                    1, 0, 0, 1, 1, 0, 2, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    3, 3, 5, // Scouting, Logistics, Pathfinding,
                    4, 3, 5, 3 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.GOBLIN, 15, 30),
            Triple(CreatureType.ORC, 0, 8)
    ),

    WIZARD(
            2,
            IdeologyType.GOOD,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 6),
                    Pair(FurtherSkill.KNOWLEDGE, 6)
            ),
            SpecialHeroFlag.SUMMON_RESURRECTION_BOUNS,
            listOf(10, 10, 40, 40),
            listOf(
                    2, 2, 2, 3, // Estates, Leadership, Luck, Diplomacy
                    3, 3, 0, 5, // Air, Earth, Fire, Water
                    6, 0, 4, 2, 2, 3, 5, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    3, 2, 2, // Scouting, Logistics, Pathfinding,
                    0, 0, 0, 0 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.YOUNG_MAGE, 10, 20),
            Triple(CreatureType.WHITE_WOLF, 0, 6)
    ),

    WARLOCK(
            3,
            IdeologyType.EVIL,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 6),
                    Pair(FurtherSkill.KNOWLEDGE, 6)
            ),
            SpecialHeroFlag.MANA_RESTORE,
            listOf(15, 10, 45, 30),
            listOf(
                    2, 0, 2, 3, // Estates, Leadership, Luck, Diplomacy
                    3, 3, 5, 0, // Air, Earth, Fire, Water
                    4, 1, 4, 3, 2, 5, 4, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    4, 2, 3, // Scouting, Logistics, Pathfinding,
                    0, 0, 0, 0 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.CENTAUR, 11, 22),
            Triple(CreatureType.GARGOYLE, 0, 8)
    ),

    SORCERESS(
            4,
            IdeologyType.GOOD,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 6),
                    Pair(FurtherSkill.KNOWLEDGE, 6)
            ),
            SpecialHeroFlag.DAMAGE_SPELL_BONUS,
            listOf(15, 15, 30, 40),
            listOf(
                    2, 2, 2, 3, // Estates, Leadership, Luck, Diplomacy
                    3, 3, 0, 5, // Air, Earth, Fire, Water
                    6, 0, 4, 2, 2, 3, 5, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    3, 2, 2, // Scouting, Logistics, Pathfinding,
                    1, 1, 0, 0 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.SPRITE, 14, 28),
            Triple(CreatureType.DWARF, 0, 8)
    ),

    NECROMANCER(
            5,
            IdeologyType.EVIL,
            setOf(
                    Pair(FurtherSkill.MIN_GOLD, 5000),
                    Pair(FurtherSkill.ATTACK, 4),
                    Pair(FurtherSkill.DEFENCE, 4),
                    Pair(FurtherSkill.POWER, 4),
                    Pair(FurtherSkill.KNOWLEDGE, 4),
                    Pair(FurtherSkill.NECROMANCY, 15)
            ),
            SpecialHeroFlag.NECROMANCY_BONUS,
            listOf(15, 15, 40, 30),
            listOf(
                    2, 0, 2, 3, // Estates, Leadership, Luck, Diplomacy
                    3, 3, 5, 0, // Air, Earth, Fire, Water
                    4, 5, 4, 3, 2, 5, 4, // Wisdom, Necromancy, Mysticism, Intelligence, Resistance, Sorcery, Learning
                    4, 2, 3, // Scouting, Logistics, Pathfinding,
                    1, 1, 0, 0 // Archery, Ballistics, Offence, Armorer
            ),
            Triple(CreatureType.SKELETON, 20, 40),
            Triple(CreatureType.ZOMBIE, 0, 9)
    ),

    COUNT(6);
}
