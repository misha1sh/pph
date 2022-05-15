package com.github.servb.pph.config

import com.github.servb.pph.gxlib.IDibPixel
import com.github.servb.pph.gxlib.RGB16

class SurfaceType {
    var v: Int
    var mask: Int
    var moveCost: Int
    var color: IDibPixel

    constructor(yaml: LinkedHashMap<String, Any?>){
        v = yaml["v"] as Int
        mask = yaml["mask"] as Int
        moveCost = yaml["moveCost"] as Int

        var color_yaml = yaml["color"] as LinkedHashMap<String, Any?>

        color = RGB16(
            color_yaml["r"] as Number,
            color_yaml["g"] as Number,
            color_yaml["b"] as Number
        )
    }

}