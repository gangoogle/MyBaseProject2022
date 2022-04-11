package com.leaf.common.view

import android.content.Context
import android.widget.LinearLayout
import android.text.TextWatcher
import android.widget.EditText
import android.view.LayoutInflater
import android.widget.TextView

import android.text.method.DigitsKeyListener
import android.text.Editable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.leaf.common.R
import com.leaf.common.ext.view.px2sp
import java.lang.NumberFormatException

/**
 * Author        Hule  hu.le@cesgroup.com.cn
 * Date          2016/12/22 15:52
 * Description:  TODO: 自定义商城点击+或-实现数字框的数字增加或减少
 */
class NumberPickerView : LinearLayout, View.OnClickListener, TextWatcher {
    //当前输入框可输入的值（默认为不限制）
    var maxValue = Int.MAX_VALUE
        private set

    /**
     * @return 获取当前的库存
     */
    //当前的库存量（默认为不限制）
    var currentInvventory = Int.MAX_VALUE
        private set

    //默认字体的大小
    private val textDefaultSize = 14

    // 中间输入框的‘输入值
    private var mNumText: EditText? = null

    //默认输入框最小值
    var minDefaultNum = 0
    var canEdit = false

    var onNumberChangeListener: ((Int) -> Unit)? = null

    // 监听事件(负责警戒值回调)
    private var onClickInputListener: OnClickInputListener? = null

