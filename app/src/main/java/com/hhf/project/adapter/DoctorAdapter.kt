package com.hhf.project.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hhf.project.R
import com.hhf.project.bean.DoctorInfoBean
import com.hhf.project.ui.WheelViewDialogFragment
import com.hhf.project.widght.CommonTableView

/**
 *  @date 2021/10/23
 *  @author admin
 *  @action
 */
class DoctorAdapter: BaseQuickAdapter<DoctorInfoBean, BaseViewHolder>(
    R.layout.item_doctor_selector){
    override fun convert(
        holder: BaseViewHolder,
        item: DoctorInfoBean
    ) {
        holder.setText(R.id.tv_name,item.lastName.plus(" ").plus(item.firstName))
        holder.getView<CommonTableView>(R.id.common_view_time).setText(item.canAppointmentTime[item.subIndex].time)
    }
}