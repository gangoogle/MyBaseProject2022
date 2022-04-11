package com.leaf.common.listener

open interface OceanShipListener {
    fun click(type: Int, url: String?, id: String?, oceanShipId: String?, createTime: Long?)
}