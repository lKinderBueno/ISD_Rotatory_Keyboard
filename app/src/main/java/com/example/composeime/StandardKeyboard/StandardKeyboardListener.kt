package com.example.composeime.ui.StandardKeyboard

import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.composeime.CLIENTPORT
import com.example.composeime.ExternalButtonAction
import com.example.composeime.IMEService
import com.example.composeime.KeyButton
import com.example.composeime.SERVERPORT
import com.example.composeime.SERVER_IP
import com.example.composeime.getButtonFromMatrix
import com.example.composeime.getIndexFromSimilarItem
import com.example.composeime.getItemsInMatrix
import com.example.composeime.littleEndianConversion
import com.example.composeime.standardLayoutSymbols
import com.example.composeime.ui.bgColor
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.UnknownHostException
import androidx.compose.runtime.derivedStateOf


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StandardKeyBoardListener(
    modifier: Modifier = Modifier,
    mainLayout: Array<Array<KeyButton>>,
    keyHeight: Float = 30f,
) {
    val ctx = LocalContext.current

    var selectedIndex by remember { mutableStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    var hasFocus by remember { mutableStateOf(false) }
    var layout by remember { mutableStateOf(mainLayout) }
    var shift by remember { mutableStateOf(false) }
    val handler = Handler(Looper.getMainLooper())

    val numberOfItems by remember {derivedStateOf {getItemsInMatrix(layout)}}

    fun goRight() {
        if (selectedIndex >= 0 && selectedIndex < numberOfItems-1)
            selectedIndex++
        else selectedIndex = 0;
    }

    fun goLeft() {
        if (selectedIndex > 0) selectedIndex--;
        else selectedIndex = numberOfItems - 1;
    }

    fun enter() {
        val item = getButtonFromMatrix(selectedIndex, layout)
        if (item != null)
            when (item.action) {
                ExternalButtonAction.INPUT -> {
                    (ctx as IMEService).currentInputConnection.commitText(
                        if (shift) item.text.uppercase() else item.text.lowercase(),
                        item.text.length
                    )
                    shift = false
                }
                ExternalButtonAction.NUMBERS -> {
                    if (layout == standardLayoutSymbols) layout = mainLayout;
                    else layout = standardLayoutSymbols
                    shift = false
                    selectedIndex = getIndexFromSimilarItem(layout, ExternalButtonAction.NUMBERS)
                }

                ExternalButtonAction.SPACE -> (ctx as IMEService).currentInputConnection.commitText(" ", 1)
                ExternalButtonAction.SHIFT -> {
                    shift = !shift
                }

                ExternalButtonAction.DEL -> (ctx as IMEService).currentInputConnection.deleteSurroundingText(1,0)
                ExternalButtonAction.OK -> {
                    (ctx as IMEService).currentInputConnection.let {
                        it.sendKeyEvent(android.view.KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
                        it.sendKeyEvent(android.view.KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
                    }
                }
            }
    }

    DisposableEffect(Unit) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)  //Just for testing relax the rules...
        var socket = DatagramSocket(CLIENTPORT)
        val recvBuf = ByteArray(1)
        val packet =
            DatagramPacket(recvBuf, recvBuf.size, InetAddress.getByName(SERVER_IP), SERVERPORT)

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
                        }
                    }
                }
            } catch (e1: IOException) {
                println(">>>>>>>>>>>>> IOException  " + e1.message)
                socket?.close()
            } catch (e2: UnknownHostException) {
                println(">>>>>>>>>>>>> UnknownHostException  " + e2.message)
                socket?.close()
            } finally {
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
                    if (it.key == Key.DirectionRight || it.key == Key.VolumeUp) {
                        goRight()
                        true
                    } else if (it.key == Key.DirectionLeft || it.key == Key.VolumeDown) {
                        goLeft()
                        true
                    } else if (it.key == Key.Enter || it.key == Key.VolumeMute) {
                        enter()
                        true
                    }
                    false
                }
                false
            },
    ) {

        StandardKeyBoardComponent(
            layout = layout,
            selectedIndex = selectedIndex, modifier = modifier, shift = shift,
            keyHeight= keyHeight
        )
    }
}