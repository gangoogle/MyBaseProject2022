package com.rms.supply.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.rms.supply.R
import com.rms.supply.data.bean.OrderBean
import kotlinx.android.synthetic.main.layout_num_edit.view.*

/**
 * @Author: zgYi
 * @CreateDate: 2022/3/4
 * @Description: 船信息弹窗
 */
class OrderInputDialog(
    val mContext: Activity,
    val onNumChange: (num: Int) -> Unit,
    val onDialogClose: () -> Unit
) :
    BaseDialog(mContext) {
    lateinit var mView: View

    init {
        onInit()
    }

    override fun initDialog(context: Activity?): Dialog {
        mView = View.inflate(context, R.layout.layout_num_edit, null)
        val dialog = CustomWHDialog(
            context,
            mView,
            R.style.dialog,
            1,
            Gravity.BOTTOM,
            ConvertUtils.dp2px(50f)
        )
        mView.bt_cancel.setOnClickListener {
            dismissDialog()
        }
        dialog.window!!.setWindowAnimations(R.style.dialog_anim_right_to_left)
        dialog.setOnDismissListener {
            onDialogClose.invoke()
        }
        return dialog
    }

    var numString = ""

    fun setData(bean: OrderBean) {
        mView.tv_name.text = bean.name
        mView.tv_curr_num.text = "数量：" + bean.orgNum.toString()

        numString = getRealNum(bean.editNum)
        mView.tv_change_num.text = "实收：" + numString
        mView.bt_num_0.setOnClickListener {
            onChangeNum(0)
        }
        mView.bt_num_1.setOnClickListener {
            onChangeNum(1)
        }
        mView.bt_num_2.setOnClickListener {
            onChangeNum(2)
        }
        mView.bt_num_3.setOnClickListener {
            onChangeNum(3)
        }
        mView.bt_num_4.setOnClickListener {
            onChangeNum(4)
        }
        mView.bt_num_5.setOnClickListener {
            onChangeNum(5)
        }
        mView.bt_num_6.setOnClickListener {
            onChangeNum(6)
        }
        mView.bt_num_7.setOnClickListener {
            onChangeNum(7)
        }
        mView.bt_num_8.setOnClickListener {
            onChangeNum(8)
        }
        mView.bt_num_9.setOnClickListener {
            onChangeNum(9)
        }

        mView.bt_num_clear.setOnClickListener {
            numString = ""
            updateNumString()
        }
        mView.bt_num_delete.setOnClickListener {
            if (numString.length > 0) {
                numString = numString.substring(0, numString.length - 1)
                updateNumString()
            }
        }
        mView.bt_sure.setOnClickListener {
            var num = -1
            if (!numString.isNullOrEmpty()) {
                num = numString.toInt()
            }
            onNumChange.invoke(num)
            dismissDialog()
        }

    }

    fun updateNumString() {
        mView.tv_change_num.text = "实收：" + numString
    }

    fun onChangeNum(inputNum: Int) {

        if (numString.isNullOrEmpty()) {
            numString = numString + inputNum
            updateNumString()
        } else {
            if (numString.first() == '0') {
                return
            } else {
                numString = numString + inputNum
                updateNumString()
            }
        }


    }

    fun getRealNum(num: Int): String {
        if (num < 0) {
            return ""
        }
        return num.toString()
    }

}