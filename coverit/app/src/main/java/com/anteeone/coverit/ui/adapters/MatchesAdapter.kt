package com.anteeone.coverit.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.ui.utils.extensions.loadImage

class MatchesAdapter(val onClick: (user: User) -> Unit) :
    RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    private var userList: List<User> = emptyList()

    class MatchesViewHolder(itemView: View, val onClick: (user: User) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val mAvatar: ImageView = itemView.findViewById(R.id.item_matches_avatar)
        private val mName: TextView = itemView.findViewById(R.id.item_matches_name)
        private val mRole: TextView = itemView.findViewById(R.id.item_matches_role)

        fun bind(user: User) {
            mAvatar.loadImage(user.avatarUri)
            mName.text = user.name
            mRole.text = user.role
            itemView.setOnClickListener {
                onClick(user)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.matches_list_item, parent, false), onClick
        )
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUsers(users: List<User>, onFinish: () -> Unit) {
        this.userList = users
        notifyDataSetChanged()
        onFinish()
    }
}