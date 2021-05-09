package com.anteeone.coverit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.ui.utils.extensions.loadImage

class UsersAdapter(val onClick: (user: User) -> Unit): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var userList: List<User> = emptyList()

    class UsersViewHolder(itemView: View,val onClick: (user: User) -> Unit): RecyclerView.ViewHolder(itemView){

        private val titleView: TextView = itemView.findViewById(R.id.swipe_card_item_title)
        private val roleView: TextView = itemView.findViewById(R.id.swipe_card_item_role)
        private val imageView: ImageView = itemView.findViewById(R.id.swipe_card_item_avatar)
        private val detailsButton: Button = itemView.findViewById(R.id.swipe_card_item_btn_details)

        fun bind(user: User){
            titleView.text = user.name
            roleView.text = user.role
            imageView.loadImage(user.avatarUri)
            detailsButton.setOnClickListener {
                onClick(user)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.swipe_card_item,parent,false),onClick)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUsers(users: List<User>){
        this.userList = users
        notifyDataSetChanged()
    }

}