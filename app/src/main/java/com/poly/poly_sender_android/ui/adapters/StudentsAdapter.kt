package com.poly.poly_sender_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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
                return oldItem == newItem
            }
        }
    }
    private val selectedStudents = mutableSetOf<Student>()
    fun setSelectedStudents(selectedStudents: Set<Student>) {
        this.selectedStudents.clear()
        this.selectedStudents.addAll(selectedStudents)
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
                var isDismissed = true
                for (selStudent in selectedStudents) {
                    if (student == selStudent) {
                        studentCard.isChecked = true
                        isDismissed = false
                        break
                    }
                }
                if (isDismissed) {
                    studentCard.isChecked = false
                }

                cardStudentName.text = student.name
                cardStudentEmail.text = student.email
            }
        }
    }
}