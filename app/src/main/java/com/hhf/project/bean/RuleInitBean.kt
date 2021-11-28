package com.hhf.project.bean

/**
 *  @date 2021/11/28
 *  @author admin
 *  @action
 */
data class RuleInitBean(
    val agreementName: String,
    val clinicId: String,
    val group: MutableList<Group>
)

data class Group(
    val detail: List<Detail>,
    val groupId: String,
    var isChecked: Boolean,
    val groupName: String
)

data class Detail(
    val description: String,
    val detailId: String,
    val detailName: String,
    val isLink: Boolean
)