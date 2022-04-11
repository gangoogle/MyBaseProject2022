package com.rms.supply.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.leaf.common.base.BaseViewModel
import com.leaf.common.util.XLog
import com.leaf.oceanshipping.api.base.BaseActivity
import com.rms.supply.R
import com.rms.supply.api.base.StatusHolder
import com.rms.supply.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    lateinit var flow: Flow<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        //防止从桌面打开app时 重新开启app
        if (!this.isTaskRoot) {
            super.onCreate(savedInstanceState)
            finish()
            return
        }
        StatusHolder.getInstance().isKill = false
        super.onCreate(savedInstanceState)
        setupFlow()
    }

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {
    }

    override fun initListener() {
        super.initListener()
        mDatabind.btGoMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mDatabind.btFlow.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                flow.collect {
                    XLog.d("yzg", it.toString())
                }
            }
        }
        mDatabind.btOrderList.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }
    }

    fun setupFlow() {
        flow = flow {
            (0..10).forEach {
                kotlinx.coroutines.delay(100)
                emit(it)
            }
        }.flowOn(Dispatchers.Default)

    }
}