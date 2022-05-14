package com.github.servb.pph.config

class SurfaceTypes {

    var WATER: SurfaceType
    var SAND: SurfaceType
    var DIRT: SurfaceType
    var GRASS: SurfaceType
    var SWAMP: SurfaceType
    var LAVA: SurfaceType
    var WASTELAND: SurfaceType
    var DESERT: SurfaceType
    var SNOW: SurfaceType
    var NEW_DESERT: SurfaceType
    var PAVEMENT: SurfaceType
    var NEW_WASTELAND: SurfaceType

    var array: Array<SurfaceType?>


    constructor(yaml: LinkedHashMap<String, Any?>){
        WATER = SurfaceType(yaml["water"] as LinkedHashMap<String, Any?>)
        SAND = SurfaceType(yaml["sand"] as LinkedHashMap<String, Any?>)
        DIRT = SurfaceType(yaml["dirt"] as LinkedHashMap<String, Any?>)
        GRASS = SurfaceType(yaml["grass"] as LinkedHashMap<String, Any?>)
        SWAMP = SurfaceType(yaml["swamp"] as LinkedHashMap<String, Any?>)
        LAVA = SurfaceType(yaml["lava"] as LinkedHashMap<String, Any?>)
        WASTELAND = SurfaceType(yaml["wasteland"] as LinkedHashMap<String, Any?>)
        DESERT = SurfaceType(yaml["desert"] as LinkedHashMap<String, Any?>)
        SNOW = SurfaceType(yaml["snow"] as LinkedHashMap<String, Any?>)
        NEW_DESERT = SurfaceType(yaml["new_desert"] as LinkedHashMap<String, Any?>)
        PAVEMENT = SurfaceType(yaml["pavement"] as LinkedHashMap<String, Any?>)
        NEW_WASTELAND = SurfaceType(yaml["new_wasteland"] as LinkedHashMap<String, Any?>)

        array = arrayOf(WATER, SAND, DIRT, GRASS, SWAMP, LAVA, WASTELAND, DESERT, SNOW, NEW_DESERT, PAVEMENT, NEW_WASTELAND)
    }

    fun count(): Int{
        return array.size
    }

    operator fun get(indx: Int): SurfaceType?{
        return array[indx]
    }

}