package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.databinding.CardFilterBinding

class FiltersAdapter(
    private val onItemClicked: (Filter) -> Unit,
    private val onEditClicked: (Filter) -> Unit,
    private val onDeleteClicked: (Filter) -> Unit,
    private val onShareClicked: (Filter) -> Unit
) : ListAdapter<Filter, FiltersAdapter.FilterViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Filter>() {
            override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            CardFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter: Filter = getItem(position)
        holder.bind(filter)

        holder.itemView.setOnClickListener {
            onItemClicked(filter)
        }
        holder.binding.cardFilterButtonEdit.setOnClickListener {
            onEditClicked(filter)
        }
        holder.binding.cardFilterButtonDelete.setOnClickListener {
            onDeleteClicked(filter)
        }
        holder.binding.cardFilterButtonShare.setOnClickListener {
            onShareClicked(filter)
        }
    }

    inner class FilterViewHolder(val binding: CardFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: Filter) {
            binding.apply {
                cardFilterName.text = filter.filterName
                cardFilterEmail.text = filter.mail
                cardFilterStudentCount.text = filter.students.size.toString()
                cardFilterCreationType.text = filter.type
                cardFilterMode.text = filter.mode
                cardFilterCreationDate.text = filter.created
            }
        }
    }
}