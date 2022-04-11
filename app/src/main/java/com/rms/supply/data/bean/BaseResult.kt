package com.rms.supply.data.bean

import com.google.gson.annotations.SerializedName


data class BaseResult<T>(
    @SerializedName("data")
    val `data`: T,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
)