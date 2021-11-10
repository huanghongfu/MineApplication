package com.hhf.project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.aigestudio.wheelpicker.WheelPicker
import com.blankj.utilcode.util.ActivityUtils
import com.hhf.project.MineApp
import com.hhf.project.R
import com.hhf.project.bean.PollDataBean
import com.hhf.project.databinding.ActivityRegisterUserBinding
import com.hhf.project.ext.*
import com.hhf.project.vm.RegisterUserViewModel
import com.hhf.project.widght.CommonTableView
import me.hgj.jetpackmvvm.demo.app.base.BaseActivity
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */

class RegisterUserActivity : BaseActivity<RegisterUserViewModel, ActivityRegisterUserBinding>() {
    private lateinit var mEditHolderFragment: EditHolderFragment
    var mPollDataBean: PollDataBean? = null


    var mStatusIndex = 0
    var mTitleIndex = 0
    var mMartialIndex = 0
    var mRaceIndex = 0

    companion object {
        @JvmStatic
        fun start(
            context: Context,
            firstName: String,
            lastName: String,
            dob: String,
            genderIndex: String
        ) {
            val starter = Intent(context, RegisterUserActivity::class.java)
            starter.putExtra("firstName", firstName)
            starter.putExtra("lastName", lastName)
            starter.putExtra("dob", dob)
            starter.putExtra("genderIndex", genderIndex)
            context.startActivity(starter)
        }
    }

