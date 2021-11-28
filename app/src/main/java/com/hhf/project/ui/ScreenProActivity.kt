package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.hhf.project.R
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.databinding.ActivityScreenProBinding
import com.hhf.project.vm.LoginViewModel
import me.hgj.jetpackmvvm.demo.app.App
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity

/**
 *@date 2021/11/16
 *@anchor huanghongfu
 */
class ScreenProActivity : BaseActivity<LoginViewModel, ActivityScreenProBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ScreenProActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun layoutId() = R.layout.activity_screen_pro

    override fun initView(savedInstanceState: Bundle?) {
        App.stayTime = SPUtils.getInstance().getInt(GlobalConstants.SERVICE_TIME)
        mDatabind.rlContainer.setOnClickListener {
            RuleActivity.start(this@ScreenProActivity)
            finish()
        }
    }

    override fun initSystemBar() {
        immersionBar {
            fullScreen(true)
        }
    }
}