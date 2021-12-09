package com.hhf.project.ui

import android.os.Bundle
import com.aigestudio.wheelpicker.WheelPicker
import com.hhf.project.R
import com.hhf.project.bean.DoctorInfoBean
import com.hhf.project.bean.PollDataBean
import com.hhf.project.databinding.DialogFreagmentWheelViewBinding
import com.hhf.project.ext.getLevelList
import com.hhf.project.ext.getPayClassList
import com.hhf.project.ext.getPolicyHolderList
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class WheelViewDialogFragment :
    BaseDialogFragment<BaseViewModel, DialogFreagmentWheelViewBinding>() {
    private var index: Int = 0
    private var mPosition: Int = 0
    private var mListener: WheelPicker.OnItemSelectedListener? = null
    private var type = TITLE_TYPE

    companion object {
        const val TITLE_TYPE = 1
        const val RACE_TYPE = 2
        const val STATUS_TYPE = 3
        const val MARTIAL_TYPE = 4

        const val PAY_CLASS_TYPE = 5
        const val LEVEL_TYPE = 6
        const val POLICY_HOLDER_TYPE = 7
        const val DOCTOR_SELECT_TYPE = 8
        fun newInstance(
            bean: PollDataBean?,
            type: Int,
            index: Int,
            doctorSelect: DoctorInfoBean? = null
        ): WheelViewDialogFragment {
            val args = Bundle()
            args.putSerializable("bean", bean)
            args.putSerializable("doctorSelect", doctorSelect)
            args.putInt("type", type)
            args.putInt("index", index)
            val fragment = WheelViewDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId() = R.layout.dialog_freagment_wheel_view

    override fun initView(savedInstanceState: Bundle?) {
        val bean = requireArguments().getSerializable("bean") as PollDataBean?
        val doctorInfoBean = requireArguments().getSerializable("doctorSelect") as DoctorInfoBean?
        type = requireArguments().getInt("type")
        index = requireArguments().getInt("index")
        when (type) {
            TITLE_TYPE -> {
                mDatabind.tvTitle.text = "TITLE"
                mDatabind.wheelPicker.data = bean!!.title.list.map {
                    it.name
                }
            }

            RACE_TYPE -> {
                mDatabind.tvTitle.text = "RACE"
                mDatabind.wheelPicker.data = bean!!.race.list.map {
                    it.name
                }
            }
            STATUS_TYPE -> {
                mDatabind.tvTitle.text = "STATE"
                mDatabind.wheelPicker.data = bean!!.state.list.map {
                    it.name
                }
            }
            MARTIAL_TYPE -> {
                mDatabind.tvTitle.text = "MARITAL STATUS"
                mDatabind.wheelPicker.data = bean!!.martial.list.map {
                    it.name
                }
            }
            LEVEL_TYPE -> {
                mDatabind.tvTitle.text = "LEVEL"
                mDatabind.wheelPicker.data = getLevelList().map {
                    it.name
                }
            }
            PAY_CLASS_TYPE -> {
                mDatabind.tvTitle.text = "PAYER CLASS"
                mDatabind.wheelPicker.data = getPayClassList().map {
                    it.name
                }
            }
            POLICY_HOLDER_TYPE -> {
                mDatabind.tvTitle.text = "POLICY HOLDER"
                mDatabind.wheelPicker.data = getPolicyHolderList().map {
                    it.name
                }
            }
            DOCTOR_SELECT_TYPE -> {
                mDatabind.tvTitle.text = "TIME"
                mDatabind.wheelPicker.data =
                    doctorInfoBean!!.canAppointmentTime.map {
                        it.time
                    }
            }
        }
        mDatabind.wheelPicker.setSelectedItemPosition(index, false)
        mDatabind.tvCancel.setOnClickListener { dismissAllowingStateLoss() }
        mDatabind.tvComplete.setOnClickListener {
            mListener?.onItemSelected(mDatabind.wheelPicker, "", mPosition)
            dismissAllowingStateLoss()
        }

        mDatabind.wheelPicker.setOnItemSelectedListener { _, _, position ->
            mPosition = position
        }
    }

    fun setOnItemSelectedListener(listener: WheelPicker.OnItemSelectedListener) = apply {
        mListener = listener
    }
}