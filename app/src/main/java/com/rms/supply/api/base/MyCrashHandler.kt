package com.rms.supply.api.base

import android.content.Context
import com.rms.supply.api.ext.appExit

/**
 * @author zgYi
 * @date 2019/12/24
 * @Description: 全局异常捕获
 */
class MyCrashHandler : Thread.UncaughtExceptionHandler {

    /**
     *  单例对象
     */
    companion object {
        val Instance by lazy { MyCrashHandler() }
    }

    private lateinit var mContext: Context

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        try {
            Thread.sleep(500)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //退出程序
        appExit(MyApp.instance)
    }

    /**
     * 在Application 中进行全局初始化
     */
    fun init(context: Context) {
        mContext = context
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
}