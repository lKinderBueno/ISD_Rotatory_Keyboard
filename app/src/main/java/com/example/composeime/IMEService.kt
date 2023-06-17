package com.example.composeime

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.compose.ui.input.key.Key
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import kotlinx.coroutines.flow.*

interface MyKeyboardListener {
    fun onEventOccurred(event: Int)
}

class KeyboardListener {
    private var listener: MyKeyboardListener? = null

    fun setEventListener(listener: MyKeyboardListener) {
        this.listener = listener
    }

    fun trigger(event: Int) {
        // Trigger the event
        listener?.onEventOccurred(event)
    }
}


class IMEService : LifecycleInputMethodService(),
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    init {
        initKeyboard()
    }


    private val myKeyboardListener = KeyboardListener()

    override fun onCreateInputView(): View {
        val view = ComposeKeyboardView(this, myKeyboardListener)

        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }
        return view
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(isInputViewShown){
            if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode  || KeyEvent.KEYCODE_DPAD_RIGHT == keyCode ||
                KeyEvent.KEYCODE_DPAD_UP == keyCode || KeyEvent.KEYCODE_DPAD_DOWN == keyCode ||
                KeyEvent.KEYCODE_DPAD_CENTER == keyCode || KeyEvent.KEYCODE_ENTER == keyCode) {
                myKeyboardListener.trigger(keyCode)
                return true
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if(isInputViewShown){
            if(KeyEvent.KEYCODE_DPAD_LEFT == keyCode  || KeyEvent.KEYCODE_DPAD_RIGHT == keyCode ||
                KeyEvent.KEYCODE_DPAD_UP == keyCode || KeyEvent.KEYCODE_DPAD_DOWN == keyCode ||
                KeyEvent.KEYCODE_DPAD_CENTER == keyCode || KeyEvent.KEYCODE_ENTER == keyCode) {
                //myKeyboardListener.trigger(keyCode)
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }


    override fun onCreate() {
        super.onCreate()
        savedStateRegistryController.performRestore(null)

    }

    override val viewModelStore: ViewModelStore
        get() = store
    override val lifecycle: Lifecycle
        get() = dispatcher.lifecycle


    //ViewModelStore Methods
    private val store = ViewModelStore()

    //SaveStateRegestry Methods

    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val savedStateRegistry: SavedStateRegistry get() = savedStateRegistryController.savedStateRegistry
}
