package com.hhf.project.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.DeviceUtils
import com.hhf.project.bean.CreateHolderBean
import com.hhf.project.bean.SearchInfoBean
import com.hhf.project.ext.wrapperMap
import com.hhf.project.network.ApiResponse
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import com.hhf.project.network.apiService
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.state.ResultState

/**
 *  @date 2021/10/18
 *  @author admin@action
 */
class SearchViewModel : BaseViewModel() {

    val searchNameLiveData = MutableLiveData<ResultState<SearchInfoBean>>()

    fun searchName(name: String, page: Int = 1, row: Int = 10) {
        request({
            val mutableMapOf = mutableMapOf<String, String>()
            mutableMapOf["insureCompany.name"] = name
            mutableMapOf["page"] = page.toString()
            mutableMapOf["row"] = row.toString()
            mutableMapOf.wrapperMap()
            apiService.searchName(mutableMapOf)
        }, searchNameLiveData)
    }



}