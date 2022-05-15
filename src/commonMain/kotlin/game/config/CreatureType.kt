package com.github.servb.pph.config

class CreatureType {
    var v: Int
    var descriptor: CreatureDescriptor? = null

    constructor(yaml: LinkedHashMap<String, Any?>){
        v = yaml["v"] as Int
        if(yaml["descriptor"] != null)
            descriptor = createCreatureDescriptor(yaml["descriptor"] as LinkedHashMap<String, Any?>)
    }
}