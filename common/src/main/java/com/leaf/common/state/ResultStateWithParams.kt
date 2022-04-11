package com.leaf.common.state

import androidx.lifecycle.MutableLiveData
import com.leaf.common.network.AppException
import com.leaf.common.network.BaseResponse
import com.leaf.common.network.ExceptionHandle

/**
 * 作者　: hegaojian
 * 时间　: 2020/4/9
 * 描述　: 自定义结果集封装类
 */
sealed class ResultStateWithParams<out T, out D> {

    companion object {
        fun <T, D> onAppSuccess(data: T, params: D): ResultStateWithParams<T, D> =
            Success(data, params)

        fun <T, D> onAppLoading(loadingMessage: String): ResultStateWithParams<T, D> =
            Loading(loadingMessage)

        fun <T, D> onAppError(error: AppException, params: D): ResultStateWithParams<T, D> =
            Error(error, params)
    }

    data class Loading(val loadingMessage: String) : ResultStateWithParams<Nothing, Nothing>()
    data class Success<out T, out D>(val data: T, val params: D) : ResultStateWithParams<T, D>()
    data class Error<out D>(val error: AppException, val params: D) :
        ResultStateWithParams<Nothing, D>()
}

/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T, D> MutableLiveData<ResultStateWithParams<T, D>>.paresResult(
    result: BaseResponse<T>,
    params: D
) {
    value = when {
        result.isSucces() -> {
            ResultStateWithParams.onAppSuccess(result.getResponseData(), params)
        }
        else -> {
            ResultStateWithParams.onAppError(
                AppException(
                    result.getResponseCode(),
                    result.getResponseMsg(),
                    isLogicError = true
                ), params
            )
        }
    }
}


/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T, D> MutableLiveData<ResultStateWithParams<T, D>>.paresResult(result: T, params: D) {
    value = ResultStateWithParams.onAppSuccess(result, params)
}

/**
 * 异常转换异常处理
 */
fun <T, D> MutableLiveData<ResultStateWithParams<T, D>>.paresException(e: Throwable, params: D) {
    this.value = ResultStateWithParams.onAppError(ExceptionHandle.handleException(e), params)
}

