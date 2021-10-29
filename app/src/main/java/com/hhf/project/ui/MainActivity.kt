package com.hhf.project.ui

import android.os.Bundle
import com.hhf.project.R
import com.hhf.project.databinding.ActivityMainBinding
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity

class MainActivity : BaseActivity<BaseViewModel,ActivityMainBinding>() {

    override fun layoutId()=R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }
}