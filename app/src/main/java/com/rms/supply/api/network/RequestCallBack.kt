package com.rms.supply.api.network



interface RequestCallBack<T> {
    fun onSucc(result: T)
    fun onError(exception: Throwable?)
}