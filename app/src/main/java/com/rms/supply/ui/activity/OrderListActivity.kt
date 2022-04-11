package com.rms.supply.ui.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.king.zxing.CameraScan
import com.leaf.common.base.BaseViewModel
import com.leaf.oceanshipping.api.base.BaseActivity
import com.rms.supply.R
import com.rms.supply.api.base.SideBarIndexHelper
import com.rms.supply.api.base.TopSmoothScroller
import com.rms.supply.data.bean.OrderBean
import com.rms.supply.data.bean.PageBean
import com.rms.supply.databinding.ActivityOrderListBinding
import com.rms.supply.ui.adapter.OrderAdapterHelper
import com.rms.supply.widget.dialog.OrderInputDialog
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_order_list.*


class OrderListActivity : BaseActivity<BaseViewModel, ActivityOrderListBinding>() {

    private val mDataList = arrayListOf<Any>()

    private val mAdapterHelper = OrderAdapterHelper()

    private val mSideHelper = SideBarIndexHelper()

    private val mCurrentPosition = FloatArray(2)

    private var i = 0

    override fun layoutId(): Int {
        return R.layout.activity_order_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_scan.setOnClickListener {
            RxPermissions(this).request(android.Manifest.permission.CAMERA).subscribe {
                if (it) {
                    //跳转的默认扫码界面
                    startActivityForResult(Intent(this, EasyCaptureActivity::class.java), 100)
                }
            }
        }
        rc_container.linear().setup {
            addType<PageBean>(R.layout.item_page_title)
            addType<OrderBean>(R.layout.item_order)
            onBind {
                mAdapterHelper.convert(this, { bean, swipe ->
                    OrderInputDialog(this@OrderListActivity, { num ->
                        bean.editNum = num
                    }, {
                        swipe.quickClose()
                        rc_container.bindingAdapter.notifyDataSetChanged()
                    }).apply { setData(bean) }.showDialog()
                }, {
                    addCart(it)
                })
            }
        }.models = mDataList
        //侧边栏
        side_bar.setOnSelectIndexItemListener {
            scrollToIndex(it)
        }

        buildData()
        buildSlideArray()
    }

    override fun createObserver() {
    }

    fun scrollToIndex(str: String) {
        if (mSideHelper.headKeyIndexMap.containsKey(str)) {
            val headIndex = mSideHelper.headKeyIndexMap.get(str)
            val s1 = TopSmoothScroller(this)
            s1.targetPosition = headIndex!!
            rc_container.layoutManager?.startSmoothScroll(s1)
        }
    }

    /**
     * 构建侧边栏数据
     */
    private fun buildSlideArray() {
        mSideHelper.headArray.clear()
        mSideHelper.headKeyIndexMap.clear()
        mDataList.forEachIndexed { index, entitiy ->
            if (entitiy is PageBean) {
                mSideHelper.headArray.add(entitiy.pageNum.toString())
                mSideHelper.headKeyIndexMap[entitiy.pageNum.toString()] = index
            }
        }
        side_bar.setIndexArray(mSideHelper.headArray.toTypedArray())
    }


    fun buildData() {
        val list = arrayListOf<Any>()
        for (z in 1..10) {
            val orderList = arrayListOf<OrderBean>()
            list.add(PageBean(z, orderList))
            for (x in 1..20) {
                val orderBean = OrderBean("name:$x", z + x, -1)
                orderList.add(orderBean)
                list.add(orderBean)
            }
        }
        mDataList.clear()
        mDataList.addAll(list)
        rc_container.bindingAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val scanStr = CameraScan.parseScanResult(data);
            Log.d("zgyi", "二维码:" + scanStr.toString())
            if (!scanStr.isNullOrEmpty()) {
                try {
                    val result = scanStr.split("#")
                    val page = result[result.size - 2]
                    scrollToIndex(page)
                    Toast.makeText(this, "识别到：${scanStr}", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     * @param iv
     */
    private fun addCart(iv: ImageView) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        val goods = ImageView(this)
        goods.setImageDrawable(iv.getDrawable())
        val params = RelativeLayout.LayoutParams(100, 100)
        cl_body.addView(goods, params)

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        val parentLocation = IntArray(2)
        cl_body.getLocationInWindow(parentLocation)

        //得到商品图片的坐标（用于计算动画开始的坐标）
        val startLoc = IntArray(2)
        iv.getLocationInWindow(startLoc)

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        val endLoc = IntArray(2)
        iv_card.getLocationInWindow(endLoc)


//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        val startX: Float = startLoc[0] - parentLocation[0] + iv.getWidth() / 2f
        val startY: Float = startLoc[1] - parentLocation[1] + iv.getHeight() / 2f

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        val toX: Float = endLoc[0] - parentLocation[0] + iv_card.getWidth() / 5f
        val toY = (endLoc[1] - parentLocation[1]).toFloat()

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        val path = Path()
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY)
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY)
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        val mPathMeasure = PathMeasure(path, false)

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        val valueAnimator = ValueAnimator.ofFloat(0f, mPathMeasure.getLength())
        valueAnimator.duration = 1000
        // 匀速线性插值器
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation -> // 当插值计算进行时，获取中间的每个值，
            // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
            val value = animation.animatedValue as Float
            // ★★★★★获取当前点坐标封装到mCurrentPosition
            // boolean getPosTan(float distance, float[] pos, float[] tan) ：
            // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
            // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
            mPathMeasure.getPosTan(value, mCurrentPosition, null) //mCurrentPosition此时就是中间距离点的坐标值
            // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
            goods.setTranslationX(mCurrentPosition.get(0))
            goods.setTranslationY(mCurrentPosition.get(1))
        }
        //      五、 开始执行动画
        valueAnimator.start()

//      六、动画结束后的处理
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                super.onAnimationStart(animation, isReverse)
            }

            override fun onAnimationStart(animation: Animator?) {
                TODO("Not yet implemented")
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)

            }

            override fun onAnimationEnd(animation: Animator?) {
                // 购物车的数量加1
                i++
//                count.setText(java.lang.String.valueOf(i))
                // 把移动的图片imageview从父布局里移除
                cl_body.removeView(goods)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }


        })
    }

}