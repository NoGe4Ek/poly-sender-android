package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.databinding.CardAttributeBinding

class AttributesAdapter(
    private val onItemClicked: (Attribute, MaterialCardView) -> Unit,
    private val onItemLongClicked: (Attribute) -> Unit
) : ListAdapter<Attribute, AttributesAdapter.AttributeViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Attribute>() {
            override fun areItemsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Attribute, newItem: Attribute): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val selectedAttributes = mutableSetOf<Attribute>()
    fun setSelectedAttributes(selectedAttributes: Set<Attribute>) {
        this.selectedAttributes.clear()
        this.selectedAttributes.addAll(selectedAttributes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder {
        return AttributeViewHolder(
            CardAttributeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) {
        val attribute: Attribute = getItem(position)
        holder.bind(attribute)
        val card = holder.binding.attributeCard

        card.setOnLongClickListener {
            onItemLongClicked(attribute)
            true
        }
        card.setOnClickListener {
            onItemClicked(attribute, card)
        }
    }


    inner class AttributeViewHolder(val binding: CardAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attribute: Attribute) {
            binding.apply {
                attributeCard.isChecked = selectedAttributes.contains(attribute)
                cardAttributeName.text = attribute.attributeName
                cardAttributeStudentCount.text = "${attribute.students.size} students"
            }
        }
    }
}