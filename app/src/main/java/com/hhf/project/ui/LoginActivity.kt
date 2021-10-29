package com.hhf.project.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.MineApp
import com.hhf.project.R
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.databinding.ActivityLoginBinding
import com.hhf.project.ext.selectDate
import com.hhf.project.ext.selectGender
import com.hhf.project.vm.LoginViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun layoutId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.commonViewDob.setOnClickListener {
            selectDate(this, mDatabind.commonViewDob)
        }

        mDatabind.commonViewGender.setOnClickListener {
            selectGender(this, mDatabind.commonViewGender)
        }

        mDatabind.ivBack.setOnClickListener {
            finish()
        }

        mDatabind.tvGoLogin.clickNoRepeat {
            mViewModel.login(
                mDatabind.commonViewFirstName.getText(),
                mDatabind.commonViewLastName.getText(),
                mDatabind.commonViewGender.getExtField(),
                mDatabind.commonViewDob.getText()
            )
        }

        mDatabind.tvInputDialog.setOnClickListener {
            InputClientIdDialogFragment.newInstance().show(supportFragmentManager,"")
        }

        if(TextUtils.isEmpty(SPUtils.getInstance().getString(GlobalConstants.CLIENT_ID))){
            InputClientIdDialogFragment.newInstance().show(supportFragmentManager,"")
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.loginLiveData.observe(this, { it ->
            parseState(it, {
                when {
                    TextUtils.equals("PATIENT_EXIXT", it.getErrorCode()) -> {
                        //病人存在
                        MineApp.userid=it.result
                        AddBuilderActivity.start(this)
                        finish()
                    }
                    TextUtils.equals("PATIENT_NOT_EXIXT", it.getErrorCode()) -> {
                        //病人不存在
                        RegisterUserActivity.start(this,
                            mDatabind.commonViewFirstName.getText(),
                            mDatabind.commonViewLastName.getText(),
                            mDatabind.commonViewDob.getText(),
                            mDatabind.commonViewGender.getExtField()
                        )
                    }
                }
                if(!TextUtils.isEmpty(it.msg)){
                    ToastUtils.showShort(it.msg)
                }
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })
    }
}