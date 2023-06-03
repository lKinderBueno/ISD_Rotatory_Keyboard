package com.example.composeime

enum class ExternalButtonAction {
    NUMBERS, SPACE, SHIFT, DEL, OK, INPUT
}

class KeyButton(text: String, action: ExternalButtonAction = ExternalButtonAction.INPUT, size: Int = 15){
    var text = text
    var action = action
    var size = size
}

val _layoutQwerty =   arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m")
val _layoutCQwuerty = arrayOf("a", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "g", "h", "j", "k", "l", "m", "n", "b", "v", "c", "x", "z", "f", "d", "s")
val _layoutCirrin =   arrayOf("e", "r", "o", "n", "i", "f", "l", "d", "u", "k", "b", "y", "q", "x", "p", "j", "v", "z", "c", "g", "w", "m", "s", "a", "t", "h")
val _layoutAbc =      arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
val _layoutSymbols =  arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "?", "à", "è", "ì", "ò", "ù", "+", ",", ";", ".", "@", ":", "-", "_")



val rotatoryLayoutQwerty = _layoutQwerty.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("?12", action = ExternalButtonAction.NUMBERS, size = 9))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val standardLayoutQwerty = arrayOf(
        _layoutQwerty.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
        _layoutQwerty.sliceArray(10..18).map{KeyButton(it)}.toTypedArray().plus(KeyButton(",")),
        arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
            .plus(_layoutQwerty.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())
            .plus(KeyButton("."))
            .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
        arrayOf<KeyButton>(KeyButton("?123", action = ExternalButtonAction.NUMBERS))
            .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
            .plus(KeyButton("-")).plus(KeyButton("_"))
            .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)



val rotatoryLayoutCQwerty = _layoutCQwuerty.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("?12", action = ExternalButtonAction.NUMBERS, size = 9))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val standardLayoutCQwerty = arrayOf(
    _layoutCQwuerty.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutCQwuerty.sliceArray(10..18).map{KeyButton(it)}.toTypedArray().plus(KeyButton(",")),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutCQwuerty.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("."))
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("?123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("-")).plus(KeyButton("_"))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)


val rotatoryLayoutCirrin = _layoutCirrin.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("?12", action = ExternalButtonAction.NUMBERS, size = 9))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val standardLayoutCirrin = arrayOf(
    _layoutCirrin.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutCirrin.sliceArray(10..18).map{KeyButton(it)}.toTypedArray().plus(KeyButton(",")),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutCirrin.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("."))
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("?123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("-")).plus(KeyButton("_"))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)

val rotatoryLayoutAbc = _layoutAbc.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("?12", action = ExternalButtonAction.NUMBERS, size = 9))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val standardLayoutAbc = arrayOf(
    _layoutAbc.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutAbc.sliceArray(10..18).map{KeyButton(it)}.toTypedArray().plus(KeyButton(",")),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutAbc.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("."))
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("?123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("-")).plus(KeyButton("_"))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)

val rotatoryLayoutSymbols = _layoutSymbols.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("abc", action = ExternalButtonAction.NUMBERS))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK))

val standardLayoutSymbols = arrayOf(
    _layoutSymbols.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutSymbols.sliceArray(10..16).map{KeyButton(it)}.toTypedArray().plus(KeyButton(",")),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutSymbols.sliceArray(17..22).map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("abc", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)





