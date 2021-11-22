package com.anailies.userapp.ui.userlist

import androidx.recyclerview.widget.DiffUtil
import com.anailies.userapp.domain.entities.User

class UserListItemDiffUtil : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}