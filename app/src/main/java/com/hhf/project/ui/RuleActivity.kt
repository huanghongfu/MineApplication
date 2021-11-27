package com.hhf.project.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.hhf.project.R
import com.hhf.project.databinding.ActivityRuleBinding
import com.hhf.project.vm.LoginViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity

/**
 *@date 2021/11/27
 *@anchor huanghongfu
 */
class RuleActivity : BaseActivity<LoginViewModel, ActivityRuleBinding>() {
    override fun layoutId()= R.layout.activity_rule

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.getRuleList()
    }
}