package com.github.servb.pph.config

import com.github.servb.pph.pheroes.common.common.IdeologyType
import com.soywiz.korio.lang.InvalidArgumentException

class NationType {
    var v: Int
    var mask: Int = -42
    var ideology: IdeologyType

    constructor(yaml: LinkedHashMap<String, Any?>) {
        v = yaml["v"] as Int
        if (yaml["mask"] != null)
            mask = yaml["mask"] as Int
        ideology = createIdeologyType(yaml["ideology"] as String)
    }
}