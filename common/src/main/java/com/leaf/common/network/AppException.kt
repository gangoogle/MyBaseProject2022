package com.leaf.common.network

import com.leaf.common.network.Error

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/17
 * 描述　:自定义错误信息异常
 */
class AppException : Exception {

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? = "" //错误日志
    var throwable: Throwable? = null
    var isLogicError: Boolean = false

    constructor(errCode: Int, errorMsg: String = "请求失败，请稍后再试") {
        this.errCode = errCode
        this.errorMsg = errorMsg
    }

    constructor(
        errCode: Int,
        error: String?,
        errorLog: String? = "",
        throwable: Throwable? = null,
        isLogicError: Boolean = false
    ) : super(error) {
        this.errorMsg = error ?: "请求失败，请稍后再试"
        this.errCode = errCode
        this.errorLog = errorLog ?: this.errorMsg
        this.throwable = throwable
        this.isLogicError = isLogicError
    }

    constructor(error: Error, e: Throwable?) {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
        throwable = e
        isLogicError = false
    }
}