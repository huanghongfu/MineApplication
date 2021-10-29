package com.hhf.project.vm

import androidx.lifecycle.MutableLiveData
import com.hhf.project.bean.InputClientBean
import com.hhf.project.ext.wrapperMap
import com.hhf.project.network.ApiResponse
import com.hhf.project.network.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.state.ResultState

/**
 *  @date 2021/10/18
 *  @author admin@action
 */
class LoginViewModel: BaseViewModel(){

    val loginLiveData=MutableLiveData<ResultState<ApiResponse<String>>>()
    val inputLiveData=MutableLiveData<ResultState<InputClientBean>>()

    fun login(firstName:String,lastName:String,gender:String,dob:String){
        requestNoCheck({
            val mutableMapOf = mutableMapOf<String, String>()
            mutableMapOf["patientInfo.lastName"] = lastName
            mutableMapOf["patientInfo.firstName"] = firstName
            mutableMapOf["patientInfo.gender"] = gender
            mutableMapOf["patientInfo.strdob"] = dob
            mutableMapOf.wrapperMap()
            apiService.login(mutableMapOf)
        },loginLiveData,isShowDialog = true)
    }

    fun inputClient(clinicId:String){
        request({
            apiService.inputClient(clinicId)
        },inputLiveData)
    }
}