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


/**
 *  @date 2021/10/23
 *  @author admin
 *  @action
 */
class EditHolderAdapter(
    var fragment: EditHolderFragment,
    var insuranceePay: Boolean,
) : BaseQuickAdapter<EditHolderInfoBean, BaseViewHolder>(
    R.layout.common_edit_builder,
    mutableListOf(EditHolderInfoBean())
) {

    init {
        fragment.mViewModel.uploadLiveData.observe(fragment, Observer {
            if(it.front){

            }
        })
    }

    override fun convert(holder: BaseViewHolder, item: EditHolderInfoBean) {
        holder.setVisible(R.id.common_view_member_id, insuranceePay)
        holder.setVisible(R.id.common_view_policy_holder, insuranceePay)

        apply {
            val statusListBean = getPayClassList()[0]
            initTextView(
                holder.getView(R.id.common_view_payer_class),
                statusListBean.name,
                statusListBean.id
            )
        }

        apply {
            val statusListBean = getLevelList()[0]
            initTextView(
                holder.getView(R.id.common_view_level),
                statusListBean.name,
                statusListBean.id
            )
        }

        apply {
            val statusListBean = getPolicyHolderList()[0]
            initTextView(
                holder.getView(R.id.common_view_policy_holder),
                statusListBean.name,
                statusListBean.id
            )
        }

        holder.getView<View>(R.id.fl_go_builder).setOnClickListener {
            addData(EditHolderInfoBean())
        }

        holder.getView<View>(R.id.common_view_effective_date).setOnClickListener {
            selectDate(context, holder.getView(R.id.common_view_effective_date))
        }

        holder.getView<View>(R.id.common_view_term_date).setOnClickListener {
            selectDate(context, holder.getView(R.id.common_view_term_date))
        }

        holder.getView<View>(R.id.common_view_payer_class).setOnClickListener { view ->
            WheelViewDialogFragment.newInstance(
                null,
                WheelViewDialogFragment.PAY_CLASS_TYPE,
                item.mPayClassIndex
            )
                .setOnItemSelectedListener { _, _, position ->
                    val statusListBean = getPayClassList()[position]
                    item.mPayClassIndex = position
                    (view as CommonTableView).setText(statusListBean.name)
                    (view as CommonTableView).setExtField(statusListBean.id)
                }.show(fragment.childFragmentManager, "")
        }

        holder.getView<View>(R.id.common_view_level).setOnClickListener { view ->
            WheelViewDialogFragment.newInstance(
                null,
                WheelViewDialogFragment.LEVEL_TYPE,
                item.mLevelIndex
            )
                .setOnItemSelectedListener { _, data, position ->
                    val statusListBean = getLevelList()[position]
                    item.mLevelIndex = position
                    (view as CommonTableView).setText(statusListBean.name)
                    view.setExtField(statusListBean.id)
                }.show(fragment.childFragmentManager, "")
        }


        holder.getView<View>(R.id.common_view_policy_holder).setOnClickListener { view ->
            WheelViewDialogFragment.newInstance(
                null,
                WheelViewDialogFragment.POLICY_HOLDER_TYPE,
                item.mPolicyIndex
            )
                .setOnItemSelectedListener { _, data, position ->
                    val statusListBean = getPolicyHolderList()[position]
                    item.mPolicyIndex = position
                    (view as CommonTableView).setText(statusListBean.name)
                    (view as CommonTableView).setExtField(statusListBean.id)
                    if (!TextUtils.equals(statusListBean.id, "1")) {
                        (view as CommonTableView).setRightContentVisible(true)
                        showEditHolderDialog(view)
                        view.ll_right_content.setOnClickListener {
                            showEditHolderDialog(view)
                        }
                    } else {
                        (view as CommonTableView).setRightContentVisible(false)
                    }

                }.show(fragment.childFragmentManager, "")
        }


        holder.getView<View>(R.id.common_view_name).setOnClickListener { view ->
            SearchDialogFragment.newInstance()
                .setSelectListener(object : SearchDialogFragment.OnSelectListener {
                    override fun onSelect(bean: SearchItemBean) {
                        (view as CommonTableView).setText(bean.name)
                        (view as CommonTableView).setExtField(bean.id)
                    }
                })
                .show(fragment.childFragmentManager, "")
        }

        holder.getView<View>(R.id.iv_image_front).setOnClickListener { view ->
            PermissionUtils.permission(PermissionConstants.CAMERA,PermissionConstants.STORAGE)
                .callback(object:PermissionUtils.SimpleCallback{
                    override fun onGranted() {
                        pictureSelector(view,item,true)
                    }

                    override fun onDenied() {
                        ToastUtils.showShort("no permission")
                    }
                })
                .request()

        }

        holder.getView<View>(R.id.iv_image_back).setOnClickListener { view ->
            PermissionUtils.permission(PermissionConstants.CAMERA,PermissionConstants.STORAGE)
                .callback(object:PermissionUtils.SimpleCallback{
                    override fun onGranted() {
                        pictureSelector(view,item,false)
                    }

                    override fun onDenied() {
                        ToastUtils.showShort("no permission")
                    }
                })
                .request()
        }

    }

    private fun pictureSelector(view: View?, item: EditHolderInfoBean,isFront:Boolean) {
        PictureSelector.create(fragment)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(createGlideEngine())
            .minSelectNum(1)
            .selectionMode(PictureConfig.SINGLE)
            .isSingleDirectReturn(true)
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: List<LocalMedia?>) {
                    result[0]?.apply {
                        Glide.with(fragment).load(path).into(view as ImageView)
                        fragment.mViewModel.uploadImage(File(realPath),isFront,item)
                    }
                }

                override fun onCancel() {
                }
            })
    }

    private fun showEditHolderDialog(view: View?) {
        EditHolderDialogFragment.newInstance()
            .setOnCompleteListener(object : EditHolderDialogFragment.OnCompleteEditHolderListener {
                override fun onComplete(bean: CreateHolderBean) {
                    (view as CommonTableView).setRightText(bean.name)
                    (view as CommonTableView).setRightExtField(bean.id)
                }
            })
            .show(fragment.childFragmentManager, "")
    }

    private fun initTextView(view: CommonTableView, name: String, id: String) {
        view.setText(name)
        view.setExtField(id)
    }
}