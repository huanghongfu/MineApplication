package com.hhf.project.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.MineApp
import com.hhf.project.bean.InputClientBean
import com.hhf.project.bean.RuleInitBean
import com.hhf.project.constant.GlobalConstants
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
class LoginViewModel : BaseViewModel() {

    val loginLiveData = MutableLiveData<ResultState<ApiResponse<String>>>()
    val inputLiveData = MutableLiveData<ResultState<InputClientBean>>()
    val getRuleListLiveData = MutableLiveData<ResultState<RuleInitBean>>()

    fun login(firstName: String, lastName: String, gender: String, dob: String) {
        if (TextUtils.isEmpty(firstName)) {
            ToastUtils.showShort("Please enter your firstName")
            return
        }
        if (TextUtils.isEmpty(lastName)) {
            ToastUtils.showShort("Please enter your lastName")
            return
        }

        if (TextUtils.isEmpty(gender)) {
            ToastUtils.showShort("Please enter your gender")
            return
        }

        if (TextUtils.isEmpty(dob)) {
            ToastUtils.showShort("Please enter your dob")
            return
        }
        requestNoCheck({
            val mutableMapOf = mutableMapOf<String, String>()

            mutableMapOf["patientInfo.lastName"] = lastName
            mutableMapOf["patientInfo.firstName"] = firstName
            mutableMapOf["patientInfo.gender"] = gender
            mutableMapOf["patientInfo.strdob"] = dob
            mutableMapOf.wrapperMap()
            apiService.login(mutableMapOf)
        }, loginLiveData, isShowDialog = true)
    }

    fun inputClient(clinicId: String) {
        request({
            apiService.inputClient(clinicId)
        }, inputLiveData)
    }

    fun getRuleList() {
        request({
            apiService.getRuleList( SPUtils.getInstance().getString(GlobalConstants.CLIENT_ID))
        }, getRuleListLiveData)
    }
}