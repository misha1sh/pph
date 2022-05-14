package com.github.servb.pph.config

import com.soywiz.korio.file.VfsFile
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.serialization.yaml.Yaml
import com.soywiz.korio.stream.SyncStream

class Data {
    companion object {

        var difficultyLevels: DifficultyLevels? = null
        var mapSizes: MapSizes? = null
        var surfaceTypes: SurfaceTypes? = null
        var nationTypes: NationTypes? = null
        var perks: Perks? = null


        suspend fun init(res: VfsFile) {

            difficultyLevels = DifficultyLevels(getYaml(res["configs\\DifficultyLevels.yml"]))
            mapSizes = MapSizes(getYaml(res["configs\\MapSizes.yml"]))
            surfaceTypes = SurfaceTypes(getYaml(res["configs\\SurfaceTypes.yml"]))
            nationTypes = NationTypes(getYaml(res["configs\\NationTypes.yml"]))
            perks = Perks(getYaml(res["configs\\Perks.yml"]))
        }


        private suspend fun getYaml(file: VfsFile): LinkedHashMap<String, Any?>  {
            val configText: String = file.readString()
            val yaml: LinkedHashMap<String, Any?> = Yaml.decode(configText) as LinkedHashMap<String, Any?>
            return yaml
        }
    }
}
