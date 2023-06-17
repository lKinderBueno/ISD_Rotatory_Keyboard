package com.example.composeime

import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeime.ui.bgColor
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.UnknownHostException
import java.time.Instant


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyBoardListenerSingleRotor(
    modifier: Modifier = Modifier,
    minSize: Dp = 64.dp,
    mainLayout: Array<KeyButton>,
    changeKeyboardType: () -> Unit,
    keyboardListener : KeyboardListener,
    ) {
    val ctx = LocalContext.current

    var selectedIndex by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }

    var layout by remember {mutableStateOf(mainLayout)}
    var layoutType by remember { mutableStateOf(0) }

    var shift by remember { mutableStateOf(true) }
    val handler = Handler(Looper.getMainLooper())

    fun changeLayout() {
        if (layoutType == 0) {
            layoutType = 1
            layout = rotatoryLayoutAbc
            selectedIndex = 0
        }else  if (layoutType == 1) {
            layoutType = 2
            layout = rotatoryLayoutCQwerty
            selectedIndex = rotatoryLayoutCQwerty.size-1
        }else{
            layoutType == 0
            layout = rotatoryLayoutQwerty
            selectedIndex = 0
        }
        //if (layoutType == 1) {
        //    layoutType = 2
        //    layout = rotatoryLayoutQwerty
        //}
        //else if (layoutType == 2) {
        //    layoutType = 3
        //    layout = rotatoryLayoutCirrin
        //}
        //else if (layoutType == 3) {
        //    layoutType = 4
        //    layout = rotatoryLayoutCQwerty
        //}else{
        //    layoutType = 1
        //    layout = rotatoryLayoutAbc
        //}
        selectedIndex = 0
    }

    fun goRight(){
        if(selectedIndex >= 0 && selectedIndex < layout.size-1)
            selectedIndex++
        else selectedIndex = 0;
    }

    fun goLeft(){
        if(selectedIndex > 0) selectedIndex--;
        else selectedIndex =  layout.size-1;
    }

    fun enter(){
        when (layout[selectedIndex].action) {
            ExternalButtonAction.INPUT -> {
                (ctx as IMEService).currentInputConnection.commitText(
                    if (shift) layout[selectedIndex].text.uppercase() else layout[selectedIndex].text.lowercase(),
                    layout[selectedIndex].text.length
                )
                shift = false
            }
            ExternalButtonAction.NUMBERS -> {
                if(layout == rotatoryLayoutSymbols) layout = mainLayout;
                else layout = rotatoryLayoutSymbols
                selectedIndex = layout.indexOfFirst { it.action == ExternalButtonAction.NUMBERS }
                shift = false
            }
            ExternalButtonAction.SPACE -> (ctx as IMEService).currentInputConnection.commitText(" ", 1)
            ExternalButtonAction.SHIFT -> {
                shift = !shift
            }
            ExternalButtonAction.DEL -> (ctx as IMEService).currentInputConnection.deleteSurroundingText(1,0)
            ExternalButtonAction.OK -> {
                (ctx as IMEService).currentInputConnection?.performEditorAction(EditorInfo.IME_ACTION_DONE)
                shift = false
                layout = mainLayout
                selectedIndex = 0
            }
        }
    }

    keyboardListener.setEventListener(object : MyKeyboardListener {
        override fun onEventOccurred(event : Int) {
            if(KeyEvent.KEYCODE_DPAD_LEFT == event)
                goLeft()
            else if(KeyEvent.KEYCODE_DPAD_RIGHT == event)
                goRight()
            else if(KeyEvent.KEYCODE_DPAD_CENTER == event || KeyEvent.KEYCODE_ENTER == event)
                enter()
        }
    })

    DisposableEffect(Unit) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)  //Just for testing relax the rules...
        var socket = DatagramSocket(CLIENTPORT)
        val recvBuf = ByteArray(1)
        val packet = DatagramPacket(recvBuf, recvBuf.size, InetAddress.getByName(SERVER_IP), SERVERPORT)

        Thread {
            try {
                //Keep a socket open to listen to all the UDP trafic that is destined for this port
                while (true) {
                    //Receive a packet
                    socket!!.receive(packet)
                    val data = littleEndianConversion(packet.data)
                    handler.post {
                        when (data) {
                            0 -> goLeft()
                            1 -> enter()
                            2 -> goRight()
                            3 -> changeLayout()
                            4 -> changeKeyboardType()
                        }
                    }
                }
            }
            catch (e1: IOException) {
                println(">>>>>>>>>>>>> IOException  "+e1.message)
                socket?.close()
            }
            catch (e2: UnknownHostException) {
                println(">>>>>>>>>>>>> UnknownHostException  "+e2.message)
                socket?.close()
            }
            finally{
                socket?.close()
            }
        }.start()

        onDispose {
            socket?.close()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .background(color = bgColor)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (!it.hasFocus)
                    hasFocus = it.hasFocus
            }
            .focusable()
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp) {
                    if(it.key == Key.DirectionRight || it.key == Key.VolumeUp){
                        goRight()
                        true
                    }else if(it.key == Key.DirectionLeft || it.key == Key.VolumeDown) {
                        goLeft()
                        true
                    }else if(it.key == Key.Enter || it.key == Key.VolumeMute){
                        enter()
                        true
                    }
                    false
                }
                false
            },
        contentAlignment = Alignment.BottomCenter
    ) {

        val width = if (minWidth < 1.dp) minSize else minWidth
        val height = if (minHeight < 1.dp) minSize else minHeight
        KeyBoardSingleRotorComponent(width = width, height = height, layout = layout,
            selectedIndex = selectedIndex, modifier = modifier, shift = shift)
    }
}