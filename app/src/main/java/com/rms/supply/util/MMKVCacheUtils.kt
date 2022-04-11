package com.rms.supply.util

import android.content.Context
import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

/**
 * 腾讯MMKV存储
 */
object MMKVCacheUtils {
    private val mmkv by lazy {
        MMKV.defaultMMKV()
    }

    /**
     * 存储
     */
    fun save(key: String?, `object`: Any) {
        if (`object` is String) {
            mmkv.encode(key, `object`)
        } else if (`object` is Int) {
            mmkv.encode(key, `object`)
        } else if (`object` is Boolean) {
            mmkv.encode(key, `object`)
        } else if (`object` is Float) {
            mmkv.encode(key, `object`)
        } else if (`object` is Long) {
            mmkv.encode(key, `object`)
        } else {
            mmkv.encode(key, `object`.toString())
        }
    }

    /**
     * 获取保存的数据
     */
    fun get(key: String?, defaultObject: Any?): Any? {
        return if (defaultObject is String) {
            mmkv.decodeString(key, defaultObject as String?)
        } else if (defaultObject is Int) {
            mmkv.decodeInt(key, (defaultObject as Int?)!!)
        } else if (defaultObject is Boolean) {
            mmkv.decodeBool(key, (defaultObject as Boolean?)!!)
        } else if (defaultObject is Float) {
            mmkv.decodeFloat(key, (defaultObject as Float?)!!)
        } else if (defaultObject is Long) {
            mmkv.decodeLong(key, (defaultObject as Long?)!!)
        } else {
            mmkv.decodeString(key, null)
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    fun remove(key: String?) {
        mmkv.remove(key)
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        mmkv.clearAll()
    }

    /**
     * 查询某个key是否存在
     */
    fun contain(key: String?): Boolean {
        return mmkv.containsKey(key)
    }


}