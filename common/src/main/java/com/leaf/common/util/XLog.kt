package com.leaf.common.util

import android.text.TextUtils
import android.util.Log
import com.orhanobut.logger.Logger

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/26
 * 描述　:
 */
object XLog {
    private const val DEFAULT_TAG = "JetpackMvvm"

    fun v(tag: String? = TAG, message: String) {
        if (!jetpackMvvmLog) return
        Logger.v("$TAG - $message")
    }

    fun d(tag: String? = TAG, message: String) {
        if (!jetpackMvvmLog) return
        Logger.d("$TAG - $message")
    }

    fun i(tag: String? = TAG, message: String) {
        if (!jetpackMvvmLog) return
        Logger.i("$TAG - $message")
    }

    fun w(tag: String? = TAG, message: String) {
        if (!jetpackMvvmLog) return
        Logger.w("$TAG - $message")
    }

    fun e(tag: String? = TAG, message: String) {
        if (!jetpackMvvmLog) return
        Logger.e(message)
    }


    fun v(message: String) {
        if (!jetpackMvvmLog) return
        Logger.v(message)
    }

    fun d(message: String) {
        if (!jetpackMvvmLog) return
        Logger.d(message)
    }

    fun i(message: String) {
        if (!jetpackMvvmLog) return
        Logger.i(message)
    }

    fun w(message: String) {
        if (!jetpackMvvmLog) return
        Logger.w(message)
    }

    fun e(message: String) {
        if (!jetpackMvvmLog) return
        Logger.e(message)
    }
}