package com.leaf.common.util

import com.orhanobut.logger.Logger

const val TAG = "JetpackMvvm"

/**
 *
 * 是否需要开启打印日志，默认打开，1.1.7-1.1.8版本是默认关闭的(1.0.0-1.1.6没有这个字段，框架在远程依赖下，直接不打印log)，但是默认关闭后很多人反馈都没有日志，好吧，我的我的
 * 根据true|false 控制网络请求日志和该框架产生的打印
 */
var jetpackMvvmLog = true

enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG) =
    Logger.v( this)

fun String.logd(tag: String = TAG) =
    Logger.d( this)

fun String.logi(tag: String = TAG) =
    Logger.i( this)

fun String.logw(tag: String = TAG) =
    Logger.w( this)

fun String.loge(tag: String = TAG) =
    Logger.e( this)
