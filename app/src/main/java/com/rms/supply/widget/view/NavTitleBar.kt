package com.rms.supply.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ConvertUtils.*
import com.leaf.common.ext.view.clickNoRepeat
import com.leaf.common.util.RxBarTool.getStatusBarHeight
import com.rms.supply.R
import kotlinx.android.synthetic.main.layout_nav_title_bar.view.*

/**
 * @author zgYi
 * @date 2019/6/15
 * @Description: 导航栏
 */
class NavTitleBar : FrameLayout {
    private var mContext: Context? = null
    private var leftTextShow = false
    private var rightTextShow = false
    private var rightTextShowWithIcon = false
    private var leftText: String? = null
    private var rightText: String? = null
    private var rightTextWithIcon: String? = null
    private var bgColor = 0
    private var rightIconShow = false
    private var rightIconId = 0
    private var middleText: String? = null
    private var middleTextShow = true
    private var showBackIcon = false
    private var attrLeftTextSize = 0
    private var attrRightTextSize = 0
    private var attrRightTextSizeWithIcon = 0
    private var backIconId = 0
    private var attrRightIconSize = 0
    private var mOnBackListener: OnBackListener? = null
    private var mOnCenterListener: OnCenterListener? = null
    private var mOnRightListener: (() -> Unit?)? = null
    private var mOnCommonFinishListener: (() -> Unit?)? = null
    private var mOnRightIconListener: (() -> Unit?)? = null
    private var onRightTextWithIconListener: (() -> Unit?)? = null
    private var barStatusMargin = false
    private var backGroundAlpha = 0f

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.layout_nav_title_bar, this, true)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavTitleBar)
        leftTextShow = typedArray.getBoolean(R.styleable.NavTitleBar_leftTextShow, false)
        leftText = typedArray.getString(R.styleable.NavTitleBar_leftText)
        rightTextShow = typedArray.getBoolean(R.styleable.NavTitleBar_rightTextShow, false)
        rightTextShowWithIcon =
            typedArray.getBoolean(R.styleable.NavTitleBar_rightTextShowWithIcon, false)
        rightText = typedArray.getString(R.styleable.NavTitleBar_rightText)
        rightTextWithIcon = typedArray.getString(R.styleable.NavTitleBar_rightTextWithIcon)
        bgColor = typedArray.getColor(
            R.styleable.NavTitleBar_backGroundColor,
            resources.getColor(com.leaf.common.R.color.colorPrimary)
        )
        barStatusMargin = typedArray.getBoolean(R.styleable.NavTitleBar_barStatusMargin, false)
        backGroundAlpha = typedArray.getFloat(R.styleable.NavTitleBar_backGroundAlpha, 1f)
        rightIconShow = typedArray.getBoolean(R.styleable.NavTitleBar_rightIconShow, false)
        rightIconId = typedArray.getResourceId(
            R.styleable.NavTitleBar_rightIcon,
            com.leaf.common.R.mipmap.img_shezhi
        )
        middleText = typedArray.getString(R.styleable.NavTitleBar_middleText)
        middleTextShow = typedArray.getBoolean(R.styleable.NavTitleBar_middleTextShow, true)
        showBackIcon = typedArray.getBoolean(R.styleable.NavTitleBar_showBackIcon, true)
        attrLeftTextSize = typedArray.getDimensionPixelSize(
            R.styleable.NavTitleBar_leftTextSize,
            sp2px(19f)
        )
        attrRightTextSize = typedArray.getDimensionPixelSize(
            R.styleable.NavTitleBar_rightTextSize,
            sp2px(16f)
        )
        attrRightTextSizeWithIcon = typedArray.getDimensionPixelSize(
            R.styleable.NavTitleBar_rightTextSizeWithIcon,
            sp2px(16f)
        )
        backIconId =
            typedArray.getResourceId(R.styleable.NavTitleBar_backIcon,  com.leaf.common.R.mipmap.img_fanhuibais)
        attrRightIconSize = typedArray.getDimensionPixelSize(
            R.styleable.NavTitleBar_rightIconSize,
            sp2px(30f)
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        tv_title!!.visibility = if (leftTextShow) VISIBLE else GONE
        tv_title!!.text = leftText
        if (!leftTextShow) {
            iv_return!!.setPadding(dp2px(15f), 0, dp2px(15f), 0)
        }
        tv_delete_text!!.visibility =
            if (rightTextShow) VISIBLE else GONE
        tv_delete_text!!.text = rightText
        tv_right_text_with_icon!!.text = rightTextWithIcon
        fl_bottom_bg!!.setBackgroundColor(bgColor)
        tv_title!!.textSize = px2sp(attrLeftTextSize.toFloat()).toFloat()
        tv_delete_text!!.textSize = px2sp(attrRightTextSize.toFloat()).toFloat()
        tv_right_text_with_icon!!.textSize = px2sp(attrRightTextSizeWithIcon.toFloat()).toFloat()
        //设置图片宽度
        val rightIconLayoutParams = iv_delete_icon!!.layoutParams as RelativeLayout.LayoutParams
        rightIconLayoutParams.width = attrRightIconSize
        iv_delete_icon!!.layoutParams = rightIconLayoutParams

        //设置状态栏间距
        if (barStatusMargin) {
            val statusBarHeight: Int = getStatusBarHeight(mContext)
            fl_holder!!.setPadding(0, statusBarHeight, 0, 0)
        }
        fl_bottom_bg!!.alpha = backGroundAlpha
        //设置右侧图标
        if (rightIconShow) {
            iv_delete_icon!!.visibility = VISIBLE
            iv_delete_icon!!.setImageResource(rightIconId)
        }
        //是否显示返回按钮
        if (!showBackIcon) {
            iv_return!!.visibility = GONE
            //            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTvTitle.getLayoutParams();
//            params.setMarginStart(RxImageTool.dip2px(15));
//            mTvTitle.setLayoutParams(params);
        } else {
            iv_return!!.visibility = VISIBLE
            iv_return!!.setImageResource(backIconId)
        }
        if (middleTextShow) {
            tv_centerText!!.visibility = VISIBLE
            tv_centerText!!.text = middleText
        }
        onViewClicked()
    }

    private fun onViewClicked() {
        iv_return.clickNoRepeat {
            if (mOnBackListener != null) {
                mOnBackListener?.onClick()
            }
            if (mOnCommonFinishListener != null) {
                mOnCommonFinishListener?.invoke()
            }
        }
        tv_title.clickNoRepeat {
            if (mOnCenterListener != null) {
                mOnCenterListener!!.onClick()
            }
            if (mOnCommonFinishListener != null) {
                mOnCommonFinishListener?.invoke()
            }
        }
        tv_delete_text.clickNoRepeat {
            if (mOnRightListener != null) {
                mOnRightListener?.invoke()
            }
        }
        iv_delete_icon.clickNoRepeat {
            if (mOnRightIconListener != null) {
                mOnRightIconListener?.invoke()
            }
        }
        rl_container.clickNoRepeat {
            onRightTextWithIconListener?.invoke()
        }
        tv_right_text_with_icon.clickNoRepeat {
            if (onRightTextWithIconListener != null) {
                onRightTextWithIconListener?.invoke()
            }
        }

    }


    fun setLeftText(title: String?) {
        tv_title!!.text = title
    }

    fun setLeftTextShow(show: Boolean) {
        tv_title!!.visibility = if (show) VISIBLE else GONE
    }

    fun setLeftIconShow(show: Boolean) {
        iv_return!!.visibility = if (show) VISIBLE else GONE
    }

    fun setMiddleTextShow(show: Boolean) {
        tv_centerText!!.visibility = if (show) VISIBLE else GONE
    }

    fun setRightTextShow(show: Boolean) {
        tv_delete_text!!.visibility = if (show) VISIBLE else GONE
    }

    fun setRightTextWithIconShow(show: Boolean) {
        tv_right_text_with_icon!!.visibility =
            if (show) VISIBLE else GONE
    }

    fun setMiddleText(text: String?) {
        tv_centerText!!.text = text
    }

    fun setRightTextColor(colorRes: Int) {
        tv_delete_text!!.setTextColor(mContext!!.resources.getColor(colorRes))
    }

    fun setRightTextColorWithIcon(colorRes: Int) {
        tv_right_text_with_icon!!.setTextColor(mContext!!.resources.getColor(colorRes))
    }

    fun setRightTextWithIcon(title: String?) {
        tv_right_text_with_icon!!.text = title
    }

    fun setRightTextSizeWithIcon(sp: Int) {
        tv_right_text_with_icon!!.textSize = sp.toFloat()
    }

    fun setRightText(title: String?) {
        tv_delete_text!!.text = title
    }

    fun setRightTextCanTouch(canTouch: Boolean) {
        tv_delete_text!!.isClickable = canTouch
    }

    fun setLeftTextSize(sp: Int) {
        tv_title!!.textSize = sp.toFloat()
    }

    fun setRightTextSize(sp: Int) {
        tv_delete_text!!.textSize = sp.toFloat()
    }

    fun setRightIcon(resId: Int) {
        iv_delete_icon!!.setImageResource(resId)
    }

    fun setRightIconShow(show: Boolean) {
        iv_delete_icon!!.visibility = if (show) VISIBLE else GONE
    }

    fun setBackGroundAlpha(alpha: Float) {
        if (fl_bottom_bg!!.alpha == alpha) {
            return
        }
        fl_bottom_bg!!.alpha = alpha
    }

    fun setOnBackClickListener(onClickListener: OnBackListener?) {
        mOnBackListener = onClickListener
    }

    fun setOnCenterClickListener(onClickListener: OnCenterListener?) {
        mOnCenterListener = onClickListener
    }

    fun setOnRightClickListener(onClickListener: () -> Unit?) {
        mOnRightListener = onClickListener
    }

    fun setOnCommonFinishListener(mOnCommonFinishListener: () -> Unit) {
        this.mOnCommonFinishListener = mOnCommonFinishListener
    }

    fun setOnRightIconListener(onRightIconListener: () -> Unit) {
        mOnRightIconListener = onRightIconListener
    }

    fun setReturnShowing(isShow: Boolean) {
        iv_return!!.visibility = if (isShow) VISIBLE else GONE
    }

    fun setRightTextWithIconListener(onRightTextWithIconListener: () -> Unit?) {
        this.onRightTextWithIconListener = onRightTextWithIconListener
    }

    interface OnBackListener {
        fun onClick()
    }

    interface OnCenterListener {
        fun onClick()
    }

    interface OnRightListener {
        fun onClick()
    }

    interface OnCommonFinishListener {
        fun onClick()
    }

    interface OnRightIconListener {
        fun onClick()
    }

    interface OnRightTextWithIconListener {
        fun onClick()
    }
}
