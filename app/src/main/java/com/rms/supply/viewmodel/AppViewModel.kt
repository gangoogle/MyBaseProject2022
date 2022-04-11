package com.rms.supply.viewmodel

import androidx.lifecycle.ViewModel
import com.leaf.oceanshipping.data.params.BigParamsCache

/**
 * @Author: zgYi gangoogle@163.com
 * @CreateDate: 19:14
 * @Description: 保存App全局数据
 */
class AppViewModel : ViewModel() {
    //缓存大数据
    val mIntentBigParams by lazy {
        BigParamsCache()
    }





    fun manualClear() {
        mIntentBigParams.clear()
    }
}