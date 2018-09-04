package com.zy.socketclient.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.zy.socketclient.R
import kotlinx.android.synthetic.main.send_msg_layout.view.*

class SendMsgLayout : ConstraintLayout {
    private lateinit var mView: View

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }


    private fun initView(context: Context) {
        mView = LayoutInflater.from(context).inflate(R.layout.send_msg_layout, this, true)
    }

    /**
     * 获取内容
     */
    fun getText(): Editable? = mView.editText.text

    /**
     * 发送按钮
     */
    fun send(): Button = mView.send

    /**
     * 设置发送监听事件
     */
    fun setSendListener(onClickListener: View.OnClickListener) {
        mView.send.setOnClickListener(onClickListener)
    }

    /**
     * 设置文本监听事件
     */
    fun addTextChangedListener(textWatcher: TextWatcher) {
        mView.editText.addTextChangedListener(textWatcher)
    }


}
