package com.hhf.project.ext

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.hhf.project.bean.StatusListBean
import com.hhf.project.constant.GlobalConstants
import com.hhf.project.widght.CommonTableView
import java.util.*

/**
 *  @date 2021/10/18
 *  @author admin
 *  @action
 */

val genderArray = arrayOf("male", "female")

fun selectDate(context: Context, commonView: CommonTableView) {
    val calendar: Calendar = Calendar.getInstance() //获取日期格式器对象

    val datePickerDialog = DatePickerDialog(
        context, { _, p1, p2, p3 ->
            commonView.setText(
                "${
                    if (p2 >= 10) {
                        p2.toString()
                    } else {
                        "0".plus(p2)
                    }
                }/${
                    if (p3 > 10) {
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
}


fun getPayClassList(): List<StatusListBean> {
    return mutableListOf(
        StatusListBean("", "None"),
        StatusListBean("3", "Medicare"),
        StatusListBean("1", "Other"),
        StatusListBean("2", "Commercial")
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
        StatusListBean("1", "self"),
        StatusListBean("2", "spouse"),
        StatusListBean("3", "child"),
        StatusListBean("5", "mather"),
        StatusListBean("6", "father"),
        StatusListBean("4", "other ")
    )
}