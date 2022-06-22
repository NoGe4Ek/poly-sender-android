package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.databinding.CardAttributeBinding

class AttributesAdapter(
    private val onItemClicked: (Attribute) -> Unit,
    private val onEditClicked: (Attribute) -> Unit,
    private val onDeleteClicked: (Attribute) -> Unit,
    private val onShareClicked: (Attribute) -> Unit
): ListAdapter<Attribute, AttributesAdapter.AttributeViewHolder>(DiffCallback)
{
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

        holder.itemView.setOnClickListener {
            onItemClicked(attribute)
        }
        holder.binding.cardAttributeButtonEdit.setOnClickListener {
            onEditClicked(attribute)
        }
        holder.binding.cardAttributeButtonDelete.setOnClickListener {
            onDeleteClicked(attribute)
        }
        holder.binding.cardAttributeButtonShare.setOnClickListener {
            onShareClicked(attribute)
        }
    }

    inner class AttributeViewHolder(val binding: CardAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(attribute: Attribute) {
            binding.apply {
                cardAttributeName.text = attribute.attributeName
                cardAttributeSection.text = attribute.groupName
                cardAttributeStudentCount.text = attribute.students.size.toString()
                cardAttributeCreationType.text = attribute.type
                cardAttributeCreationDate.text = attribute.created
            }
        }
    }
}