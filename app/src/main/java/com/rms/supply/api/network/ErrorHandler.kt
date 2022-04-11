package com.rms.supply.api.network

import android.util.Log
import android.view.View
import android.widget.Toast
import com.drake.net.Net
import com.drake.net.NetConfig
import com.drake.net.R
import com.drake.net.exception.*
import com.drake.net.interfaces.NetErrorHandler
import com.drake.net.utils.TipUtils
import com.hjq.toast.ToastUtils
import com.leaf.common.util.XLog
import java.net.UnknownHostException

class ErrorHandler : NetErrorHandler {

    override fun onError(e: Throwable) {
        val message = when (e) {
            is UnknownHostException -> NetConfig.app.getString(R.string.net_host_error)
            is URLParseException -> NetConfig.app.getString(R.string.net_url_error)
            is NetConnectException -> NetConfig.app.getString(R.string.net_connect_error)
            is NetworkingException -> NetConfig.app.getString(R.string.net_networking_error)
            is NetSocketTimeoutException -> NetConfig.app.getString(
                R.string.net_connect_timeout_error,
                e.message
            )
            is DownloadFileException -> NetConfig.app.getString(R.string.net_download_error)
            is ConvertException -> NetConfig.app.getString(R.string.net_parse_error)
            is RequestParamsException -> NetConfig.app.getString(R.string.net_request_error)
            is ServerResponseException -> NetConfig.app.getString(R.string.net_server_error)
            is NullPointerException -> NetConfig.app.getString(R.string.net_null_error)
            is NoCacheException -> NetConfig.app.getString(R.string.net_no_cache_error)
            is ResponseException -> e.message
            is HttpFailureException -> NetConfig.app.getString(R.string.request_failure)
            is NetException -> NetConfig.app.getString(R.string.net_error)
            else -> NetConfig.app.getString(R.string.net_other_error)
        }
        Net.printStackTrace(e)
        Log.d("network", "errorHandler:${e.message}")
        if (e is ResponseException && e.tag == -1001) { // 判断异常为token失效
            // 打开登录界面或者弹登录失效对话框

        } else {
            ToastUtils.show(message)
        }
    }

    override fun onStateError(e: Throwable, view: View) {
        super.onStateError(e, view)

    }
}