    // 监听输入框内容变化
    private var onInputNumberListener: OnInputNumberListener? = null

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initNumberPickerView(context, attrs)
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private fun initNumberPickerView(context: Context, attrs: AttributeSet?) {
        //加载定义好的布局文件
        LayoutInflater.from(context).inflate(R.layout.number_button, this)
        val mRoot = findViewById<View>(R.id.root) as LinearLayout
        val subText = findViewById<View>(R.id.button_sub) as TextView
        val addText = findViewById<View>(R.id.button_add) as TextView
        mNumText = findViewById<View>(R.id.middle_count) as EditText

        //添加监听事件
        addText.setOnClickListener(this)
        subText.setOnClickListener(this)
        mNumText!!.setOnClickListener(this)
        mNumText!!.addTextChangedListener(this)

        //获取自定义属性的相关内容
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton)
        // 背景
        val resourceId = typedArray.getResourceId(R.styleable.NumberButton_backgroud, R.drawable.bg_number_button)
        val addResourceId = typedArray.getResourceId(R.styleable.NumberButton_addBackground, R.drawable.bg_button_right)
        val subResourceId = typedArray.getResourceId(R.styleable.NumberButton_subBackground, R.drawable.bg_button_left)
        // 水平分割线
        val dividerDrawable = typedArray.getDrawable(R.styleable.NumberButton_individer)
        //中间的编辑框是否可编辑
        canEdit = typedArray.getBoolean(R.styleable.NumberButton_editable, true)
        //+和-文本的宽度 geDiemension返回float getDimensionPixelSize四舍五入+  getDimensionPixeloffset四舍五入-
        val buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1)
        //+和-文本的颜色
        val textColor = typedArray.getColor(R.styleable.NumberButton_textColor, -0x1000000)
        //+和-文本的字体大小
        val textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1)
        // 中间显示数量的按钮宽度
        val editextWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_editextWidth, -1)
        //必须调用这个，因为自定义View会随着Activity创建频繁的创建array
        typedArray.recycle()

        //设置输入框是否可用
        setEditable(canEdit)
        //初始化控件颜色
        mRoot.setBackgroundResource(resourceId)
        mRoot.dividerDrawable = dividerDrawable
        subText.setBackgroundResource(subResourceId)
        addText.setBackgroundResource(addResourceId)
        addText.setTextColor(textColor)
        subText.setTextColor(textColor)
        mNumText!!.setTextColor(textColor)

        //初始化字体,注意默认的是px单位，要转换
        if (textSize > 0) {
            mNumText!!.textSize = px2sp(textSize.toFloat()).toFloat()
        } else {
            mNumText!!.textSize = textDefaultSize.toFloat()
        }

        //设置文本框的宽高
        if (buttonWidth > 0) {
            val layoutParams = LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT)
            addText.layoutParams = layoutParams
            subText.layoutParams = layoutParams
        } else {
        }
        //设置输入框的宽高
        if (editextWidth > 0) {
            val layoutParams = LayoutParams(editextWidth, LayoutParams.MATCH_PARENT)
            mNumText!!.layoutParams = layoutParams
        } else {
        }
    }

    /**
     * @param editable 设置输入框是否可编辑
     */
    fun setEditable(editable: Boolean) {
        canEdit = editable
        if (editable) {
            mNumText!!.isFocusable = true
            mNumText!!.keyListener = DigitsKeyListener()
        } else {
            mNumText!!.isFocusable = false
            mNumText!!.keyListener = null
        }
    }

    /**
     * @return 获取输入框的最终数字值
     */
    val numText: Int
        get() = try {
            val textNum = mNumText!!.text.toString().trim { it <= ' ' }
            textNum.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            mNumText!!.setText(minDefaultNum.toString())
            minDefaultNum
        }

    /**
     * 设置当前的最大值，即库存的上限
     */
    fun setCurrentInventory(maxInventory: Int): NumberPickerView {
        currentInvventory = maxInventory
        return this
    }

    /**
     * 设置默认的最小值
     *
     * @param minDefaultNum
     * @return
     */
    fun setMinDefaultNum(minDefaultNum: Int): NumberPickerView {
        this.minDefaultNum = minDefaultNum
        return this
    }

    /**
     * 最大限制量
     *
     * @param maxValue
     * @return
     */
    fun setMaxValue(maxValue: Int): NumberPickerView {
        this.maxValue = maxValue
        return this
    }

    var setNumFlag = false

    /**
     * @param currentNum 设置当前输入框的值
     * @return NumPickerView
     */
    fun setCurrentNum(currentNum: Int): NumberPickerView {
        setNumFlag = true
        if (currentNum > minDefaultNum) {
            if (currentNum <= currentInvventory) {
                mNumText!!.setText(currentNum.toString())
            } else if (currentNum > maxValue) {
                mNumText!!.setText(maxValue.toString())
            } else {
                mNumText!!.setText(currentInvventory.toString())
            }
        } else {
            mNumText!!.setText(minDefaultNum.toString())
        }
        return this
    }

    fun setmOnClickInputListener(mOnWarnListener: OnClickInputListener?): NumberPickerView {
        onClickInputListener = mOnWarnListener
        return this
    }

    fun setOnInputNumberListener(onInputNumberListener: OnInputNumberListener?): NumberPickerView {
        this.onInputNumberListener = onInputNumberListener
        return this
    }


    override fun onClick(view: View) {
        val widgetId = view.id
        val numText = numText
        if (widgetId == R.id.button_sub) {
            if (!canEdit) {
                return
            }
            if (numText > minDefaultNum + 1) {
                mNumText!!.setText((numText - 1).toString())
            } else {
                mNumText!!.setText(minDefaultNum.toString())
                //小于警戒值
                warningForMinInput()
                Log.d("NumberPicker", "减少已经到达极限")
            }
        } else if (widgetId == R.id.button_add) {
            if (!canEdit) {
                return
            }
            if (numText < Math.min(maxValue, currentInvventory)) {
                mNumText!!.setText((numText + 1).toString())
            } else if (currentInvventory < maxValue) {
                mNumText!!.setText(currentInvventory.toString())
                //库存不足
                warningForInventory()
                Log.d("NumberPicker", "增加已经到达极限")
            } else {
                mNumText!!.setText(maxValue.toString())
                // 超过限制数量
                warningForMaxInput()
                Log.d("NumberPicker", "达到已经限制的输入数量")
            }
        }
        mNumText!!.setSelection(mNumText!!.text.toString().length)
    }

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        if (onInputNumberListener != null) {
            onInputNumberListener!!.beforeTextChanged(charSequence, start, count, after)
        }
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        if (onInputNumberListener != null) {
            onInputNumberListener!!.onTextChanged(charSequence, start, before, count)
        }
    }

    override fun afterTextChanged(editable: Editable) {
        if (onInputNumberListener != null) {
            onInputNumberListener!!.afterTextChanged(editable)
        }
        try {
            mNumText!!.removeTextChangedListener(this)
            val inputText = editable.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(inputText)) {
                val inputNum = inputText.toInt()
                if (inputNum < minDefaultNum) {
                    mNumText!!.setText(minDefaultNum.toString())
                    // 小于警戒值
                    warningForMinInput()
                } else if (inputNum <= Math.min(maxValue, currentInvventory)) {
                    mNumText!!.setText(inputText)
                } else if (inputNum >= maxValue) {
                    mNumText!!.setText(maxValue.toString())
                    //超过限量
                    warningForMaxInput()
                } else {
                    mNumText!!.setText(currentInvventory.toString())
                    //库存不足
                    warningForInventory()
                }
            }
            mNumText!!.addTextChangedListener(this)
            mNumText!!.setSelection(mNumText!!.text.toString().length)
            if (!setNumFlag) {
                onNumberChangeListener?.invoke(numText)
            } else {
                setNumFlag = false
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private fun warningForInventory() {
        if (onClickInputListener != null) {
            onClickInputListener!!.onWarningForInventory(currentInvventory)
        }
    }

    /**
     * 小于最小值回调
     * Warning for inventory.
     */
    private fun warningForMinInput() {
        if (onClickInputListener != null) {
            onClickInputListener!!.onWarningMinInput(minDefaultNum)
        }
    }

    /**
     * 查过最大值值回调
     * Warning for inventory.
     */
    private fun warningForMaxInput() {
        if (onClickInputListener != null) {
            onClickInputListener!!.onWarningMaxInput(maxValue)
        }
    }

    /**
     * 超过警戒值回调
     */
    interface OnClickInputListener {
        fun onWarningForInventory(inventory: Int)
        fun onWarningMinInput(minValue: Int)
        fun onWarningMaxInput(maxValue: Int)
    }

    /**
     * 输入框数字内容监听
     */
    interface OnInputNumberListener {
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
        fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int)
        fun afterTextChanged(editable: Editable?)
    }


}