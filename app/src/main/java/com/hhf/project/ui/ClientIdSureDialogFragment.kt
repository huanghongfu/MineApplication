package com.hhf.project.ui

import android.os.Bundle
import androidx.appcompat.widget.DialogTitle
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.R
import com.hhf.project.bean.InputClientBean
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.databinding.DialogFragmentClientIdBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.App
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment

/**
 *  @date 2021/10/25
 *  @author admin
 *  @action
 */
class ClientIdSureDialogFragment :
    BaseDialogFragment<BaseViewModel, DialogFragmentClientIdBinding>() {

    companion object {
        fun newInstance(clientid: String, bean: InputClientBean): ClientIdSureDialogFragment {
            val args = Bundle()
            args.putSerializable("bean", bean)
            args.putString("clientid", clientid)
            val fragment = ClientIdSureDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun layoutId() = R.layout.dialog_fragment_client_id

    override fun initView(savedInstanceState: Bundle?) {
        val bean = (requireArguments().getSerializable("bean") as InputClientBean)
        bean.apply {
            mDatabind.commonViewName.setText(name)
            mDatabind.commonViewAddress.setText(addr)
            mDatabind.commonViewCity.setText(city)
            mDatabind.commonViewNpi.setText(npi)
            mDatabind.commonViewPhone.setText(phone)
        }

        mDatabind.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        mDatabind.tvComplete.setOnClickListener {
            ToastUtils.showShort("Save Success")
            App.stayTime = bean.screenSaverTimer
            SPUtils.getInstance()
                .put(GlobalConstants.CLIENT_ID, requireArguments().getString("clientid"))
            SPUtils.getInstance().put(GlobalConstants.SERVICE_TIME, bean.screenSaverTimer)
            dismissAllowingStateLoss()
        }
    }
}