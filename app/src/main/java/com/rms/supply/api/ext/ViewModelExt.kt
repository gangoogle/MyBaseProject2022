package com.rms.supply.api.ext

import androidx.lifecycle.scopeNetLife
import com.leaf.common.base.BaseViewModel
import com.leaf.common.base.BaseVmActivity
import com.rms.supply.api.network.RequestCallBack
import kotlinx.coroutines.CoroutineScope


fun <T> BaseVmActivity<*>.getResult(
    onSuccess: (T) -> Unit,
    onError: ((String) -> Unit)? = null,
): RequestCallBack<T> {
    return object : RequestCallBack<T> {
        override fun onSucc(result: T) {
            onSuccess.invoke(result)
        }

        override fun onError(exception: Throwable?) {
            if (exception != null) {
                onError?.invoke(exception?.message ?: "")
            }
        }


    }
}


