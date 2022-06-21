package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.databinding.CardStudentBinding

class StudentsAdapter(
    private val onItemClicked: (Student) -> Unit
): ListAdapter<Student, StudentsAdapter.StudentViewHolder>(DiffCallback)
{
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            CardStudentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student: Student = getItem(position)
        holder.bind(student)

        holder.itemView.setOnClickListener {
            onItemClicked(student)
        }
    }

    inner class StudentViewHolder(private val binding: CardStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.apply {
                cardStudentName.text = student.name
                cardStudentEmail.text = student.email
            }
        }
    }
}