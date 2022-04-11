package com.rms.supply.api.base

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * 自定义recyclerview滚动方式
 */
class TopSmoothScroller internal constructor(context: Context) : LinearSmoothScroller(context) {

    override fun getHorizontalSnapPreference(): Int {
        return LinearSmoothScroller.SNAP_TO_START//具体见源码注释
    }

    override fun getVerticalSnapPreference(): Int {
        return LinearSmoothScroller.SNAP_TO_START//具体见源码注释
    }

}