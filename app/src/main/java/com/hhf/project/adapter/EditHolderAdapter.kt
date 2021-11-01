package com.hhf.project.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hhf.project.R
import com.hhf.project.bean.CreateHolderBean
import com.hhf.project.bean.EditHolderInfoBean
import com.hhf.project.bean.SearchItemBean
import com.hhf.project.ext.getLevelList
import com.hhf.project.ext.getPayClassList
import com.hhf.project.ext.getPolicyHolderList
import com.hhf.project.ext.selectDate
import com.hhf.project.ui.EditHolderDialogFragment
import com.hhf.project.ui.EditHolderFragment
import com.hhf.project.ui.SearchDialogFragment
import com.hhf.project.ui.WheelViewDialogFragment
import com.hhf.project.widght.CommonTableView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import kotlinx.android.synthetic.main.text_view_layout.view.*
import me.hgj.jetpackmvvm.util.image.GlideEngine.Companion.createGlideEngine
import java.io.File


///**
// *  @date 2021/10/23
// *  @author admin
// *  @action
// */
//class EditHolderAdapter(
//    var fragment: EditHolderFragment,
//    var insuranceePay: Boolean,
//) : BaseQuickAdapter<EditHolderInfoBean, BaseViewHolder>(
//    R.layout.common_edit_builder,
//    mutableListOf(EditHolderInfoBean())
//) {
//
//    override fun convert(holder: BaseViewHolder, item: EditHolderInfoBean) {
//        holder.setIsRecyclable(false)
//
//}