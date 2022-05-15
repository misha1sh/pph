package com.github.servb.pph.config

class Perk {
    var v: Int
    var description: String? = null

    constructor(value: Int, str: String){
        v = value
        description = str
    }

    constructor(yaml: LinkedHashMap<String, Any?>){
        v = yaml["v"] as Int
        if(v != 0) v = 1 shl (v-1)
        if(yaml["description"] != null)
            description = yaml["description"] as String
    }
}