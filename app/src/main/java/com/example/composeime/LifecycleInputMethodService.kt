package com.example.composeime

import android.R
import android.content.Intent
import android.inputmethodservice.InputMethodService
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher


abstract class LifecycleInputMethodService : InputMethodService(), LifecycleOwner {

    protected val dispatcher = ServiceLifecycleDispatcher(this)

    @CallSuper
    override fun onCreate() {
        dispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBindInput() {
        super.onBindInput()
        dispatcher.onServicePreSuperOnBind()
    }

    override fun onEvaluateFullscreenMode(): Boolean {

        //no full screen mode if only simple Kp2aKeyboard is shown
        return false
        //val dm = resources.displayMetrics
        //val displayHeight = dm.heightPixels.toFloat()
        //// If the display is more than X inches high, don't go to fullscreen mode
        //val dimen = resources.getDimension(R.dimen.max_height_for_fullscreen)
        //return if (displayHeight > dimen) {
        //    false
        //} else {
        //    super.onEvaluateFullscreenMode()
        //}
    }


    // this method is added only to annotate it with @CallSuper.
    // In usual service super.onStartCommand is no-op, but in LifecycleService
    // it results in mDispatcher.onServicePreSuperOnStart() call, because
    // super.onStartCommand calls onStart().
    @CallSuper
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @CallSuper
    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

}