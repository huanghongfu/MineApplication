package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.R
import com.hhf.project.databinding.ActivityAddBuilderBinding
import com.hhf.project.vm.RegisterUserViewModel
import kotlinx.android.synthetic.main.activity_add_builder.*
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class AddBuilderActivity : BaseActivity<RegisterUserViewModel, ActivityAddBuilderBinding>() {

    private lateinit var mEditHolderFragment: EditHolderFragment

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, AddBuilderActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun layoutId()= R.layout.activity_add_builder

    override fun createObserver() {
        super.createObserver()
        mViewModel.updateInfoLiveData.observe(this,{ it ->
            parseState(it,{
                if(it.isSuccess()){
                    if(TextUtils.equals("EXIST_APPINTMENT",it.getErrorCode())){
                        ToastUtils.showShort(it.getResponseMsg())
                    }else if(TextUtils.equals("SELECT_DOCTOR",it.getErrorCode())){
                        DoctorSelectActivity.start(this)
                    }
                }else{
                    ToastUtils.showShort(it.getResponseMsg())
                }
            })
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.ivBack.setOnClickListener { finish() }

        mEditHolderFragment = supportFragmentManager.findFragmentByTag("fragmentTag") as EditHolderFragment

        mDatabind.tvSave.clickNoRepeat {
            val editHolderData = mEditHolderFragment.getEditHolderData()
            mViewModel.updateInfo(editHolderData,mEditHolderFragment.getPayWay())
        }

        mViewModel.getPollDownData()

    }

}