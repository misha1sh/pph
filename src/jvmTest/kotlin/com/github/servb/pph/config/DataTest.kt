package com.github.servb.pph.config

import com.github.servb.pph.pheroes.common.common.IdeologyType
import com.soywiz.klock.seconds
import com.soywiz.korio.async.suspendTest
import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.std.openAsZip
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openAsync
import com.soywiz.korio.util.OS
import gxlib.graphics.dib.RGB16
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class DataTest : StringSpec({
    "Data loads from resources" {
        suspendTest({ OS.isJvm || OS.isNativeDesktop }, 3.seconds) {
            val rootVfs =
                resourcesVfs["resources.zip"].readAll().openAsync()
                    .openAsZip(caseSensitive = false)
            Data.init(rootVfs)

            Data.creatureTypes shouldNotBe null
            Data.creatureTypes!!.count() shouldBeGreaterThan 1
            Data.creatureTypes!!.ARCHER.v shouldBe 2
            Data.creatureTypes!!.ARCHER.descriptor!!.level shouldBe 2

            Data.difficultyLevels shouldNotBe null
            Data.mapSizes shouldNotBe null
            Data.perks shouldNotBe null
            Data.nationTypes shouldNotBe null
            Data.surfaceTypes shouldNotBe null
        }
    }

    "MapSize" {
        val mapSize = MapSize(linkedMapOf(
            "v" to 1,
            "size" to 4
        ))
        mapSize.v shouldBe 1
        mapSize.size shouldBe 4
    }

    "NationType" {
        val nationType = NationType(
            linkedMapOf(
                "v" to 2,
                "mask" to 5,
                "ideology" to "evil"
            )
        )
        nationType.v shouldBe  2
        nationType.mask shouldBe 5
        nationType.ideology shouldBe IdeologyType.EVIL
    }

    "Perk" {
        val perk = Perk(linkedMapOf(
            "v" to 1,
            "description" to "this is perk"
        ))
        perk.v shouldBe 1
        perk.description shouldBe "this is perk"
    }

    "SurfaceType" {
        val surfaceType = SurfaceType(linkedMapOf(
            "v" to 4,
            "mask" to 33,
            "moveCost" to 7,
            "color" to linkedMapOf(
                "r" to 255,
                "g" to 0,
                "b" to 255
            )
        ))
        surfaceType.v shouldBe 4
        surfaceType.mask shouldBe 33
        surfaceType.moveCost shouldBe 7
        surfaceType.color shouldBe RGB16(255, 0, 255)
    }

    "CreatureType" {
        val rootVfs =
            resourcesVfs["resources.zip"].readAll().openAsync()
                .openAsZip(caseSensitive = false)
        Data.init(rootVfs)

        val creatureType = CreatureType(linkedMapOf(
            "v" to 4,
            "descriptor" to linkedMapOf(
                "perks" to arrayListOf<String>(),
                "level" to 1,
                "nation" to "HIGHMEN",
                "attack" to 4,
                "defence" to 5,
                "speed" to 7,
                "size" to 34,
                "transType" to "WALK",
                "shots" to 435,
                "hits" to 42,
                "damage_min" to 34,
                "damage_max" to 61,
                "cost" to linkedMapOf(
                    "gold" to 1,
                    "ore" to 0,
                    "wood" to 23,
                    "mercury" to 235,
                    "gem" to 2355,
                    "crystal" to 11,
                    "sulfur" to 233
                ),
                "growth" to 333,
                "pidx" to 222,
            ),
        ))
        creatureType.v shouldBe 4
        creatureType.descriptor!!.perks shouldBe 0
        creatureType.descriptor!!.level shouldBe 1
        creatureType.descriptor!!.nation shouldBe Data.nationTypes!!.HIGHMEN
        creatureType.descriptor!!.attack shouldBe 4
        creatureType.descriptor!!.defence shouldBe 5
        creatureType.descriptor!!.speed shouldBe 7
        creatureType.descriptor!!.size shouldBe 34
        creatureType.descriptor!!.shots shouldBe 435
        creatureType.descriptor!!.hits shouldBe 42
        creatureType.descriptor!!.damage_min shouldBe 34
        creatureType.descriptor!!.damage_max shouldBe 61
        creatureType.descriptor!!.growth shouldBe 333
        creatureType.descriptor!!.pidx shouldBe 222
    }

    "DifficultyLevel" {
        val difficultyLevel = DifficultyLevel(
            linkedMapOf(
                "v" to 5,
                "human" to linkedMapOf(
                    "gold" to 1,
                    "ore" to 0,
                    "wood" to 23,
                    "mercury" to 235,
                    "gem" to 2355,
                    "crystal" to 11,
                    "sulfur" to 233
                ),
                "computer" to linkedMapOf(
                    "gold" to 777,
                    "ore" to 0,
                    "wood" to 23,
                    "mercury" to 235,
                    "gem" to 2355,
                    "crystal" to 11,
                    "sulfur" to 233
                )
            ));

        difficultyLevel.v shouldBe 5
        difficultyLevel.human!!.quant[0] shouldBe  1
        difficultyLevel.computer!!.quant[0] shouldBe 777
    }
})