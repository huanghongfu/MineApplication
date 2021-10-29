package com.hhf.project.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        builder.addHeader("clinicId", "2c9fa80a49a2ce4e0149a7e247af75ca").build()
//        builder.addHeader("deviceNum", DeviceUtils.getUniqueDeviceId()).build()
//        builder.addHeader("isLogin", CacheUtil.isLogin().toString()).build()
        return chain.proceed(builder.build())
    }

}