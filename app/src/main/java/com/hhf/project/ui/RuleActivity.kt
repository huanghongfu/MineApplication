package com.hhf.project.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hhf.project.R
import com.hhf.project.bean.Group
import com.hhf.project.databinding.ActivityRuleBinding
import com.hhf.project.vm.LoginViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity
import me.hgj.jetpackmvvm.ext.parseState

/**
 *@date 2021/11/27
 *@anchor huanghongfu
 */
class RuleActivity : BaseActivity<LoginViewModel, ActivityRuleBinding>() {
    private lateinit var mAdapter: BaseQuickAdapter<Group, BaseViewHolder>

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, RuleActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun layoutId() = R.layout.activity_rule

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.getRuleList()

        mDatabind.btnContinue.setOnClickListener {
            LoginActivity.start(this)
            finish()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = object : BaseQuickAdapter<Group, BaseViewHolder>(R.layout.item_rule_layout) {
            override fun convert(holder: BaseViewHolder, item: Group) {

                val tvContent:TextView=holder.getView(R.id.tv_content)
                tvContent.movementMethod=LinkMovementMethod.getInstance()
                val ivStatus:ImageView=holder.getView(R.id.iv_status)

                ivStatus.isSelected=item.isChecked
                val spanUtils = SpanUtils()
                item.detail.forEach {
                    spanUtils.append(it.detailName)
                    if(it.isLink){
                        spanUtils.setForegroundColor(resources.getColor(R.color.colorAccent)).setClickSpan(object:ClickableSpan(){
                            override fun onClick(p0: View) {
                                AlertDialog.Builder(mContext)
                                    .setMessage(it.description)
                                    .setPositiveButton("Sure"
                                    ) { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }

                            override fun updateDrawState(ds: TextPaint) {
                                super.updateDrawState(ds)
                                ds.isUnderlineText=false
                            }
                        })
                    }
                }
                tvContent.text=spanUtils.create()
            }
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            item.isChecked = !item.isChecked
            mAdapter.notifyItemChanged(position)
            mDatabind.btnContinue.isEnabled=mAdapter.data.filter { it.isChecked }
                .toList().size == mAdapter.data.size
        }
        mDatabind.recyclerView.adapter = mAdapter
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.getRuleListLiveData.observe(this, Observer { it ->
            parseState(it, {
                mAdapter.setNewInstance(it.group)
                mDatabind.tvTitle.text = it.agreementName
            })
        })
    }
}