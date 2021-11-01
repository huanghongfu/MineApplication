package com.hhf.project.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.hhf.project.R
import com.hhf.project.bean.CreateHolderBean
import com.hhf.project.bean.EditHolderInfoBean
import com.hhf.project.bean.PollDataBean
import com.hhf.project.bean.SearchItemBean
import com.hhf.project.databinding.FragmentEditHolderBinding
import com.hhf.project.ext.getLevelList
import com.hhf.project.ext.getPayClassList
import com.hhf.project.ext.getPolicyHolderList
import com.hhf.project.ext.selectDate
import com.hhf.project.vm.RegisterUserViewModel
import com.hhf.project.widght.CommonTableView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import kotlinx.android.synthetic.main.fragment_edit_holder.*
import kotlinx.android.synthetic.main.text_view_layout.view.*
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.event.AppViewModel
import me.hgj.jetpackmvvm.ext.getViewModel
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.util.image.GlideEngine
import java.io.File

/**
 *  @date 2021/10/23
 *  @author admin
 *  @action
 */
class EditHolderFragment : BaseFragment<RegisterUserViewModel, FragmentEditHolderBinding>() {

    var mPollDataBean: PollDataBean? = null

    override fun layoutId() = R.layout.fragment_edit_holder

    lateinit var mData:MutableList<EditHolderInfoBean>
    override fun createObserver() {
        super.createObserver()
        mViewModel.getPollDownLiveData.observe(this, {
            parseState(it, {
                mPollDataBean = it
            })
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        mData= mutableListOf()
        mDatabind.rgGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rb_insurancee_pay) {
                mDatabind.llAddBuilderContainer.isVisible = true
            } else if (i == R.id.rb_self_pay) {
                mDatabind.llAddBuilderContainer.isVisible = false
            }
        }

        addBuilder()
    }

    private fun addBuilder() {
        val item=EditHolderInfoBean()
        mData.add(item)
        val inflate = LayoutInflater.from(context).inflate(R.layout.common_edit_builder,null)
        mDatabind.llAddBuilderContainer.addView(inflate)
        apply {
            val statusListBean = getPayClassList()[0]
            initTextView(
                inflate.findViewById(R.id.common_view_payer_class),
                statusListBean.name,
                statusListBean.id
            )
        }

        apply {
            val statusListBean = getLevelList()[0]
            initTextView(
                inflate.findViewById(R.id.common_view_level),
                statusListBean.name,
                statusListBean.id
            )
        }

        apply {
            val statusListBean = getPolicyHolderList()[0]
            initTextView(
                inflate.findViewById(R.id.common_view_policy_holder),
                statusListBean.name,
                statusListBean.id
            )
        }

        inflate.findViewById<View>(R.id.fl_go_builder).setOnClickListener {
            addBuilder()
        }

        inflate.findViewById<View>(R.id.common_view_effective_date).setOnClickListener {
            selectDate(requireContext(), inflate.findViewById(R.id.common_view_effective_date))
        }

        inflate.findViewById<View>(R.id.common_view_term_date).setOnClickListener {
            selectDate(requireContext(), inflate.findViewById(R.id.common_view_term_date))
        }

        inflate.findViewById<View>(R.id.common_view_payer_class).setOnClickListener { view ->
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
                }.show(childFragmentManager, "")
        }

        inflate.findViewById<View>(R.id.common_view_level).setOnClickListener { view ->
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
                }.show(childFragmentManager, "")
        }


        inflate.findViewById<View>(R.id.common_view_policy_holder).setOnClickListener { view ->
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

                }.show(childFragmentManager, "")
        }


        inflate.findViewById<View>(R.id.common_view_name).setOnClickListener { view ->
            SearchDialogFragment.newInstance()
                .setSelectListener(object : SearchDialogFragment.OnSelectListener {
                    override fun onSelect(bean: SearchItemBean) {
                        (view as CommonTableView).setText(bean.name)
                        (view as CommonTableView).setExtField(bean.id)
                    }
                })
                .show(childFragmentManager, "")
        }

        inflate.findViewById<View>(R.id.iv_image_front).setOnClickListener { view ->
            PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        pictureSelector(view, item, true)
                    }

                    override fun onDenied() {
                        ToastUtils.showShort("no permission")
                    }
                })
                .request()

        }

        inflate.findViewById<View>(R.id.iv_image_back).setOnClickListener { view ->
            PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        pictureSelector(view, item, false)
                    }

                    override fun onDenied() {
                        ToastUtils.showShort("no permission")
                    }
                })
                .request()
        }

    }

    private fun pictureSelector(view: View?, item: EditHolderInfoBean, isFront: Boolean) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .minSelectNum(1)
            .selectionMode(PictureConfig.SINGLE)
            .isSingleDirectReturn(true)
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: List<LocalMedia?>) {
                    result[0]?.apply {
                        Glide.with(this@EditHolderFragment).load(path).into(view as ImageView)
                        mViewModel.uploadImage(File(realPath), isFront, item)
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
            .show(childFragmentManager, "")
    }

    private fun initTextView(view: CommonTableView, name: String, id: String) {
        view.setText(name)
        view.setExtField(id)
    }

    fun getEditHolderData(): List<Map<String, String>> {
        val mutableListOf = mutableListOf<Map<String, String>>()
        (0 until mDatabind.llAddBuilderContainer.childCount).forEachIndexed { index, _ ->
            val childAt = mDatabind.llAddBuilderContainer.getChildAt(index)
            val mutableMapOf = mutableMapOf<String, String>()
            mutableMapOf[String(StringBuffer("insureIds"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_name).getExtField()
            mutableMapOf[String(StringBuffer("payerClasses"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_payer_class).getExtField()
            mutableMapOf[String(StringBuffer("insureGrade"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_level).getExtField()

            mutableMapOf[String(StringBuffer("accounts"))] = if (rb_self_pay.isChecked) {
                ""
            } else {
                childAt.findViewById<CommonTableView>(R.id.common_view_member_id)
                    .getText()
            }

            mutableMapOf[String(StringBuffer("policy_holders"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_policy_holder).getExtField()

            mutableMapOf[String(StringBuffer("policyHolderId"))] = if (rb_self_pay.isChecked) {
                ""
            } else {
                childAt.findViewById<CommonTableView>(R.id.common_view_policy_holder)
                    .getRightExtField()
            }

            mutableMapOf[String(StringBuffer("pis"))] = ""
            mutableMapOf[String(StringBuffer("accessoryPath"))] = mutableListOf(
                mData[index].frontPath,
                mData[index].backPath
            )
                .filter {
                    !TextUtils.isEmpty(it)
                }.joinToString("@*")


            mutableMapOf[String(StringBuffer("groupNumbers"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_group).getText()


            mutableMapOf[String(StringBuffer("effective_dates"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_effective_date).getText()
            mutableMapOf[String(StringBuffer("term_dates"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_term_date).getText()

            mutableListOf.add(mutableMapOf)
        }
        return mutableListOf
    }

    fun getPayWay() = mDatabind.rbInsuranceePay.isChecked
}