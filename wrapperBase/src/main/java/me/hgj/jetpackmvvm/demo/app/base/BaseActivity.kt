package me.hgj.jetpackmvvm.demo.app.base

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ActivityUtils
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.demo.app.App
import me.hgj.jetpackmvvm.demo.app.event.AppViewModel
import me.hgj.jetpackmvvm.demo.app.event.EventViewModel
import me.hgj.jetpackmvvm.demo.app.ext.dismissLoadingExt
import me.hgj.jetpackmvvm.demo.app.ext.showLoadingExt
import me.hgj.jetpackmvvm.ext.getAppViewModel
import me.jessyan.autosize.AutoSizeCompat
import java.util.*

/**
 * 时间　: 2019/12/21
 * 作者　: hegaojian
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
open abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> :
    BaseVmDbActivity<VM, DB>() {

    //Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
    val appViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    //Application全局的ViewModel，用于发送全局通知操作
    val eventViewModel: EventViewModel by lazy { getAppViewModel<EventViewModel>() }

    abstract override fun layoutId(): Int

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        showLoadingExt(message)
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dismissLoadingExt()
    }

    protected var timer: Timer? = null

    override fun onResume() {
        super.onResume()
        val mContext = this
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                if (ActivityUtils.getTopActivity().localClassName.contains("SplashActivity")) {
                    return
                }
                if (App.stayTime > 0 && ActivityUtils.getTopActivity() == mContext) {
                    Log.e("adsjda", "mNoTouchTime=$mNoTouchTime")
                    mNoTouchTime++
                    if (mNoTouchTime >= App.stayTime) {
                        doQuitTask()
                    }
                }
            }
        }, 0, 1000)
    }

    override fun onPause() {
        super.onPause()
        mNoTouchTime = 0
        timer?.cancel()
    }

    open fun doQuitTask() {
        try {

            val loader: ClassLoader = classLoader
            val clz = loader.loadClass("com.hhf.project.ui.SplashActivity")
            clz.getDeclaredMethod("start", Context::class.java)
                .invoke(clz, this)
            ActivityUtils.finishAllActivities()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open var mNoTouchTime = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mNoTouchTime = 0
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    v!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // Return whether touch the view.
    private  fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 在任何情况下本来适配正常的布局突然出现适配失效，适配异常等问题，只要重写 Activity 的 getResources() 方法
     */
    override fun getResources(): Resources {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        return super.getResources()
    }
}