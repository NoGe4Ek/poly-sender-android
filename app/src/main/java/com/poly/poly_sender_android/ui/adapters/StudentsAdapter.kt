package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.poly.poly_sender_android.data.models.domainModel.Student
import com.poly.poly_sender_android.databinding.CardStudentBinding

class StudentsAdapter(
    private val onItemClicked: (Student, MaterialCardView) -> Unit,
    private val onItemLongClicked: (Student) -> Unit,
) : ListAdapter<Student, StudentsAdapter.StudentViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
    private val _selectedStudents = mutableSetOf<String>()

    val selectedStudents: Set<String> = _selectedStudents

    fun setSelectedStudents(selectedStudents: Set<String>) {
        this._selectedStudents.clear()
        this._selectedStudents.addAll(selectedStudents)
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
        val card = holder.binding.studentCard

        card.setOnLongClickListener {
            onItemLongClicked(student)
            true
        }

        card.setOnClickListener {
            onItemClicked(student, card)
        }
    }

    inner class StudentViewHolder(val binding: CardStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.apply {
                studentCard.isChecked = _selectedStudents.contains(student.id)

                cardStudentName.text = student.name
                cardStudentEmail.text = student.email
            }
        }
    }
}