package com.hhf.project.ext

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.hhf.project.bean.StatusListBean
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.widght.CommonTableView
import com.lxj.xpopup.XPopup
import com.lxj.xpopupext.listener.TimePickerListener
import java.text.SimpleDateFormat
import java.util.*


/**
 *  @date 2021/10/18
 *  @author admin
 *  @action
 */

val genderArray = arrayOf("Male", "Female")

fun selectDate(context: Context, commonView: CommonTableView) {
  val custom=  com.hhf.project.widght.TimePickerPopup(context)
        //                        .setDefaultDate(date)  //设置默认选中日期
        //                        .setYearRange(1990, 1999) //设置年份范围
        //                        .setDateRange(date, date2) //设置日期范围
        .setTimePickerListener(object : TimePickerListener {
            override fun onTimeChanged(date: Date?) {
                //时间改变
            }


            @SuppressLint("SimpleDateFormat")
            override fun onTimeConfirm(date: Date, view: View?) {
                val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy").format(date)
                commonView.setText(simpleDateFormat)
            }
        }).setShowLabel(false)

    val xpopup=XPopup.Builder(context)
        .asCustom(custom)
    xpopup .show()
}

fun selectGender(context: Context, commonView: CommonTableView) {
    AlertDialog.Builder(context)
        .setSingleChoiceItems(
            genderArray,
            if (TextUtils.isEmpty(commonView.getExtField())) {
                "0"
            } else {
                commonView.getExtField()
            }.toInt()
        ) { dialog, index ->
            dialog.dismiss()
            commonView.setExtField(index.toString())
            commonView.setText(genderArray[index])
        }.show()
}

fun MutableMap<String, String>.wrapperMap() {
    this["deviceNum"] = com.blankj.utilcode.util.DeviceUtils.getUniqueDeviceId()
    this["clinicId"] = SPUtils.getInstance().getString(GlobalConstants.CLIENT_ID)
    this["appDate"] = getCurrentDate()
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy")
    return sdf.format(Date())
}

fun getPayClassList(): List<StatusListBean> {
    return mutableListOf(
        StatusListBean("3", "Other"),
        StatusListBean("0", "Medicare"),
        StatusListBean("1", "Medicaid"),
        StatusListBean("2", "Commercial"),
        StatusListBean("4", "Medicare managed care"),
        StatusListBean("5", "Medicaid managed care"),
        StatusListBean("6", "CHAMPUS/CHAMPVA"),
        StatusListBean("7", "Workers compensation"),
        StatusListBean("8", "Auto-accident"),
    )
}

fun getLevelList(): List<StatusListBean> {
    return mutableListOf(
        StatusListBean("1", "PRIMARY"),
        StatusListBean("2", "SECONDARY"),
        StatusListBean("3", "TERTIARY")
    )
}

fun getPolicyHolderList(): List<StatusListBean> {
    return mutableListOf(
        StatusListBean("1", "Self"),
        StatusListBean("2", "Spouse"),
        StatusListBean("3", "Child"),
        StatusListBean("5", "Parent"),
        StatusListBean("6", "Relative"),
        StatusListBean("4", "Other")
    )
}