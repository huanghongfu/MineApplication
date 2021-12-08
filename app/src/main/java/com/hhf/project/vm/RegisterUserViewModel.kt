package com.hhf.project.vm

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ToastUtils
import com.hhf.project.MineApp
import com.hhf.project.bean.*
import com.hhf.project.ext.wrapperMap
import com.hhf.project.network.ApiResponse
import com.hhf.project.network.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.ext.requestNoCheck
import me.hgj.jetpackmvvm.state.ResultState
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 *  @date 2021/10/17
 *  @author admin
 *  @a
 */
class RegisterUserViewModel : BaseViewModel() {

    val registerUserLiveData = MutableLiveData<ResultState<String>>()
    val getPollDownLiveData = MutableLiveData<ResultState<PollDataBean>>()
    val createHolderLiveData = MutableLiveData<ResultState<CreateHolderBean>>()
    val uploadLiveData = MutableLiveData<UploadInfoBean>()
    val updateInfoLiveData = MutableLiveData<ResultState<ApiResponse<String>>>()
    val getDoctorListLiveData = MutableLiveData<ResultState<ApiResponse<MutableList<DoctorInfoBean>>>>()
    val createOrderLiveData = MutableLiveData<ResultState<Any>>()

    fun registerUser(
        lastName: String,
        firstName: String,
        middleName: String,
        gender: String,
        strdob: String,
        email: String,
        title: String,
        martial: String,
        race: String,
        ssn: String,
        payWay: Boolean,
        employer: String,
        g_addr: String,
        g_city: String,
        g_state: String,
        g_zipCode: String,
        g_home_phone: String,
        content: List<Map<String, String>>
    ) {
        if (TextUtils.isEmpty(lastName)) {
            ToastUtils.showShort("Please enter your lastName")
            return
        }
        if (TextUtils.isEmpty(firstName)) {
            ToastUtils.showShort("Please enter your firstName")
            return
        }
        if (TextUtils.isEmpty(gender)) {
            ToastUtils.showShort("Please enter your gender")
            return
        }
        if (TextUtils.isEmpty(g_addr)) {
            ToastUtils.showShort("Please enter your address")
            return
        }
        if (TextUtils.isEmpty(g_city)) {
            ToastUtils.showShort("Please enter your city")
            return
        }
        if (TextUtils.isEmpty(g_zipCode)) {
            ToastUtils.showShort("Please enter your zip")
            return
        }
        val mutableMapOf = IdentityHashMap<String, String>()
        mutableMapOf["patientInfo.lastName"] = lastName
        mutableMapOf["patientInfo.firstName"] = firstName
        mutableMapOf["patientInfo.mi"] = middleName
        mutableMapOf["patientInfo.gender"] = gender
        mutableMapOf["patientInfo.ssn"] = ssn
        mutableMapOf["patientInfo.title.id"] = title
        mutableMapOf["patientInfo.martial.id"] = martial
        mutableMapOf["patientInfo.email"] = email
        mutableMapOf["patientInfo.race.id"] = race
        mutableMapOf["patientInfo.strdob"] = strdob
        mutableMapOf["patientInfo.payWay"] = if (payWay) {
            "INSURANCE"
        } else {
            "SELF"
        }


        mutableMapOf["patientInfo.g_addr"] = g_addr
        mutableMapOf["patientInfo.g_city"] = g_city
        mutableMapOf["patientInfo.g_state.id"] = g_state
        mutableMapOf["patientInfo.g_zipCode"] = g_zipCode
        mutableMapOf["patientInfo.g_home_phone"] = g_home_phone
        mutableMapOf["patientInfo.employer"] = employer
        content.reversed().forEach {
            mutableMapOf.putAll(it)
        }

        mutableMapOf.wrapperMap()
        request({ apiService.register(mutableMapOf) }, registerUserLiveData, true)
    }

