package com.rms.supply.api.network

import android.app.ProgressDialog
import android.view.View
import com.drake.net.NetConfig
import com.drake.net.exception.ResponseException
import com.drake.net.interfaces.NetErrorHandler
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setDialogFactory
import com.drake.net.okhttp.setErrorHandler
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.leaf.common.base.appContext
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.rms.supply.BuildConfig
import com.rms.supply.api.base.AppConfig
import com.rms.supply.api.base.MyApp
import com.rms.supply.api.base.UrlConfig
import okhttp3.Cache
import java.io.File
import java.util.concurrent.TimeUnit

class NetWorkHandle {

    fun create(app: MyApp) {
        app.apply {
            val cookieJar: PersistentCookieJar by lazy {
                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
            }

            NetConfig.initialize(UrlConfig.BASE_URL, this) {
                cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024L))
                //添加Cookies自动持久化
                cookieJar(cookieJar)
                addInterceptor(MyHeadInterceptor())
                setConverter(NetGsonConverter())
                if (BuildConfig.DEBUG) {
                    addInterceptor(OkHttpProfilerInterceptor())
                }
                //超时时间 连接、读、写
                connectTimeout(AppConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)

                //错误处理
                setErrorHandler(ErrorHandler())
            }
        }
    }
}