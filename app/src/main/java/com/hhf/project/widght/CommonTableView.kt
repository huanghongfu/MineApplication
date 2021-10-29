package com.hhf.project.widght

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.hhf.project.R
import kotlinx.android.synthetic.main.text_view_layout.view.*

/**
 *  @date 2021/10/16
 *  @author admin
 *  @action
 */
class CommonTableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mIndex:String=""
    private var mRightIndex:String=""
    private var editable: Boolean
    private var ivRightImage: ImageView
    private var tvTitle: TextView
    private var ivMustImage: ImageView
    private var canEditableView: TextView
    private var noEditableView: TextView
    private var llContentContainer: View
    private var ll_right_content: View
    private var tv_right_content: TextView
    private var ll_left_container: View

    init {
        LayoutInflater.from(context).inflate(R.layout.text_view_layout, this)

        val obtainStyledAttributes =
            getContext().obtainStyledAttributes(attrs, R.styleable.CommonTableView)
        val title = obtainStyledAttributes.getText(R.styleable.CommonTableView_common_title_hint)
        val res =
            obtainStyledAttributes.getResourceId(R.styleable.CommonTableView_common_right_icon, 0)
        val showMust =
            obtainStyledAttributes.getBoolean(R.styleable.CommonTableView_common_show_must, false)

        val showCommonBg =
            obtainStyledAttributes.getBoolean(
                R.styleable.CommonTableView_common_use_content_white_bg,
                true
            )
        editable =
            obtainStyledAttributes.getBoolean(R.styleable.CommonTableView_common_editable, false)

        noEditableView = findViewById<TextView>(R.id.text_view)
        canEditableView = findViewById<TextView>(R.id.edit_text)
        ivMustImage = findViewById<ImageView>(R.id.iv_must_image)
        tvTitle = findViewById<TextView>(R.id.tv_title)
        ivRightImage = findViewById<ImageView>(R.id.iv_right_image)
        ll_left_container = findViewById<TextView>(R.id.ll_left_container)
        llContentContainer = findViewById<View>(R.id.ll_content_container)
        ll_right_content = findViewById<View>(R.id.ll_right_content)
        tv_right_content = findViewById<TextView>(R.id.tv_right_content)
        if(TextUtils.isEmpty(title)){
            ll_left_container.isVisible=false
        }else{
            ll_left_container.isVisible=true
            tvTitle.text = title
        }


        iv_must_image.isVisible = showMust

        noEditableView.isVisible = !editable
        canEditableView.isVisible = editable

        if (res > 0) {
            ivRightImage.setImageResource(res)
            ivRightImage.isVisible = true
        } else {
            ivRightImage.isVisible = false
        }

        if (showCommonBg) {
            llContentContainer.setBackgroundResource(R.drawable.common_selector_rect_bg)
            ll_right_content.setBackgroundResource(R.drawable.common_selector_rect_bg)
        } else {
            llContentContainer.setBackgroundResource(R.drawable.common_selector_rect_bg_white)
            ll_right_content.setBackgroundResource(R.drawable.common_selector_rect_bg_white)
        }
    }

    fun setText(text: String) {
        if(editable){
            canEditableView.text = text
        }else{
            noEditableView.text = text
        }
    }

    fun getText(): String {
        return if (editable) {
            canEditableView.text.toString()
        } else {
            noEditableView.text.toString()
        }
    }

    fun setRightText(text: String) {
        tv_right_content.text=text
    }

    fun getRightText(): String {
        return tv_right_content.text.toString()
    }

    fun setRightContentVisible(rightContentVisible:Boolean){
        ll_right_content.isVisible=rightContentVisible
    }

    /**
     * 设置拓展字段
     */
    fun setExtField(index:String){
        mIndex=index
    }

    fun getExtField()=mIndex

    fun getRightExtField()=mRightIndex

    fun setRightExtField(index:String){
        mRightIndex=index
    }
}