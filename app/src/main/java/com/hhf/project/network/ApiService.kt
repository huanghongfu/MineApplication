package com.hhf.project.network

import com.hhf.project.bean.*
import okhttp3.MultipartBody
import retrofit2.http.*


/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "https://34.222.19.171/emr/"
        const val SERVER_URL1 = "http://eee.nat300.top/revenueManagement/"
    }

    @FormUrlEncoded
    @POST("clinic!checkGetClinicInfo.pad")
    suspend fun inputClient(@Field("clinicId") clinicId: String): ApiResponse<InputClientBean>

    @FormUrlEncoded
    @POST("patientInfo!check.pad")
    suspend fun login(@FieldMap text: Map<String, String>): ApiResponse<String>

    @FormUrlEncoded
    @POST("appointment!doctorTimeList.pad")
    suspend fun getDoctorList(@FieldMap text: Map<String, String>): ApiResponse<MutableList<DoctorInfoBean>>

    @FormUrlEncoded
    @POST("appointment!createPatientAppointment.pad")
    suspend fun createOrder(@FieldMap text: Map<String, String>): ApiResponse<Any>


    @FormUrlEncoded
    @POST("patientInfo!register.pad")
    suspend fun register(@FieldMap text: Map<String, String>): ApiResponse<String>

    @FormUrlEncoded
    @POST("patientInfo!updatePateintInsurance.pad")
    suspend fun updateInfo(@FieldMap text: Map<String, String>): ApiResponse<String>


    @FormUrlEncoded
    @POST("dict!padall.pad")
    suspend fun getPollDownData(@FieldMap text: Map<String, String>): ApiResponse<PollDataBean>


    @FormUrlEncoded
    @POST("insureCompany!getClinicInsureCompanyList.pad")
    suspend fun searchName(@FieldMap text: Map<String, String>): ApiResponse<SearchInfoBean>

    @FormUrlEncoded
    @POST("policyHolder!createPatientPolicyHolder.pad")
    suspend fun createPolicyHolder(@FieldMap text: Map<String, String>): ApiResponse<CreateHolderBean>

    @Multipart
    @POST("patientIC!addBillingAttachmentAccesstory.pad")
    suspend fun uploadImage(@Part() files: MutableList<MultipartBody.Part>): ApiResponse<String>

}