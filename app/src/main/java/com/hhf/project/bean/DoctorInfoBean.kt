package com.hhf.project.bean

import java.io.Serializable

/**
 *  @date 2021/10/23
 *  @author admin
 *  @action
 */
data class DoctorInfoBean(
    val canAppointmentTime: List<CanAppointmentTime>,
    val firstName: String,
    val id: String,
    var subIndex: Int=0,
    val lastName: String
):Serializable

data class CanAppointmentTime(
    val endHour: String,
    val startHour: String,
    val startMinute: String,
    val endMinute: String,
    val time: String
):Serializable