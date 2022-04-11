package com.rms.supply.database

import android.app.Activity
import android.content.Intent
import com.leaf.common.ext.lifecycle.KtxActivityManger
import com.rms.supply.util.MMKVCacheUtils

/**
 * @Author: zgYi gangoogle@163.com
 * @CreateDate: 15:36
 * @Description: 用户信息缓存
 */
object UserCacheExt {
    private const val DEVICE_ID = "DEVICE_ID"

    //token
    private const val USER_TOKEN = "user_token"
    private const val USER_TOKEN_GET_TIME = "user_token_get_time"
    private const val USER_TOKEN_EXPECT_TIME = "user_token_expect_time"

    //用户相关信息保存
    private const val USER_JobNum = "JobNum"
    private const val USER_Name = "Name"
    private const val USER_BindDriverId = "BindDriverId"
    private const val USER_DriverId = "UserDriverId"

    private const val LOGIN_USERNAME = "username"
    private const val LOGIN_PWD = "pwd"

    private const val LOGIN_RESULT = "loginResultStr"
    private const val LOGIN_STATE = "loginState"


    /**
     * 获取数据库名称
     */
    fun getDbName(): String {
//        if (userJobNum.isNullOrEmpty()) {
//            if (!userName.isNullOrEmpty()) {
//                return userName
//            }
//            return loginUserName
//        }
        return "default"
    }




}
