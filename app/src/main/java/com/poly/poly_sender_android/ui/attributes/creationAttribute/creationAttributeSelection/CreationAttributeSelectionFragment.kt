package com.poly.poly_sender_android.ui.attributes.creationAttribute.creationAttributeSelection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeBinding
import com.poly.poly_sender_android.databinding.FragmentCreationAttributeSelectionBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentsAdapter
import com.poly.poly_sender_android.ui.attributes.creationAttribute.CreationAttributeSharedViewModel
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeNews
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeState
import com.poly.poly_sender_android.ui.attributes.creationAttribute.mvi.CreationAttributeWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreationAttributeSelectionFragment : Fragment(),
    MviView<CreationAttributeState, CreationAttributeNews> {

    @Inject
    lateinit var logger: Logger

    private val creationAttributeSharedViewModel: CreationAttributeSharedViewModel by activityViewModels()

    private var _binding: FragmentCreationAttributeSelectionBinding? = null
    private val binding get() = _binding!!

    lateinit var studentsRecycler: RecyclerView
    lateinit var studentsAdapter: StudentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationAttributeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)

        studentsRecycler = binding.studentList
        studentsAdapter = StudentsAdapter(onItemClicked = {student, materialCardView -> }, onItemLongClicked = {student -> }) //TODO add student to selected list in state & set student.isChecked to true OR VISE VERSA
        studentsRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        studentsRecycler.adapter = studentsAdapter

        with(creationAttributeSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationAttributeSelectionFragment)
        }

        binding.buttonNext.setOnClickListener {
            creationAttributeSharedViewModel.apply {
                obtainWish(
                    CreationAttributeWish.UpdateSharedStorageBySelection(nmState.students, nmState.selectedStudents)
                )
            }
        }
        binding.buttonFilter.setOnClickListener {
            //TODO navigate to searchPage
        }
        binding.checkboxAll.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                //TODO add all students in selectedList & set student.isChecked to true & refresh students
            } else {
                //TODO delete all students from selectedList & set student.isChecked to false & refresh students
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: CreationAttributeState) {
        studentsAdapter.submitList(state.students)
    }

    override fun renderNews(new: CreationAttributeNews) {
        when (new) {
            is CreationAttributeNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

