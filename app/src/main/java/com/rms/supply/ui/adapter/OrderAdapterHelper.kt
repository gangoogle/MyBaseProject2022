package com.rms.supply.ui.adapter

import android.media.Image
import android.os.strictmode.UntaggedSocketViolation
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.drake.brv.BindingAdapter
import com.rms.supply.R
import com.rms.supply.data.bean.OrderBean
import com.rms.supply.data.bean.PageBean
import com.rms.supply.widget.view.SwipeMenuLayout
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_page_title.view.*

class OrderAdapterHelper {

    fun convert(
        bindingViewHolder: BindingAdapter.BindingViewHolder,
        onClick: (OrderBean, SwipeMenuLayout) -> Unit,
        onItemClick: (ImageView) -> Unit
    ) {
        bindingViewHolder.apply {
            when (itemViewType) {
                R.layout.item_page_title -> {
                    itemView.tv_title.text = getModel<PageBean>().pageNum.toString()
                }
                R.layout.item_order -> {
                    val bean = getModel<OrderBean>()
                    itemView.tv_order_name.text = bean.name
                    itemView.tv_org_num.text = "数量：${bean.orgNum}"
                    if (bean.editNum != -1) {
                        itemView.tv_edit_num.text = "实收数量：${bean.editNum}"
                        itemView.tv_edit_num.visibility = View.VISIBLE
                    } else {
                        itemView.tv_edit_num.visibility = View.GONE
                    }
                    itemView.content.setOnClickListener {
                        onItemClick.invoke(itemView.iv_img)
                    }
                    itemView.swipe.setListener(object : SwipeMenuLayout.SwipeListener {
                        override fun onSwipeClose() {
                            Log.d("yzg", "onSwipeClose")
                        }

                        override fun onSwipeOpen() {
                            Log.d("yzg", "onSwipeOpen")
                            onClick.invoke(bean, itemView.swipe)
                        }
                    })
                }
            }
        }
    }
}