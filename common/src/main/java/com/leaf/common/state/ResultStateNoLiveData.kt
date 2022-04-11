package com.leaf.common.state

import androidx.lifecycle.MutableLiveData
import com.leaf.common.network.AppException
import com.leaf.common.network.BaseResponse
import com.leaf.common.network.ExceptionHandle

class ResultStateNoLiveData {

    companion object {
        /**
         * 处理返回值
         * @param result 请求结果
         */
        fun <T> paresResult(result: BaseResponse<T>, onAppSucc: (T) -> Unit, onError: (AppException) -> Unit) {
            if (result.isSucces()) {
                onAppSucc.invoke(result.getResponseData())
            } else {
                onError.invoke(
                    AppException(
                        result.getResponseCode(),
                        result.getResponseMsg(),
                        isLogicError = true
                    )
                )
            }
        }
    }

}
