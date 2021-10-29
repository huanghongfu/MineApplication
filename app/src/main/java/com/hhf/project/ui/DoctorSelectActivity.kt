package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aigestudio.wheelpicker.WheelPicker
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hhf.project.R
import com.hhf.project.adapter.DoctorAdapter
import com.hhf.project.databinding.ActivityDoctorSelectBinding
import com.hhf.project.vm.RegisterUserViewModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity
import me.hgj.jetpackmvvm.ext.parseState

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class DoctorSelectActivity : BaseActivity<RegisterUserViewModel, ActivityDoctorSelectBinding>() {
    lateinit var mAdapter: DoctorAdapter

    companion object {

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DoctorSelectActivity::class.java)
            context.startActivity(starter)
        }

    }

    override fun layoutId() = R.layout.activity_doctor_select

    override fun createObserver() {
        super.createObserver()
        mViewModel.getDoctorListLiveData.observe(this, {
            parseState(it, {
                mAdapter.setNewInstance(it)
            })
        })

        mViewModel.createOrderLiveData
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRecyclerView()
        mDatabind.ivBack.setOnClickListener {
            finish()
        }
        mViewModel.getDoctorList()
    }

    private fun initRecyclerView() {
        mAdapter = DoctorAdapter()
        mDatabind.recyclerView.adapter = mAdapter
        mAdapter.addChildClickViewIds(R.id.common_view_time,R.id.tv_select)

        mAdapter.setOnItemChildClickListener { _, view, position ->
            val item = mAdapter.getItem(position)
            when(view.id){
                R.id.common_view_time->{
                    WheelViewDialogFragment.newInstance(null,WheelViewDialogFragment.DOCTOR_SELECT_TYPE,item.subIndex,item)
                        .setOnItemSelectedListener { _, _, position ->
                            item.subIndex=position
                            mAdapter.notifyDataSetChanged()
                        }
                        .show(supportFragmentManager,"")
                }
                R.id.tv_select->{
                    mViewModel.createOrder(item)
                }
            }
        }
    }
}