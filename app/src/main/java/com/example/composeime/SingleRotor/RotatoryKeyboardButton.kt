package com.example.composeime.SingleRotor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeime.ExternalButtonAction
import com.example.composeime.KeyButton
import com.example.composeime.R
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun RotatoryKeyboardButton(
    it: KeyButton,
    shift: Boolean,
    size: Int = 15,
    scale: Float = 1f
) {

    if(it.action == ExternalButtonAction.INPUT || it.action == ExternalButtonAction.NUMBERS)
        Text(
            text = if(shift) it.text.uppercase() else it.text.lowercase(),
            fontSize = size.sp,
            color = Color.White
        )
    else if(it.action == ExternalButtonAction.SHIFT)
        Box(modifier = Modifier.absolutePadding(top= 2.dp)
        ) {
            FaIcon(
                faIcon = FaIcons.ArrowUp,
                size = size.dp,
                tint = Color.White,
            )
        }
    else if(it.action == ExternalButtonAction.DEL)
        Box(modifier = Modifier.absolutePadding(left = 2.dp, top= 5.dp)
        ) {
            FaIcon(
                faIcon = FaIcons.Backspace,
                size = size.dp,
                tint = Color.White,
            )
        }

    else if(it.action == ExternalButtonAction.OK)
        Box(modifier = Modifier.absolutePadding(left = 2.dp, top= 2.dp)
        ) {
            FaIcon(
                faIcon = FaIcons.Share,
                size = size.dp,
                tint = Color.White,
            )
        }

    else if(it.action == ExternalButtonAction.SPACE)
        Image(
            painter = painterResource(id = R.drawable.space_bar),
            contentDescription = "spacebar",
            Modifier.scale(scale)
        )

}


