package com.example.composeime.ui.StandardKeyboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeime.ExternalButtonAction
import com.example.composeime.KeyButton
import com.example.composeime.R
import com.example.composeime.getButtonFromMatrix
import com.example.composeime.ui.androidKeyBG
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun StandardKeyBoardComponent(
    modifier: Modifier = Modifier,
    layout: Array<Array<KeyButton>>,
    keyHeight: Float = 30f,
    selectedIndex: Int,
    shift: Boolean = false,
) {
    val _selected = getButtonFromMatrix(selectedIndex, layout)

    Box(contentAlignment = Alignment.Center) {
        //This is the estate we are going to work with
        val keyboardSize = layout.size - 1
        Column(
            modifier = modifier.width((10 * keyHeight).dp + 11.dp),
            Arrangement.SpaceBetween
        ) {
            (0..keyboardSize).forEach() {
                val row = layout[it]
                val rowSize = layout[it].size - 1
                Row(modifier = modifier.fillMaxWidth(), Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    (0..rowSize).forEach() { it2 ->
                        val key = row[it2]
                        val text = if(shift) key.text.uppercase() else key.text.lowercase()
                        val bg = if(key == _selected) Color.White else androidKeyBG
                        val col = if(key == _selected) androidKeyBG else Color.White

                        if (key.action == ExternalButtonAction.INPUT)
                            Box(
                                modifier = modifier
                                    .width(keyHeight.dp)
                                    .height(keyHeight.dp)
                                    .background(bg),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = text, color = col, fontSize = 24.sp)
                            }
                        else if (key.action == ExternalButtonAction.NUMBERS) {
                            Box(
                                modifier = modifier
                                    .height(keyHeight.dp)
                                    .background(bg)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = text, color = col, fontSize = 24.sp)
                            }
                        }else if (key.action == ExternalButtonAction.SPACE) {
                            Box(
                                modifier = modifier
                                    .height(keyHeight.dp)
                                    .background(bg)
                                    .weight(3f),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = if(key == _selected) R.drawable.space_bar_black else R.drawable.space_bar),
                                    contentDescription = "spacebar",
                                    modifier = Modifier.scale(2f)
                                )
                            }
                        }else if (key.action == ExternalButtonAction.SHIFT) {
                            Box(
                                modifier = modifier
                                    .height(keyHeight.dp)
                                    .background(bg)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                FaIcon(
                                    faIcon = FaIcons.ArrowUp,
                                    size = (keyHeight * .5).dp,
                                    tint = col,
                                )
                            }
                        } else if (key.action == ExternalButtonAction.DEL) {
                            Box(
                                modifier = modifier
                                    .height(keyHeight.dp)
                                    .background(bg)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                FaIcon(
                                    faIcon = FaIcons.Backspace,
                                    size = (keyHeight * .5).dp,
                                    tint = col,
                                )
                            }
                        }else if (key.action == ExternalButtonAction.OK) {
                            Box(
                                modifier = modifier
                                    .height(keyHeight.dp)
                                    .background(bg)
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = if(key == _selected) R.drawable.enter_black else R.drawable.enter),
                                    contentDescription = "spacebar",
                                    modifier = Modifier.scale(2f)
                                )
                            }
                            //Box(
                            //    modifier = modifier
                            //        .height(keyHeight.dp)
                            //        .background(bg)
                            //        .weight(1f),
                            //    contentAlignment = Alignment.Center
                            //) {
                            //    FaIcon(
                            //        faIcon = FaIcons.Share,
                            //        size = (keyHeight * .5).dp,
                            //        tint = col,
                            //    )
                            //}
                        }

                        if(it2 != rowSize)
                            Box(modifier.width(1.dp))
                    }
                }
                if(it != keyboardSize)
                    Box(modifier.height(2.dp))

            }
        }

    }

}