package com.example.composeime

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.unit.dp
import com.example.composeime.ui.StandardKeyboard.StandardKeyBoardListener
import com.example.composeime.ui.bgColor

class ComposeKeyboardView(context: Context) : AbstractComposeView(context) {

    @Composable
    override fun Content() {
        var rotatoryLayout by remember { mutableStateOf(rotatoryLayoutAbc) }
        var standardLayout by remember { mutableStateOf(standardLayoutQwerty) }
        var keyboardType by remember { mutableStateOf(0) }

        fun changeKeyboardType() {
            if (keyboardType == 0)
                keyboardType = 1
            else keyboardType = 0
        }

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {

            if (keyboardType == 1)
                KeyBoardListenerSingleRotor(
                    minSize = 10.dp,
                    mainLayout = rotatoryLayout,
                    changeKeyboardType =  { changeKeyboardType()},
                )
            else
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(bgColor),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    StandardKeyBoardListener(keyHeight = 60f,
                        mainLayout = standardLayout,
                        changeKeyboardType =  { changeKeyboardType()},
                    )
                }
        }

    }
}
