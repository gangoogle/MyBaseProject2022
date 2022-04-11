package com.rms.supply.viewmodel.request

import android.app.Application
import androidx.lifecycle.scopeNetLife
import com.drake.net.Get
import com.leaf.common.base.BaseViewModel
import com.rms.supply.api.base.MyApp
import com.rms.supply.api.network.RequestCallBack
import com.rms.supply.data.bean.HomeBean

/**
 * 持有网络请求
 */
class RequestViewModel(app: Application = MyApp.instance) : BaseViewModel(app) {

    fun getHomeList(call: RequestCallBack<HomeBean>) {
        showLoadingDialog()
        scopeNetLife {
            call.onSucc(
                Get<HomeBean>("article/list/0/json").await()
            )
        }.finally {
            hideLoadingDialog()
            call.onError(it)
        }
    }


}