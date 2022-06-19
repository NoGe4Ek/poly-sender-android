package com.poly.poly_sender_android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.testwaveaccess.databinding.UserCardBinding

class UserListAdapter(
    private val onItemClicked: (User) -> Unit
): ListAdapter<User, UserListAdapter.UserViewHolder>(DiffCallback)
{
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClicked(user)
        }
    }

    inner class UserViewHolder(private val binding: UserCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {
                cardName.text = user.name
                cardEmail.text = user.email
                cardIsActive.text = if (user.isActive == "true") "Active" else "Inactive"
            }
        }
    }
}