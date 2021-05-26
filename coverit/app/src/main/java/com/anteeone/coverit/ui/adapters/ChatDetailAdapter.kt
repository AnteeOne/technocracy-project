package com.anteeone.coverit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.MessageModel

class ChatDetailAdapter() : RecyclerView.Adapter<ChatDetailAdapter.ChatDetailViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    private var messages: List<MessageModel> = emptyList()
    lateinit var currentUserId: String

    class ChatDetailViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        lateinit var meTextView: TextView
        lateinit var anotherTextView: TextView

        fun bindForMe(message: MessageModel) {
            meTextView = itemView.findViewById(R.id.item_message_me)
            meTextView.text = message.message
        }

        fun bindForAnother(message: MessageModel) {
            anotherTextView = itemView.findViewById(R.id.item_message_another)
            anotherTextView.text = message.message
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDetailViewHolder =
        if (viewType == VIEW_TYPE_MESSAGE_SENT) ChatDetailViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_message_me, parent, false
            )
        )
        else ChatDetailViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_message_another, parent, false
            )
        )

    override fun onBindViewHolder(holder: ChatDetailViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_MESSAGE_SENT) holder.bindForMe(messages[position])
        else holder.bindForAnother(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int) =
        if (messages[position].senderId == currentUserId) VIEW_TYPE_MESSAGE_SENT
        else VIEW_TYPE_MESSAGE_RECEIVED

    fun setData(list: List<MessageModel>,onFinish: () -> Unit){
        messages = list
        notifyDataSetChanged()
        onFinish()
    }

    fun setCurrentUser(id: String){
        currentUserId = id
    }
}