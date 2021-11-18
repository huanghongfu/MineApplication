package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.blankj.utilcode.util.SPUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hhf.project.R
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.databinding.ActivitySplashBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.App
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity

/**
 *@date 2021/11/16
 *@anchor huanghongfu
 */
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SplashActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun layoutId() = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
//        object : CountDownTimer(2000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//
//            }
//
//            override fun onFinish() {
//
//            }
//
//        }.start()
        App.stayTime = SPUtils.getInstance().getInt(GlobalConstants.SERVICE_TIME)
//        App.stayTime = 10
        mDatabind.rlContainer.setOnClickListener {
            LoginActivity.start(this@SplashActivity)
            finish()
        }
    }

    override fun initSystemBar() {
        immersionBar {
            fullScreen(true)
        }
    }
}