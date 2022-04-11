package com.rms.supply.api.base


import android.app.Application
import android.view.Gravity
import androidx.multidex.MultiDex
import com.hjq.toast.ActivityToast
import com.hjq.toast.ToastStrategy
import com.hjq.toast.ToastUtils
import com.hjq.toast.config.IToast
import com.leaf.common.base.BaseApp
import com.leaf.oceanshipping.data.params.BigParamsCache

import com.rms.supply.api.network.NetWorkHandle
import com.rms.supply.viewmodel.AppViewModel


//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { MyApp.appViewModelInstance }


class MyApp : BaseApp() {

    companion object {
        lateinit var instance: MyApp
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        instance = this
        MyCrashHandler.Instance.init(this)
        //初始化网络框架
        NetWorkHandle().create(this)
        initToast()
    }


    /**
     * 初始化Toast
     */
    private fun initToast() {
        ToastUtils.init(this, object : ToastStrategy() {
            override fun createToast(application: Application?): IToast {
                val toast = super.createToast(application)
                if (toast is ActivityToast) {
                    // 设置短 Toast 的显示时长（默认是 2000 毫秒）
                    toast.setGravity(Gravity.BOTTOM, 0, 100)
                }
                return toast
            }
        })
    }


}