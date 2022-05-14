package com.github.servb.pph.config

class DifficultyLevels {

    var UNDEFINED: DifficultyLevel
    var EASY: DifficultyLevel
    var NORMAL: DifficultyLevel
    var HARD: DifficultyLevel
    var EXPERT: DifficultyLevel
    var IMPOSSIBLE: DifficultyLevel

    private var array: Array<DifficultyLevel?>

    constructor(yaml: LinkedHashMap<String, Any?>){

        UNDEFINED = DifficultyLevel(yaml["undefined"] as LinkedHashMap<String, Any?>)
        EASY = DifficultyLevel(yaml["easy"] as LinkedHashMap<String, Any?>)
        NORMAL = DifficultyLevel(yaml["normal"] as LinkedHashMap<String, Any?>)
        HARD = DifficultyLevel(yaml["hard"] as LinkedHashMap<String, Any?>)
        EXPERT = DifficultyLevel(yaml["expert"] as LinkedHashMap<String, Any?>)
        IMPOSSIBLE = DifficultyLevel(yaml["impossible"] as LinkedHashMap<String, Any?>)

        array = arrayOf(UNDEFINED, EASY, NORMAL, HARD, EXPERT, IMPOSSIBLE)
    }

    fun count(): Int{
        return array.size - 1
    }

    operator fun get(indx: Int): DifficultyLevel?{
        return array[indx]
    }
}