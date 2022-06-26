package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.data.models.domainModel.StudentAttributesItem
import com.poly.poly_sender_android.databinding.CardStudentAttributeBinding
import com.poly.poly_sender_android.databinding.CardStudentBinding

class StudentAttributesAdapter
    : ListAdapter<StudentAttributesItem, StudentAttributesAdapter.StudentAttributeViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<StudentAttributesItem>() {
            override fun areItemsTheSame(oldItem: StudentAttributesItem, newItem: StudentAttributesItem): Boolean {
                return oldItem.attributeName == newItem.attributeName
            }

            override fun areContentsTheSame(oldItem: StudentAttributesItem, newItem: StudentAttributesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAttributeViewHolder {
        return StudentAttributeViewHolder(
            CardStudentAttributeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: StudentAttributeViewHolder, position: Int) {
        val studentAttribute: StudentAttributesItem = getItem(position)
        holder.bind(studentAttribute)
    }

    inner class StudentAttributeViewHolder(private val binding: CardStudentAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attributesItem: StudentAttributesItem) {
            binding.apply {
                cardStudentAttributeName.text = attributesItem.attributeName
                cardStudentAttributeValue.text = attributesItem.attributeValues.joinToString(separator = ", ")
            }
        }
    }
}