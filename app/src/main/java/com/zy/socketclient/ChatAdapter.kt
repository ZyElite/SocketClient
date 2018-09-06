package com.zy.socketclient

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zy.socketclient.model.Message
import kotlinx.android.synthetic.main.im_chat_left_layout.view.*
import kotlinx.android.synthetic.main.im_chat_right_layout.view.*

/**
 *chat adapter
 */
class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDatas = mutableListOf<Message>()

    /**
     * init const
     */
    companion object {
        private const val LEFT = 1
        private const val RIGHT = 2
    }

    /**
     * replace msg data
     */
    fun replace(messages: List<Message>) {
        mDatas.clear()
        mDatas.addAll(messages)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDatas[position].id == 1) RIGHT else LEFT
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var holder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LEFT -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.im_chat_left_layout, viewGroup, false)
                holder = LeftChatHolder(view)
            }
            RIGHT -> {
                val view1 = LayoutInflater.from(viewGroup.context).inflate(R.layout.im_chat_right_layout, viewGroup, false)
                holder = RightChatHolder(view1)
            }
        }
        return holder!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        val message = mDatas[position]
        when (itemViewType) {
            LEFT -> {
                viewHolder.itemView.leftTvContent.text = message.content
            }
            RIGHT -> {
                viewHolder.itemView.rightTvContent.text = message.content
            }
        }
    }


    override fun getItemCount(): Int {
        return mDatas.size
    }


    class LeftChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class RightChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}