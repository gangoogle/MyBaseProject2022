package com.leaf.oceanshipping.data.params

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @Author: zgYi
 * @CreateDate: 2022/1/12
 * @Description: 页面跳转时暂时缓存大数据 取出后立即移除    浅拷贝
 */
class BigParamsCache {

    private val pageJumpBigParams = hashMapOf<String, Any>()

    /**
     * 保存数据
     */
    fun putData(key: String, value: Any) {
        pageJumpBigParams.put(key, value)
    }


    /**
     * 获取深copy数据
     */
    fun <T> getDataWithDeepCopy(key: String): T? {
        if (pageJumpBigParams.containsKey(key)) {
            val value = pageJumpBigParams[key]
            pageJumpBigParams.remove(key)
            val copyValue = deepCopy(value)
            return copyValue as T
        }
        return null
    }


    fun clear() {
        pageJumpBigParams.clear()
    }

    /**
     * 深拷贝
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun deepCopy(obj: Any?): Any? {
        // 所以利用这个特性可以实现对象的深拷贝。
        if (obj == null) {
            return null
        }
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(obj)
        // 将流序列化成对象
        val bis = ByteArrayInputStream(bos.toByteArray())
        val ois = ObjectInputStream(bis)
        return ois.readObject()
    }
}