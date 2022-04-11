package com.rms.supply.ui.activity

import android.os.Bundle
import android.util.Log
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.leaf.oceanshipping.api.base.BaseActivity
import com.rms.supply.R
import com.rms.supply.api.ext.getResult
import com.rms.supply.databinding.ActivityMainBinding
import com.rms.supply.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initListener() {
        super.initListener()
        mDatabind.btGetHome.setOnClickListener {
            YoYo.with(Techniques.Shake)
                .duration(500)
                .repeat(1)
                .playOn(mDatabind.btGetHome)
            mRequestViewModel.getHomeList(getResult({
                mDatabind.tvDes.text = it.toString()
            }, {
                Log.d("network", "getHomeList=ErrorHandler:${it}")
            }))
        }
    }


    override fun createObserver() {

    }
}