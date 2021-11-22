package com.hhf.project.ui

import android.os.Bundle
import android.widget.DatePicker
import androidx.databinding.ViewDataBinding
import com.hhf.project.R
import com.hhf.project.databinding.DialogFragmentDatePickerBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment
import java.util.*

/**
 *@date 2021/11/22
 *@anchor huanghongfu
 */
class DatePickerDialogFragment : BaseDialogFragment<BaseViewModel, DialogFragmentDatePickerBinding>() {

    companion object{
        fun newInstance():DatePickerDialogFragment {
            val args = Bundle()

            val fragment = DatePickerDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun layoutId()= R.layout.dialog_fragment_date_picker

    override fun initView(savedInstanceState: Bundle?) {
        val instance = Calendar.getInstance()
        mDatabind.datePicker.init(instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH),object:DatePicker.OnDateChangedListener{
            override fun onDateChanged(
                view: DatePicker?,
                year: Int,
                monthOfYear: Int,
                dayOfMonth: Int
            ) {
            }
        })

        mDatabind.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        mDatabind.tvComplete.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}