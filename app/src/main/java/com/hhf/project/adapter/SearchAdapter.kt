package com.hhf.project.adapter

import android.graphics.Color
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hhf.project.R
import com.hhf.project.bean.SearchItemBean

/**
 *  @date 2021/10/22
 *  @author admin
 *  @action
 */

class SearchAdapter: BaseQuickAdapter<SearchItemBean, BaseViewHolder>(
    R.layout.item_search_result),LoadMoreModule{
    var selectPosition=-1
    override fun convert(holder: BaseViewHolder, item: SearchItemBean) {
        holder.apply {
            item.let {
                setText(R.id.tv_name,it.name)
                setText(R.id.tv_phone,it.phone)
                setText(R.id.tv_address,it.addr)
                if(selectPosition==adapterPosition){
                    setTextColor(R.id.tv_name,context.resources.getColor(R.color.white))
                    setTextColor(R.id.tv_phone,context.resources.getColor(R.color.white))
                    setTextColor(R.id.tv_address,context.resources.getColor(R.color.white))
                    getView<View>(R.id.view_line_1).visibility=View.INVISIBLE
                    getView<View>(R.id.view_line_2).visibility=View.INVISIBLE
                    itemView.setBackgroundColor(Color.parseColor("#5368FC"))
                }else{
                    setTextColor(R.id.tv_name,context.resources.getColor(R.color.black))
                    setTextColor(R.id.tv_phone,context.resources.getColor(R.color.black))
                    setTextColor(R.id.tv_address,context.resources.getColor(R.color.black))
                    getView<View>(R.id.view_line_1).visibility=View.VISIBLE
                    getView<View>(R.id.view_line_2).visibility=View.VISIBLE
                    itemView.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
                }
            }
        }
    }
}