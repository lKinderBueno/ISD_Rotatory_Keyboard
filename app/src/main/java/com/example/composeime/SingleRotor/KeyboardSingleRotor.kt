package com.example.composeime

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeime.SingleRotor.RotatoryKeyboardButton
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun KeyBoardSingleRotorComponent(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    layout: Array<KeyButton>,
    selectedIndex: Int,
    shift: Boolean = false,
) {
    Box() {
        val keyboardSize = layout.size - 1
        val angleDegreeDifference = 360f / (keyboardSize + 1)

        val _radius = 110.sp;//size.width * .15f

        fun getRadDiff(index: Int) = (((angleDegreeDifference * index) + (180f)) * (PI / 180f)).toFloat()

        Canvas(
            modifier = modifier.size(width, height)
        ) {
            val radius = _radius.toPx()

            //lets draw the circle now
            //but before we do that lets calculate the radius,
            //which is going to be 40% of the radius so we can achieve responsiveness

            drawCircle(
                color = colorFromHex("#d5d7de"),
                style = Stroke(width = radius * .05f /* 5% of the radius */),
                radius = radius,
                center = size.center
            )


            val angleRadDifferenceSelected = getRadDiff(selectedIndex)

            val lineLength = 0f
            val lineColour = androidx.compose.ui.graphics.Color.Red
            val startOffsetLine = Offset(
                x = lineLength * cos(angleRadDifferenceSelected) + size.center.x,
                y = lineLength * sin(angleRadDifferenceSelected) + size.center.y
            )
            val endOffsetLine = Offset(
                x = (radius - ((radius * .05f) / 2)) * cos(angleRadDifferenceSelected) + size.center.x,
                y = (radius - ((radius * .05f) / 2)) * sin(angleRadDifferenceSelected) + size.center.y
            )
            drawLine(
                color = lineColour,
                start = startOffsetLine,
                end = endOffsetLine,
                strokeWidth = radius * .18f
            )

            drawCircle(
                color = colorFromHex("#d5d7de"),
                radius = radius * .65f,
                center = size.center
            )

            drawCircle(
                color = androidx.compose.ui.graphics.Color.Black,
                radius = radius * .6f,
                center = size.center
            )
        }

        if(selectedIndex < layout.size) {
            Box(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                contentAlignment= Alignment.Center,
            ) {
                RotatoryKeyboardButton(it = layout[selectedIndex], shift = shift,
                    size = if(layout[selectedIndex].text.length > 3) 45 else 60,
                    scale = 2f)
            }
        }

        layout.forEach {
            val angleRadDifference = getRadDiff(layout.indexOf(it))

            val textRect = Rect()
            val paint = Paint()
            paint.textSize = _radius.value * .13f
            paint.color = Color.WHITE
            paint.getTextBounds(it.text, 0, it.text.length, textRect)

            val positionX = (_radius.value * .82f) * cos(angleRadDifference) + width.value / 2
            val positionY = (_radius.value * .82f) * sin(angleRadDifference) + height.value / 2

            Box(
                modifier = Modifier
                    .offset(
                        (positionX - (textRect.width() / 2)).dp,
                        (positionY + (textRect.height() / 2)).dp - 15.dp
                    ),
            ){
                RotatoryKeyboardButton(it = it, shift = shift, size = it.size)

            }
        }
    }

}