package com.hhf.project.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.KeyboardUtils
import com.hhf.project.R
import com.hhf.project.databinding.DialogFragmentInputClientIdBinding
import com.hhf.project.vm.LoginViewModel
import kotlinx.android.synthetic.main.dialog_fragment_input_client_id.*
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment
import me.hgj.jetpackmvvm.ext.parseState

/**
 *  @date 2021/10/25
 *  @author admin
 *  @action
 */
class InputClientIdDialogFragment :
    BaseDialogFragment<LoginViewModel, DialogFragmentInputClientIdBinding>() {

    companion object {
        fun newInstance(): InputClientIdDialogFragment {
            val args = Bundle()

            val fragment = InputClientIdDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.inputLiveData.observe(this, { it ->
            parseState(it, {
                ClientIdSureDialogFragment.newInstance(
                    mDatabind.etContent.text.toString().trim(),
                    it
                ).show(requireActivity().supportFragmentManager, "")
                dismissAllowingStateLoss()
            })
        })
    }

    override fun layoutId() = R.layout.dialog_fragment_input_client_id

    override fun initView(savedInstanceState: Bundle?) {
        isCancelable = false
        mDatabind.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        mDatabind.tvSure.setOnClickListener {
            mViewModel.inputClient(et_content.text.toString())
        }

        mDatabind.etContent.isFocusable = true
        mDatabind.etContent.requestFocus()
        mDatabind.etContent.postDelayed({
            KeyboardUtils.showSoftInput(mDatabind.etContent)
        }, 200)
    }

    override fun isCenter(): Boolean {
        return true
    }

    override fun isBottom(): Boolean {
        return false
    }
}