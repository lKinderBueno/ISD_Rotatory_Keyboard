package com.example.composeime

import androidx.compose.ui.graphics.Color

val CLIENTPORT = 3000
val SERVERPORT = 12345

//val SERVER_IP = "192.168.178.71" //WIFI CASA
val SERVER_IP = "172.16.69.1"  //LAN UNI

fun calculateRow(index: Int, array: Array<Array<KeyButton>>): Int? {
    var remainingIndex = index
    for (rowIndex in array.indices) {
        if (remainingIndex < array[rowIndex].size) {
            return rowIndex
        } else {
            remainingIndex -= array[rowIndex].size
        }
    }
    return null
}

fun calculateColumn(index: Int, array: Array<Array<KeyButton>>): Int? {
    var remainingIndex = index
    for (rowIndex in array.indices) {
        if (remainingIndex < array[rowIndex].size) {
            return remainingIndex
        } else {
            remainingIndex -= array[rowIndex].size
        }
    }
    return null
}

var lastPressed : Int? = null
fun selectNextOrPreviousRow(index: Int, array: Array<Array<KeyButton>>, isPrevious: Boolean): Int {
    var row = calculateRow(index, array)
    var col = calculateColumn(index, array)


    if (row == null || col == null) return 0

    var nextRow = 0
    var nextCol = 0

    if (isPrevious) {
        nextRow = if (row - 1 >= 0) row - 1
        else array.size - 1
    } else {
        nextRow = if (row + 1 >= array.size) 0
        else row + 1
    }

    if (row == array.size - 1 && col == 2) {
        nextCol = array[nextRow].size - 1
    }else if (row == array.size - 1 && col == 1) {
        nextCol = lastPressed ?: array[nextRow].size/2
    } else if (nextRow == array.size - 1) {
        if (col == 0) nextCol = 0
        else if (col == array[row].size - 1) nextCol = 2
        else {
            nextCol = 1
            lastPressed = col
        }
    } else if (col >= array[nextRow].size)
        nextCol = array[nextRow].size - 1
    else nextCol = col

    var index = 0
    (0..(nextRow - 1)).forEach {
        index += array[it].size
    }

    return index + nextCol
}


fun selectNextOrPreviousRowABC(index: Int, array: Array<Array<KeyButton>>, isPrevious: Boolean): Int {
    var row = calculateRow(index, array)
    var col = calculateColumn(index, array)


    if (row == null || col == null) return 0

    var nextRow = 0
    var nextCol = 0

    if (isPrevious) {
        nextRow = if (row - 1 >= 0) row - 1
        else array.size - 1
    } else {
        nextRow = if (row + 1 >= array.size) 0
        else row + 1
    }

    if(row == array.size - 1 && isPrevious && lastPressed != null){
        nextCol = lastPressed as Int
        lastPressed = null
    }else if (row == array.size - 1 && col == array[array.size - 1].size -1) {
        nextCol = array[nextRow].size - 1
    }else if (row == array.size - 1 && col == array[nextRow].size-1) {
        nextCol = lastPressed ?: array[nextRow].size - 2
    } else if (nextRow == array.size - 1 && col == array[nextRow].size-2 ) {
        nextCol = col
    } else if (nextRow == array.size - 1 && col == array[nextRow].size-1 ) {
        nextCol = array[nextRow].size - 2
    } else if (col >= array[nextRow].size)
        nextCol = array[nextRow].size - 1
    else nextCol = col

    if(array[nextRow][nextCol].action == ExternalButtonAction.SPACE)
        lastPressed = col

    var index = 0
    (0..(nextRow - 1)).forEach {
        index += array[it].size
    }

    return index + nextCol
}

fun selectNextOrPreviousColumn(index: Int, array: Array<Array<KeyButton>>, isPrevious: Boolean): Int {
    var row = calculateRow(index, array)
    var col = calculateColumn(index, array)


    if (row == null || col == null) return 0
    if(isPrevious){
        if(col -1 < 0) col = array[row].size-1
        else col--
    }else{
        if(col +1 >=  array[row].size) col = 0
        else col++
    }

    if(row == array.size - 1 && col == 1) lastPressed = null

    var index = 0
    (0..(row - 1)).forEach {
        index += array[it].size
    }
    return index + col

}


fun getItemsInMatrix(array: Array<Array<KeyButton>>): Int {
    var i = 0
    array.forEach {
        it.forEach {
            i++
        }
    }
    return i
}

fun getIndexFromSimilarItem(array: Array<Array<KeyButton>>, action: ExternalButtonAction): Int {
    var i = 0
    (0..array.size - 1).forEach {
        val row = array[it]
        (0..row.size - 1).forEach { it2 ->
            if (array[it][it2].action == action)
                return i
            i++
        }
    }
    return 0
}

fun getButtonFromMatrix(index: Int, array: Array<Array<KeyButton>>): KeyButton? {
    val row = calculateRow(index, array)
    val column = calculateColumn(index, array)

    if (row != null && column != null) {
        return array[row][column]
    } else {
        return null
    }
}

fun littleEndianConversion(bytes: ByteArray): Int {
    var result = 0
    for (i in bytes.indices) {
        result = result or (bytes[i].toInt() shl 8 * i)
    }
    return result
}

fun colorFromHex(color: String) = Color(android.graphics.Color.parseColor(color))
