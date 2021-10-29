package com.hhf.project.ui

import android.os.Bundle
import android.text.TextUtils
import com.aigestudio.wheelpicker.WheelPicker
import com.hhf.project.R
import com.hhf.project.bean.CreateHolderBean
import com.hhf.project.bean.PollDataBean
import com.hhf.project.databinding.EditHolderDialogFragmentBinding
import com.hhf.project.ext.selectDate
import com.hhf.project.ext.selectGender
import com.hhf.project.vm.RegisterUserViewModel
import com.hhf.project.widght.CommonTableView
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment
import me.hgj.jetpackmvvm.ext.parseState

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class EditHolderDialogFragment :
    BaseDialogFragment<RegisterUserViewModel, EditHolderDialogFragmentBinding>() {
    companion object {
        fun newInstance(): EditHolderDialogFragment {
            val args = Bundle()

            val fragment = EditHolderDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var mPollDataBean: PollDataBean? = null

    var mStatusIndex = 0

    private var mListener: OnCompleteEditHolderListener? = null

    override fun layoutId() = R.layout.edit_holder_dialog_fragment

    override fun createObserver() {
        super.createObserver()

        mViewModel.createHolderLiveData.observe(this, {
            parseState(it, {
                dismissAllowingStateLoss()
                mListener?.onComplete(it)
            })
        })

        mViewModel.getPollDownLiveData.observe(this, { it ->
            parseState(it, {
                mPollDataBean = it
                it.state.apply {
                    if (TextUtils.isEmpty(defautValue)) {
                        mStatusIndex = 0
                        initTextView(
                            mDatabind.commonViewStatus,
                            list[mStatusIndex].name,
                            list[mStatusIndex].id
                        )
                    } else {
                        list.mapIndexed { index, statusListBean ->
                            if (TextUtils.equals(statusListBean.id, defautValue)) {
                                mStatusIndex = index
                                initTextView(
                                    mDatabind.commonViewStatus,
                                    list[mStatusIndex].name,
                                    list[mStatusIndex].id
                                )
                            }
                        }
                    }
                }
            })
        })

    }

    private fun initTextView(view: CommonTableView, name: String, id: String) {
        view.setText(name)
        view.setExtField(id)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvCancel.setOnClickListener { dismissAllowingStateLoss() }
        mDatabind.tvComplete.setOnClickListener {
            mViewModel.createHolder(
                lastName = mDatabind.commonViewLastName.getText(),
                firstName = mDatabind.commonViewFirstName.getText(),
                ssn = mDatabind.commonViewSsn.getText(),
                gender = mDatabind.commonViewGender.getExtField(),
                birth = mDatabind.commonViewDob.getText(),
                phone = mDatabind.commonViewPhone.getText(),
                address = mDatabind.commonViewAddress.getText(),
                city = mDatabind.commonViewCity.getText(),
                state = mDatabind.commonViewStatus.getExtField(),
                zip = mDatabind.commonViewZip.getText(),
            )
        }
        mDatabind.commonViewStatus.setOnClickListener {
            mPollDataBean?.let {
                WheelViewDialogFragment.newInstance(
                    it,
                    WheelViewDialogFragment.STATUS_TYPE,
                    mStatusIndex
                )
                    .setOnItemSelectedListener(WheelPicker.OnItemSelectedListener { _, _, position ->
                        mStatusIndex = position
                        mDatabind.commonViewStatus.setText(it.state.list[position].name)
                        mDatabind.commonViewStatus.setExtField(it.state.list[position].id)
                    }).show(childFragmentManager, "")
            }
        }

        mDatabind.commonViewDob.setOnClickListener {
            selectDate(requireContext(), mDatabind.commonViewDob)
        }

        mDatabind.commonViewGender.setOnClickListener {
            selectGender(requireContext(), it as CommonTableView)
        }

        mViewModel.getPollDownData()
    }

    fun setOnCompleteListener(listener: OnCompleteEditHolderListener) = apply {
        mListener = listener
    }


    interface OnCompleteEditHolderListener {
        fun onComplete(bean: CreateHolderBean)
    }
}