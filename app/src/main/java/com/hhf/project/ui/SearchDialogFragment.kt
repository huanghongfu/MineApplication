package com.hhf.project.ui

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ktx.immersionBar
import com.hhf.project.R
import com.hhf.project.adapter.SearchAdapter
import com.hhf.project.bean.SearchItemBean
import com.hhf.project.databinding.SearchDialogFragmentBinding
import com.hhf.project.vm.SearchViewModel
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.base.BaseDialogFragment
import me.hgj.jetpackmvvm.demo.app.ext.hideSoftKeyboard
import me.hgj.jetpackmvvm.ext.parseState

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class SearchDialogFragment : BaseDialogFragment<SearchViewModel, SearchDialogFragmentBinding>(),
    OnLoadMoreListener {
    private var mSearchContent = ""
    private lateinit var mSearchAdapter: SearchAdapter
    private var mPage: Int = 1
    private var mListener: OnSelectListener? = null

    companion object {
        fun newInstance(): SearchDialogFragment {
            val args = Bundle()

            val fragment = SearchDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.searchNameLiveData.observe(this, Observer { it ->
            parseState(it, {
                if(mPage>1){
                    mSearchAdapter.addData(it.list)
                }else{
                    mSearchAdapter.setNewInstance(it.list)
                }
                if (mPage > it.totalPages) {
                    mSearchAdapter.loadMoreModule.loadMoreEnd(true)
                } else {
                    mSearchAdapter.loadMoreModule.loadMoreComplete()
                }
            })
        })
    }

    override fun layoutId() = R.layout.search_dialog_fragment

    override fun initView(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarDarkFont(true)
            fitsSystemWindows(true)
            statusBarColor(R.color.white)
            keyboardEnable(false)
        }
        initRecyclerView()

        mDatabind.tvSearch.setOnClickListener {
            mPage=1
            mSearchAdapter.selectPosition = -1
            doSearch()
            hideSoftKeyboard(activity)
        }

        mDatabind.etSearchContent.addTextChangedListener(afterTextChanged = {
            it?.apply {
                mSearchContent = toString()
            }
        })

        mDatabind.tvCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        mDatabind.tvComplete.setOnClickListener {
            if (mSearchAdapter.selectPosition >= 0) {
                mListener?.onSelect(mSearchAdapter.getItem(mSearchAdapter.selectPosition))
            }
            dismissAllowingStateLoss()
        }
    }

    private fun initRecyclerView() {
        mSearchAdapter = SearchAdapter()
        mDatabind.recyclerView.adapter = mSearchAdapter
        mSearchAdapter.loadMoreModule.setOnLoadMoreListener(this)

        mSearchAdapter.setOnItemClickListener { _, _, position ->
            mSearchAdapter.selectPosition = position
            mSearchAdapter.notifyDataSetChanged()
        }
    }

    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        val attributes = dialog?.window?.attributes
        attributes?.gravity = Gravity.BOTTOM
        attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes?.height =(ScreenUtils.getAppScreenHeight()*0.7f).toInt()
        attributes?.windowAnimations = R.anim.push_bottom_in
        window?.attributes = attributes
    }

    fun setSelectListener(listener: OnSelectListener) = apply {
        mListener = listener
    }

    private fun doSearch() {
        mViewModel.searchName(mSearchContent, mPage)
    }

    interface OnSelectListener {
        fun onSelect(bean: SearchItemBean)
    }

    override fun onLoadMore() {
        mPage++
        doSearch()
    }
}