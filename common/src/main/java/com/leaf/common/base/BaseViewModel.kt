package com.leaf.common.base

import android.app.Application
import androidx.lifecycle.ViewModel
import com.leaf.common.callback.livedata.event.EventLiveData
import com.leaf.common.network.AppException
import com.rxjava.rxlife.ScopeViewModel


/**
 * @Author: zgYi gangoogle@163.com
 * @CreateDate: 19:17
 * @Description:
 */
open class BaseViewModel : ScopeViewModel {

    public constructor(application: Application = MyKtx.app) : super(application)

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }


    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }

    }


    fun showLoadingDialog(showStr: String = "") {
        loadingChange.showDialog.postValue(showStr)
    }

    fun hideLoadingDialog() {
        loadingChange.dismissDialog.postValue(true)
    }


}