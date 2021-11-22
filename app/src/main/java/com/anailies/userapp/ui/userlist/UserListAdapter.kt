package com.anailies.userapp.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anailies.userapp.databinding.UserListItemBinding
import com.anailies.userapp.domain.entities.User

class UserListAdapter(
    private val onLongClick: (Long) -> Unit,
) : ListAdapter<User, UserListAdapter.UserViewHolder>(UserListItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), onLongClick)
    }

    class UserViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onLongClick: (Long) -> Unit) {
            binding.user = user
            binding.root.setOnLongClickListener {
                onLongClick(user.id)
                true
            }
        }
    }
}