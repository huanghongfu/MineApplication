package com.hhf.project.bean

import java.io.Serializable

/**
 *  @date 2021/10/25
 *  @author admin
 *  @action
 */
data class InputClientBean(
    val addr: String,
    val city: String,
    val name: String,
    val npi: String,
    val phone: String
):Serializable