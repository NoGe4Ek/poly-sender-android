package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.data.models.domainModel.Attribute
import com.poly.poly_sender_android.data.models.domainModel.Filter
import com.poly.poly_sender_android.databinding.CardFilterBinding

class FiltersAdapter(
    private val onItemClicked: (Filter) -> Unit,
    private val onItemLongClicked: (Filter) -> Unit,
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
        val card = holder.binding.filterCard

        card.setOnLongClickListener {
            onItemLongClicked(filter)
            true
        }
        holder.itemView.setOnClickListener {
            onItemClicked(filter)
        }
    }

    inner class FilterViewHolder(val binding: CardFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: Filter) {
            binding.apply {
                cardFilterName.text = filter.filterName
                cardFilterEmail.text = filter.mail

                val countText = filter.students.size.let { count ->
                    when(count) {
                        1 -> "$count студент"
                        in 2..4 -> "$count студента"
                        else -> "$count студентов"
                    }
                }
                cardFilterStudentCount.text = countText
            }
        }
    }
}