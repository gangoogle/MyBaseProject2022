package com.leaf.oceanshipping.api.base

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.hjq.toast.ToastUtils
import com.leaf.common.base.BaseViewModel
import com.leaf.common.base.BaseVmDbActivity
import com.leaf.common.util.RxBarTool
import com.leaf.common.util.StatusBarUtil
import com.rms.supply.api.base.StatusHolder
import com.rms.supply.data.enumdata.BarLightMode
import com.rms.supply.ui.activity.SplashActivity
import com.rms.supply.util.SettingUtil
import com.rms.supply.viewmodel.request.RequestViewModel
import com.rms.supply.widget.dialog.MyProgressDialog

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {
    private var mProgressDialog: MyProgressDialog? = null
    lateinit var mActivity: AppCompatActivity
    private var mStatusBarView: View? = null
    protected val mRequestViewModel by viewModels<RequestViewModel>()

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (StatusHolder.getInstance().isKill) {
            Log.d("app_recycler", "app was kill")
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else {
            Log.d("app_recycler", "app was normal")
        }
        mActivity = this;
        if (fullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        super.onCreate(savedInstanceState)
        initStatusBar()
        addLoadingObserve(mRequestViewModel)
    }

    override fun onResume() {
        super.onResume()

    }

    fun initStatusBar() {
        val barColor = defaultBarColor()
        if (!fullScreen()) {
            //?????????????????????????????????
            if (statusBarNoPadding()) {
                setStatuBarTransparency()
                StatusBarUtil.setStatusBarColor(this, barColor)
            } else {
                setStatuBarTransparency()
                addStatusViewWithColor(barColor)
            }
        }
    }


    override fun showLoading(msg: String) {
        if (this.isFinishing || this.isDestroyed) {
            return
        }
        if (mProgressDialog == null) {
            mProgressDialog = MyProgressDialog(this)
        }
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.initDialog(msg)
    }

    override fun hideLoading() {
        if (this.isFinishing || this.isDestroyed) {
            return
        }
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dissmisDialog()
        }
    }


    fun activityLogout() {

    }


    fun showErrorToast(msg: String = resources.getString(com.leaf.common.R.string.str_data_get_error)) {
        ToastUtils.show(msg)
    }

    protected open fun fullScreen(): Boolean {
        return false
    }

    protected open fun defaultBarColor(): Int {
        return SettingUtil.getPrimaryColor()
    }

    /**
     * ?????????????????????
     */
    protected open fun setStatuBarTransparency() {
        StatusBarUtil.transparencyBar(this)
        val barLightMode: BarLightMode = getStatuBarLightMode()
        if (barLightMode == BarLightMode.BLACK) {
            StatusBarUtil.StatusBarLightMode(this)
        } else {
        }
    }

    /**
     * ????????????????????? ????????????
     *
     * @return
     */
    protected open fun getStatuBarLightMode(): BarLightMode {
        return BarLightMode.WHITE
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    protected open fun statusBarNoPadding(): Boolean {
        return false
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param color
     */
    protected open fun addStatusViewWithColor(color: Int) {
        //?????? paddingTop
        val rootView = this.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rootView.setPadding(0, RxBarTool.getStatusBarHeight(this), 0, 0)
            val decorView = this.window.decorView as ViewGroup
            mStatusBarView = View(this)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                RxBarTool.getStatusBarHeight(this)
            )
            mStatusBarView?.let {
                it.setBackgroundColor(color)
                decorView.addView(it, lp)
            }

        }
    }

    /**
     * ?????????????????????
     *
     * @param color
     */
    open fun changeStatusViewColor(color: Int) {
        if (mStatusBarView != null) {
            val drawable = mStatusBarView!!.background
            if (drawable is ColorDrawable) {
                val oldColor = drawable.color
                //???????????????????????????????????????????????????"backgroundColor",???????????????????????????????????????,????????????????????????????????????????????????
                val animator: ValueAnimator =
                    ObjectAnimator.ofInt(mStatusBarView, "backgroundColor", oldColor, color)
                animator.duration = 100
                animator.setEvaluator(ArgbEvaluator())
                //??????????????????????????????ArgbEvaluator??????????????????????????????????????????????????????????????????????????????
                animator.start()
            } else {
                mStatusBarView!!.setBackgroundColor(color)
            }
        }
    }


    fun <T> getSerialParams(): T {
        return intent.getSerializableExtra("data") as T
    }


}