    fun getDoctorList() {
        val mutableMapOf = mutableMapOf<String, String>()
        mutableMapOf.wrapperMap()
        mutableMapOf["appointmentVO.patientId"] = MineApp.userid
        requestNoCheck({ apiService.getDoctorList(mutableMapOf) }, getDoctorListLiveData)
    }

    fun createOrder(bean: DoctorInfoBean) {
        val mutableMapOf = mutableMapOf<String, String>()
        mutableMapOf["appointmentVO.patientId"] = MineApp.userid
        mutableMapOf["appointmentVO.doctorId"] = bean.id
        mutableMapOf["appointmentVO.startHour"] = bean.canAppointmentTime[bean.subIndex].startHour
        mutableMapOf["appointmentVO.startMinute"] =
            bean.canAppointmentTime[bean.subIndex].startMinute
        mutableMapOf["appointmentVO.endHour"] = bean.canAppointmentTime[bean.subIndex].endHour
        mutableMapOf["appointmentVO.endMinute"] = bean.canAppointmentTime[bean.subIndex].endMinute
        mutableMapOf.wrapperMap()
        requestNoCheck({ apiService.createOrder(mutableMapOf) }, {
            if (!TextUtils.isEmpty(it.msg)) {
                ToastUtils.showShort(it.msg)
            }
        }, isShowDialog = true)
    }

    fun getPollDownData() {
        val mutableMapOf = mutableMapOf<String, String>()
        mutableMapOf.wrapperMap()
        request({ apiService.getPollDownData(mutableMapOf) }, getPollDownLiveData)
    }

    fun updateInfo(editHolderData: List<Map<String, String>>, payWay: Boolean) {
        val mutableMapOf = IdentityHashMap<String, String>()
        mutableMapOf["patientInfo.p_id"] = MineApp.userid
        mutableMapOf["patientInfo.payWay"] = if (payWay) {
            "INSURANCE"
        } else {
            "SELF"
        }
        editHolderData.reversed().forEach {
            mutableMapOf.putAll(it)
        }
        mutableMapOf.wrapperMap()
        requestNoCheck(
            { apiService.updateInfo(mutableMapOf) },
            updateInfoLiveData,
            isShowDialog = true
        )
    }

    fun uploadImage(file: File, isFront: Boolean, item: EditHolderInfoBean) {
        requestNoCheck({
            val mutableListOf = mutableListOf<MultipartBody.Part>()
            mutableListOf.add(toMultipartBodyOfText("deviceNum", DeviceUtils.getUniqueDeviceId()))
            mutableListOf.add(toMultipartBody("filePath", file))
            apiService.uploadImage(mutableListOf)
        }, {
            if (it.isSuccess()) {
                if (isFront) {
                    item.frontPath = it.result
                } else {
                    item.backPath = it.result
                }
            }
        })
    }

    private fun toMultipartBody(key: String, file: File): MultipartBody.Part {
        val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)
        return MultipartBody.Part.createFormData(
            key,
            file.name,
            requestBody
        )
    }

    private fun toMultipartBodyOfText(key: String, text: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, text)
    }

    fun createHolder(
        lastName: String,
        firstName: String,
        gender: String,
        ssn: String,
        birth: String,
        phone: String,
        address: String,
        city: String,
        state: String,
        zip: String
    ) {
        request({
            val mutableMapOf = mutableMapOf<String, String>()
            mutableMapOf["pphEntity.lastName"] = lastName
            mutableMapOf["pphEntity.firstName"] = firstName
            mutableMapOf["pphEntity.gender"] = gender
            mutableMapOf["pphEntity.ssn"] = ssn
            mutableMapOf["pphEntity.birth"] = birth
            mutableMapOf["pphEntity.phone"] = phone
            mutableMapOf["pphEntity.address"] = address
            mutableMapOf["pphEntity.city"] = city
            mutableMapOf["pphEntity.state.id"] = state
            mutableMapOf["pphEntity.zip"] = zip
            mutableMapOf.wrapperMap()
            apiService.createPolicyHolder(mutableMapOf)
        }, createHolderLiveData)
    }


}