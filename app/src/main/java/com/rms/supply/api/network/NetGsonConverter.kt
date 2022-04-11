package com.rms.supply.api.network

import com.drake.net.NetConfig
import com.drake.net.convert.NetConverter
import com.drake.net.exception.ConvertException
import com.drake.net.exception.RequestParamsException
import com.drake.net.exception.ResponseException
import com.drake.net.exception.ServerResponseException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rms.supply.api.network.adapter.IntegerAdapter
import com.rms.supply.api.network.adapter.LongAdapter
import com.rms.supply.api.network.adapter.StringNullAdapter
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * 自定义数据解析器
 */
class NetGsonConverter(
    val success: String = "0",
    val code: String = "errorCode",
    val message: String = "errorMsg",
    val data: String = "data"
) : NetConverter {

    private val mGson = buildGsonAdapter()

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bodyString = response.body?.string() ?: return null
                    return try {
                        val json = JSONObject(bodyString) // 获取JSON中后端定义的错误码和错误信息
                        if (json.getString(this.code) == success) { // 对比后端自定义错误码
                            //脱壳
                            val dataJson = json.getString(this.data)
                            val result = dataJson.parseBody<R>(succeed)
                            result
                        } else { // 错误码匹配失败, 开始写入错误异常
                            val errorMessage = json.optString(
                                message,
                                NetConfig.app.getString(com.drake.net.R.string.no_error_message)
                            )
                            throw ResponseException(response, errorMessage, tag = json.getString(this@NetGsonConverter.code))
                        }
                    } catch (e: JSONException) { // 固定格式JSON分析失败直接解析JSON
                        bodyString.parseBody<R>(succeed)
                    }
                }
                code in 400..499 -> throw RequestParamsException(
                    response,
                    code.toString()
                ) // 请求参数错误
                code >= 500 -> throw ServerResponseException(response, code.toString()) // 服务器异常错误
                else -> throw ConvertException(response)
            }
        }
    }

    fun <R> String.parseBody(succeed: Type): R? {
        return mGson.fromJson(this, succeed)
    }

    /**
     * GSON解析时处理Long,Integer
     */
    private fun buildGsonAdapter(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(String::class.java, StringNullAdapter())
            .registerTypeAdapter(Int::class.java, IntegerAdapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerAdapter())
            .registerTypeAdapter(Long::class.java, LongAdapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongAdapter())
            .create()
    }

}