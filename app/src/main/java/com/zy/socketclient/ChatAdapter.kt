package com.zy.socketclient

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *chat adapter
 */
class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
 //   private var mDatas =

    companion object {
        private const val LEFT = 1
        private const val RIGHT = 2
    }

    fun replace() {

    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
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

    }


    override fun getItemCount(): Int {
        return 0
    }


    class LeftChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class RightChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}