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

fun getItemsInMatrix(array: Array<Array<KeyButton>>) : Int{
    var i = 0
    array.forEach {
        it.forEach {
            i++
        }
    }
    return i
}

fun getIndexFromSimilarItem(array: Array<Array<KeyButton>>, action: ExternalButtonAction) : Int{
    var i = 0
    (0..array.size-1).forEach {
        val row = array[it]
        (0..row.size-1).forEach {it2->
            if(array[it][it2].action == action)
                return i
            i++
        }
    }
    return 0
}

fun getButtonFromMatrix(index: Int, array: Array<Array<KeyButton>>) : KeyButton? {
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
