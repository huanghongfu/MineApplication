package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.MineApp
import com.hhf.project.R
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.databinding.ActivityLoginBinding
import com.hhf.project.ext.genderArray
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

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }
    override fun layoutId() = R.layout.activity_login

    override fun initView(savedInstanceState: Bundle?) {
//        object: CountDownTimer(3000,1000){
//            override fun onTick(millisUntilFinished: Long) {
//
//            }
//
//            override fun onFinish() {
//                SplashActivity.start(this@LoginActivity)
//                finish()
//            }
//
//        }.start()

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

        mDatabind.commonViewGender.setText(genderArray[0])
        mDatabind.commonViewGender.setExtField("0")
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