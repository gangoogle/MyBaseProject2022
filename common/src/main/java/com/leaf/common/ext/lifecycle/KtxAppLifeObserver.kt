package com.leaf.common.ext.lifecycle

import android.util.Log
import androidx.lifecycle.*
import com.leaf.common.callback.livedata.BooleanLiveData

/**
 * 作者　: hegaojian
 * 时间　: 20120/1/7
 * 描述　:
 */
object KtxAppLifeObserver : LifecycleEventObserver {

    var isForeground = BooleanLiveData()


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            isForeground.value = true
            Log.d("lifecycle", "ON_START")
        } else if (event == Lifecycle.Event.ON_STOP) {
            isForeground.value = false
            Log.d("lifecycle", "ON_STOP")

        }
    }

}