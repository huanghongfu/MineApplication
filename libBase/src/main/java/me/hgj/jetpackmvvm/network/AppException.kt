package me.hgj.jetpackmvvm.network

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/17
 * 描述　:自定义错误信息异常
 */
class AppException : Exception {

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    constructor(errCode: Int, error: String?, errorLog: String? = "") : super(error) {
        this.errorMsg = error ?: "THE SYSTEM IS BUSY,PLEASE TRY AGAIN LATER! "
        this.errCode = errCode
        this.errorLog = errorLog?:this.errorMsg
    }

    constructor(error: Error,e: Throwable?) {
        errCode = error.getKey()
        errorMsg = e?.message?:"THE SYSTEM IS BUSY,PLEASE TRY AGAIN LATER!"
        errorLog = e?.message
    }
}