package com.leaf.common

import android.R

import android.os.Bundle


/**
 * 常量类
 * author yechen
 */
object Constants {
    const val RESULT_CODE_ZXING = 5
    const val REQUEST_CODE_ZXING = 6
    const val TIME_COMMAND = 60 * 1000
    const val ZXING_RESULT = "zxing_reult"

    const val RQ_CODE_FILE_CHOOSE = 230

    //上传文件
    const val TOKEN = "Cb_WlfgaA4UTOxdiA37YsPWsX7s_qo9l3s2wTW8Y"
    const val APPLYAUTH = "http://mteapi.1rms.cn/us3/applyAuth"
    const val APPLYPRIVATEURLAUTH = "http://mteapi.1rms.cn/us3/applyPrivateUrlAuth"
    const val KEY_REGION = "cn-bj"
    const val KEY_PROXY_SUFFIX = "ufileos.com"
    const val BUCKETNAME = "testfsr"
    const val CUSTOM_PRODUCTS = "custom"

    //企业微信
    const val WORK_WX_APPID: String = "wwff91225fad8fcc07"
    const val WORK_WX_AGENTID = "1000112"
    const val WORK_WX_SCHEMA = "wwauthff91225fad8fcc07000112"

    // 小米推送
    const val MI_PUSH_APP_ID = "2882303761520134674"
    const val Mi_PUSH_APP_KEY = "5922013454674"


    //OCR 最大图片尺寸
    const val MAX_OCR_SIZE_LEN = 2680

    //自动清除拍摄文件删除 3个月  7776000000L
    const val AUTO_CLEAR_CACHE_SIZE = 7776000000L

    //US3 文件分割大小
    const val US3_BIG_FILE = 4194304L


}