package com.rms.supply.api.ext

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.leaf.common.ext.view.clickNoRepeat
import com.rms.supply.R

/**
 * @author : zgyi
 * @date : 2022/1/12
 */


fun FragmentActivity.showCommonDialog(
    title: String = this.resources.getString(R.string.str_dialog_title),
    msg: String = "",
    left: String = this.resources.getString(R.string.str_sure),
    right: String = this.resources.getString(R.string.str_cancel),
    canTouch: Boolean = false,
    canCancel: Boolean = false,
    msgGravity: Int = Gravity.LEFT,
    sureClick: () -> Unit = {},
    cancelClick: (() -> Unit)? = null,
    isSingleButton: Boolean = false
): Dialog {
    val dialog = AlertDialog.Builder(this).create()
    val view = View.inflate(this, R.layout.layout_dialog_confirm, null)
    dialog.setView(view.apply {
        findViewById<TextView>(R.id.tv_title).text = title
        findViewById<TextView>(R.id.tv_message).apply {
            text = msg
            gravity = msgGravity
        }
        findViewById<TextView>(R.id.tv_left).apply {
            text = left
            clickNoRepeat {
                sureClick.invoke()
                dialog.dismiss()
            }

        }
        findViewById<TextView>(R.id.tv_right).apply {
            text = right
            clickNoRepeat {
                cancelClick?.invoke()
                dialog.dismiss()
            }
        }

    })
    if (isSingleButton) {
        view.findViewById<TextView>(R.id.tv_right).visibility = View.GONE
        view.findViewById<View>(R.id.view_split_line).visibility = View.GONE
    }
    dialog.setCancelable(canCancel)
    dialog.setCanceledOnTouchOutside(canTouch)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}







fun getEmptyView(isNetError: Boolean): Int {
    if (isNetError) {
        return com.leaf.common.R.layout.layout_error_noretry
    }
    return com.leaf.common.R.layout.layout_empty_noretry
}
