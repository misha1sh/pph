package com.github.servb.pph.config

class MapSizes {

    var SMAL: MapSize
    var MEDIUM: MapSize
    var LARGE: MapSize
    var EXTRA_LARGE: MapSize

    private var array: Array<MapSize?>

    constructor(yaml: LinkedHashMap<String, Any?>){
        SMAL = MapSize(yaml["smal"] as LinkedHashMap<String, Any?>)
        MEDIUM = MapSize(yaml["medium"] as LinkedHashMap<String, Any?>)
        LARGE = MapSize(yaml["large"] as LinkedHashMap<String, Any?>)
        EXTRA_LARGE = MapSize(yaml["extra_large"] as LinkedHashMap<String, Any?>)

        array = arrayOf(SMAL, MEDIUM, LARGE, EXTRA_LARGE)
    }

    fun count(): Int{
        return array.size
    }

    operator fun get(indx: Int): MapSize?{
        return array[indx]
    }

}