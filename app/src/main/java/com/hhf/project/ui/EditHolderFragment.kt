package com.hhf.project.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.StringUtils
import com.hhf.project.R
import com.hhf.project.adapter.EditHolderAdapter
import com.hhf.project.bean.PollDataBean
import com.hhf.project.databinding.FragmentEditHolderBinding
import com.hhf.project.vm.RegisterUserViewModel
import com.hhf.project.widght.CommonTableView
import kotlinx.android.synthetic.main.fragment_edit_holder.*
import me.hgj.jetpackmvvm.demo.app.base.BaseFragment
import me.hgj.jetpackmvvm.demo.app.event.AppViewModel
import me.hgj.jetpackmvvm.ext.getViewModel
import me.hgj.jetpackmvvm.ext.parseState

/**
 *  @date 2021/10/23
 *  @author admin
 *  @action
 */
class EditHolderFragment : BaseFragment<RegisterUserViewModel, FragmentEditHolderBinding>() {

    lateinit var mAdapter: EditHolderAdapter
    var mPollDataBean: PollDataBean? = null

    override fun layoutId() = R.layout.fragment_edit_holder

    override fun createObserver() {
        super.createObserver()
        mViewModel.getPollDownLiveData.observe(this, {
            parseState(it, {
                mPollDataBean = it
            })
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        initRecyclerView()
        mDatabind.rgGroup.setOnCheckedChangeListener { _, i ->
            if (i == R.id.rb_insurancee_pay) {
                mAdapter.insuranceePay = true
                mAdapter.notifyDataSetChanged()
                mDatabind.recyclerView.isVisible=true
            } else if (i == R.id.rb_self_pay) {
//                mAdapter.insuranceePay = false
                mDatabind.recyclerView.isVisible=false
            }
        }

    }

    private fun initRecyclerView() {
        mAdapter = EditHolderAdapter(this, mDatabind.rbInsuranceePay.isChecked)
        mDatabind.recyclerView.adapter = mAdapter
    }

    fun getEditHolderData(): List<Map<String, String>> {
        val mutableListOf = mutableListOf<Map<String, String>>()
        mAdapter.data.forEachIndexed { index, _ ->
            val childAt = mDatabind.recyclerView.getChildAt(index)
            val mutableMapOf = mutableMapOf<String, String>()
            mutableMapOf[String(StringBuffer("insureIds"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_name).getExtField()
            mutableMapOf[String(StringBuffer("payerClasses"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_payer_class).getExtField()
            mutableMapOf[String(StringBuffer("insureGrade"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_level).getExtField()

            mutableMapOf[String(StringBuffer("accounts"))] = if (rb_self_pay.isChecked) {
                ""
            } else {
                childAt.findViewById<CommonTableView>(R.id.common_view_member_id)
                    .getText()
            }

            mutableMapOf[String(StringBuffer("policy_holders"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_policy_holder).getExtField()

            mutableMapOf[String(StringBuffer("policyHolderId"))] = if (rb_self_pay.isChecked) {
                ""
            } else {
                childAt.findViewById<CommonTableView>(R.id.common_view_policy_holder)
                    .getRightExtField()
            }

            mutableMapOf[String(StringBuffer("pis"))] = ""
            mutableMapOf[String(StringBuffer("accessoryPath"))] = mutableListOf(
                mAdapter.getItem(index).frontPath,
                mAdapter.getItem(index).backPath
            )
                .filter {
                    !TextUtils.isEmpty(it)
                }.joinToString("@*")


            mutableMapOf[String(StringBuffer("groupNumbers"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_group).getText()


            mutableMapOf[String(StringBuffer("effective_dates"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_effective_date).getText()
            mutableMapOf[String(StringBuffer("term_dates"))] =
                childAt.findViewById<CommonTableView>(R.id.common_view_term_date).getText()

            mutableListOf.add(mutableMapOf)
        }
        return mutableListOf
    }

    fun getPayWay() = mDatabind.rbInsuranceePay.isChecked
}