    private fun initBundle() {
        intent?.apply {
            mDatabind.commonViewFirstName.setText(getStringExtra("firstName"))
            mDatabind.commonViewLastName.setText(getStringExtra("lastName"))
            mDatabind.commonViewDob.setText(getStringExtra("dob"))
            mDatabind.commonViewGender.setText(genderArray[getStringExtra("genderIndex").toInt()])
            mDatabind.commonViewGender.setExtField(getStringExtra("genderIndex"))
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.registerUserLiveData.observe(this, {
            parseState(it, {
                MineApp.userid=it
                DoctorSelectActivity.start(this)
                ActivityUtils.finishAllActivities()
            })
        })

        mViewModel.getPollDownLiveData.observe(this, { it ->
            parseState(it, {
                mPollDataBean = it
                it.state.apply {
                    if (TextUtils.isEmpty(defautValue)) {
                        mStatusIndex = 0
                        initTextView(
                            mDatabind.commonViewStatus,
                            list[mStatusIndex].name,
                            list[mStatusIndex].id
                        )
                    } else {
                        list.mapIndexed { index, statusListBean ->
                            if (TextUtils.equals(statusListBean.id, defautValue)) {
                                mStatusIndex = index
                                initTextView(
                                    mDatabind.commonViewStatus,
                                    list[mStatusIndex].name,
                                    list[mStatusIndex].id
                                )
                            }
                        }
                    }
                }


                it.title.apply {
                    if (TextUtils.isEmpty(defautValue)) {
                        mTitleIndex = 0
                        initTextView(
                            mDatabind.commonViewTitle,
                            list[mTitleIndex].name,
                            list[mTitleIndex].id
                        )
                    } else {
                        list.mapIndexed { index, statusListBean ->
                            if (TextUtils.equals(statusListBean.id, defautValue)) {
                                mTitleIndex = index
                                initTextView(
                                    mDatabind.commonViewTitle,
                                    list[mTitleIndex].name,
                                    list[mTitleIndex].id
                                )
                            }
                        }
                    }
                }

                it.race.apply {
                    if (TextUtils.isEmpty(defautValue)) {
                        mRaceIndex = 0
                        initTextView(
                            mDatabind.commonViewRace,
                            list[mRaceIndex].name,
                            list[mRaceIndex].id
                        )
                    } else {
                        list.mapIndexed { index, statusListBean ->
                            if (TextUtils.equals(statusListBean.id, defautValue)) {
                                mRaceIndex = index
                                initTextView(
                                    mDatabind.commonViewRace,
                                    list[mRaceIndex].name,
                                    list[mRaceIndex].id
                                )
                            }
                        }
                    }
                }

                it.martial.apply {
                    if (TextUtils.isEmpty(defautValue)) {
                        mMartialIndex = 0
                        initTextView(
                            mDatabind.commonViewMaritalStatus,
                            list[mMartialIndex].name,
                            list[mMartialIndex].id
                        )
                    } else {
                        list.mapIndexed { index, statusListBean ->
                            if (TextUtils.equals(statusListBean.id, defautValue)) {
                                mMartialIndex = index
                                initTextView(
                                    mDatabind.commonViewMaritalStatus,
                                    list[mMartialIndex].name,
                                    list[mMartialIndex].id
                                )
                            }
                        }
                    }
                }

            })
        })
    }


    override fun layoutId() = R.layout.activity_register_user

    override fun initView(savedInstanceState: Bundle?) {
        initBundle()
        mEditHolderFragment = supportFragmentManager.findFragmentByTag("fragmentTag") as EditHolderFragment

        mDatabind.ivBack.setOnClickListener { finish() }

        mDatabind.commonViewDob.setOnClickListener {
            selectDate(this, mDatabind.commonViewDob)
        }

        mDatabind.commonViewTitle.setOnClickListener {
            mPollDataBean?.let {
                WheelViewDialogFragment.newInstance(
                    it,
                    WheelViewDialogFragment.TITLE_TYPE,
                    mTitleIndex
                )
                    .setOnItemSelectedListener(WheelPicker.OnItemSelectedListener { picker, data, position ->
                        mTitleIndex = position
                        mDatabind.commonViewTitle.setText(it.title.list[position].name)
                        mDatabind.commonViewTitle.setExtField(it.title.list[position].id)
                    }).show(supportFragmentManager, "")
            }
        }

        mDatabind.commonViewStatus.setOnClickListener {
            mPollDataBean?.let {
                WheelViewDialogFragment.newInstance(
                    it,
                    WheelViewDialogFragment.STATUS_TYPE,
                    mStatusIndex
                )
                    .setOnItemSelectedListener(WheelPicker.OnItemSelectedListener { _, _, position ->
                        mStatusIndex = position
                        mDatabind.commonViewStatus.setText(it.state.list[position].name)
                        mDatabind.commonViewStatus.setExtField(it.state.list[position].id)
                    }).show(supportFragmentManager, "")
            }
        }

        mDatabind.commonViewMaritalStatus.setOnClickListener {
            mPollDataBean?.let {
                WheelViewDialogFragment.newInstance(
                    it,
                    WheelViewDialogFragment.MARTIAL_TYPE,
                    mMartialIndex
                )
                    .setOnItemSelectedListener(WheelPicker.OnItemSelectedListener { _, _, position ->
                        mMartialIndex = position
                        mDatabind.commonViewMaritalStatus.setText(it.martial.list[position].name)
                        mDatabind.commonViewMaritalStatus.setExtField(it.martial.list[position].id)
                    }).show(supportFragmentManager, "")
            }
        }

        mDatabind.commonViewRace.setOnClickListener {
            mPollDataBean?.let {
                WheelViewDialogFragment.newInstance(
                    it,
                    WheelViewDialogFragment.RACE_TYPE,
                    mRaceIndex
                )
                    .setOnItemSelectedListener(WheelPicker.OnItemSelectedListener { _, _, position ->
                        mRaceIndex = position
                        mDatabind.commonViewRace.setText(it.race.list[position].name)
                        mDatabind.commonViewRace.setExtField(it.race.list[position].id)
                    }).show(supportFragmentManager, "")
            }
        }

        mDatabind.tvRegister.clickNoRepeat {
            mViewModel.registerUser(
                lastName = mDatabind.commonViewLastName.getText(),
                firstName = mDatabind.commonViewFirstName.getText(),
                middleName = mDatabind.commonViewMiddleName.getText(),
                gender = mDatabind.commonViewGender.getExtField(),
                strdob = mDatabind.commonViewDob.getText(),
                email = mDatabind.commonViewEmail.getText(),
                title = mDatabind.commonViewTitle.getExtField(),
                martial = mDatabind.commonViewMaritalStatus.getExtField(),
                race = mDatabind.commonViewRace.getExtField(),
                ssn = mDatabind.commonViewSsn.getText(),
                employer = mDatabind.commonViewEmployer.getText(),
                g_addr = mDatabind.commonViewAddress.getText(),
                g_city = mDatabind.commonViewCity.getText(),
                g_state = mDatabind.commonViewStatus.getExtField(),
                g_zipCode = mDatabind.commonViewZip.getText(),
                g_home_phone = mDatabind.commonViewPhone.getText(),
                payWay = mEditHolderFragment.getPayWay(),
                content=mEditHolderFragment.getEditHolderData()
            )
        }

        mViewModel.getPollDownData()

    }

    private fun initTextView(view: CommonTableView, name: String, id: String) {
        view.setText(name)
        view.setExtField(id)
    }
}