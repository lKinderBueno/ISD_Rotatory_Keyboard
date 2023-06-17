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
val _layoutCQwerty = arrayOf("d","s","a", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "g", "h", "j", "k", "l", "m", "n", "b", "v", "c", "x", "z", "f")
val _layoutCirrin =   arrayOf("e", "r", "o", "n", "i", "f", "l", "d", "u", "k", "b", "y", "q", "x", "p", "j", "v", "z", "c", "g", "w", "m", "s", "a", "t", "h")
val _layoutAbc =      arrayOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
val _layoutSymbols =  arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "?", "à", "è", "ì", "ò", "ù", "+", ",", ";", ".", "@", ":", "-", "_")



val rotatoryLayoutQwerty = _layoutQwerty.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("123", action = ExternalButtonAction.NUMBERS, size = 13))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))



var rotatoryLayoutCQwerty = arrayOf<KeyButton>()

val rotatoryLayoutCirrin = _layoutCirrin.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("123", action = ExternalButtonAction.NUMBERS, size = 13))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val rotatoryLayoutAbc = _layoutAbc.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("123", action = ExternalButtonAction.NUMBERS, size = 13))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))

val rotatoryLayoutSymbols = _layoutSymbols.map{KeyButton(it)}.toTypedArray()
    .plus(KeyButton("abc", action = ExternalButtonAction.NUMBERS, size = 13))
    .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
    .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
    .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
    .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))



val standardLayoutQwerty = arrayOf(
    _layoutQwerty.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutQwerty.sliceArray(10..18).map{KeyButton(it)}.toTypedArray(),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutQwerty.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())

        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))

        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)


val standardLayoutCQwerty = arrayOf(
    _layoutCQwerty.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutCQwerty.sliceArray(10..18).map{KeyButton(it)}.toTypedArray(),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutCQwerty.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())

        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))

        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)



val standardLayoutCirrin = arrayOf(
    _layoutCirrin.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutCirrin.sliceArray(10..18).map{KeyButton(it)}.toTypedArray(),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutCirrin.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())

        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)


val standardLayoutAbc = arrayOf(
    _layoutAbc.sliceArray(0..9).map{KeyButton(it)}.toTypedArray(),
    _layoutAbc.sliceArray(10..18).map{KeyButton(it)}.toTypedArray(),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutAbc.sliceArray(19..25).map{KeyButton(it)}.toTypedArray())

        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("123", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))

        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)


val standardLayoutSymbols = arrayOf(
    _layoutSymbols.sliceArray(0..8).map{KeyButton(it)}.toTypedArray(),
    _layoutSymbols.sliceArray(9..16).map{KeyButton(it)}.toTypedArray(),
    arrayOf<KeyButton>(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(_layoutSymbols.sliceArray(17..22).map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13)),
    arrayOf<KeyButton>(KeyButton("abc", action = ExternalButtonAction.NUMBERS))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
)


fun initCQwerty(){
    val a = arrayOf("d","s","a", "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "g", "h", "j", "k","l")
    val b = arrayOf("m", "n", "b", "v", "c", "x", "z")
    rotatoryLayoutCQwerty = a.map{KeyButton(it)}.toTypedArray()
        .plus(KeyButton("DEL", action = ExternalButtonAction.DEL, size = 13))
        .plus(KeyButton("OK", action = ExternalButtonAction.OK, size = 13))
        .plus( b.map{KeyButton(it)}.toTypedArray())
        .plus(KeyButton("SH", action = ExternalButtonAction.SHIFT))
        .plus(KeyButton("SP", action = ExternalButtonAction.SPACE))
        .plus(KeyButton("123", action = ExternalButtonAction.NUMBERS, size = 13))
        .plus(KeyButton("f"))
}

fun initKeyboard(){
    initCQwerty()
}





