package com.leaf.oceanshipping.api.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.hjq.toast.ToastUtils
import com.leaf.common.base.BaseViewModel
import com.leaf.common.base.BaseVmDbFragment
import com.rms.supply.widget.dialog.MyProgressDialog

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    private var mProgressDialog: MyProgressDialog? = null
    protected var isFragmentVisible = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun showLoading(message: String) {
        if (mActivity.isFinishing || mActivity.isDestroyed) {
            return
        }
        if (mProgressDialog == null) {
            mProgressDialog = MyProgressDialog(mActivity)
        }
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.initDialog(message)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFragmentVisible = userVisibleHint
    }

    override fun hideLoading() {
        if (mActivity.isFinishing || mActivity.isDestroyed) {
            return
        }
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dissmisDialog()
        }
    }

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }




    fun showErrorToast(msg: String = resources.getString(com.leaf.common.R.string.str_data_get_error)) {
        ToastUtils.show(msg)
    }

    fun <T> getSerialParams(): T {
        return arguments?.getSerializable("data") as T
    }


}