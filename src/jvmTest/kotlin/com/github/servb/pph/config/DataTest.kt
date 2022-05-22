package com.github.servb.pph.config

import com.soywiz.klock.seconds
import com.soywiz.korio.async.suspendTest
import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.std.openAsZip
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.stream.openAsync
import com.soywiz.korio.util.OS
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


})