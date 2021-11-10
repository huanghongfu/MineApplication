package com.hhf.project.ext

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.hhf.project.bean.StatusListBean
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.widght.CommonTableView
import java.text.SimpleDateFormat
import java.util.*

/**
 *  @date 2021/10/18
 *  @author admin
 *  @action
 */

val genderArray = arrayOf("Male", "Female")

fun selectDate(context: Context, commonView: CommonTableView) {
    val calendar: Calendar = Calendar.getInstance() //获取日期格式器对象

    val datePickerDialog = DatePickerDialog(
        context, { _, p1, p2, p3 ->
            val mouth=p2+1
            commonView.setText(
                "${
                    if (mouth >= 10) {
                        mouth.toString()
                    } else {
                        "0".plus(mouth)
                    }
                }/${
                    if (p3 >= 10) {
                        p3.toString()
                    } else {
                        "0".plus(p3)
                    }

                }/$p1"
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
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