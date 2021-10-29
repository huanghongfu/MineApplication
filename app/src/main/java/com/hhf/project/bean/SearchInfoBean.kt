package com.hhf.project.bean

/**
 *  @date 2021/10/22
 *  @author admin
 *  @action
 */
data class SearchInfoBean(
    val autoCount: Boolean,
    val list: MutableList<SearchItemBean>,
    val pageNo: Int,
    val pageSize: Int,
    val total: Int,
    val totalPages: Int
)

data class SearchItemBean(
    val addr: String,
    val city: String,
    val fax: String,
    val id: String,
    val name: String,
    val phone: String
)