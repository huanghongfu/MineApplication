package com.hhf.project.bean

import java.io.Serializable

/**
 *  @date 2021/10/21
 *  @author admin
 *  @action
 */
data class PollDataBean(
    val martial: Martial,
    val race: Race,
    val state: State,
    val title: Title
):Serializable

data class Martial(
    val defautValue: String,
    val list: List<MartialListBean>
):Serializable

data class Race(
    val defautValue: String,
    val list: List<MartialListBean>
):Serializable

data class State(
    val defautValue: String,
    val list: List<StatusListBean>
):Serializable

data class Title(
    val defautValue: String,
    val list: List<MartialListBean>
):Serializable

data class MartialListBean(
    val code: String,
    val dictType: DictType,
    val id: String,
    val isDel: Boolean,
    val name: String
):Serializable

data class DictType(
    val flag: String,
    val id: String,
    val kind: String,
    val name: String
):Serializable

data class StatusListBean(
    val id: String,
    val name: String
):Serializable

