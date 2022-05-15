package com.github.servb.pph.config

class MapSize {
    var v: Int
    var size: Int

    constructor(yaml: LinkedHashMap<String, Any?>){
        v = yaml["v"] as Int
        size = yaml["size"] as Int
    }
}