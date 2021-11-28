package com.hhf.project.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SPUtils
import com.hhf.project.R
import com.hhf.project.constant.GlobalConstants
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity

/**
 *  @date 2021/11/28
 *  @author admin
 *  @action
 */
class SplashActivity :BaseActivity<BaseViewModel,ViewDataBinding>(){
    override fun layoutId()= R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        //检测时候输入过诊所id
        if(TextUtils.isEmpty(SPUtils.getInstance().getString(GlobalConstants.CLIENT_ID))){
            InputClientIdDialogFragment.newInstance().show(supportFragmentManager,"")
        }else{
            ScreenProActivity.start(this)
            finish()
        }
    }
}