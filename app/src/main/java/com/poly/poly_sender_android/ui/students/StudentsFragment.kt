package com.poly.poly_sender_android.ui.students

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poly.poly_sender_android.R
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentStudentsBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.MainActivityViewModel
import com.poly.poly_sender_android.ui.adapters.StudentsAdapter
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsNews
import com.poly.poly_sender_android.ui.students.mvi.StudentsState
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class StudentsFragment : Fragment(),
    MviView<StudentsState, StudentsNews> {

    @Inject
    lateinit var logger: Logger

    private val studentsSharedViewModel: StudentsSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!

    lateinit var studentsRecycler: RecyclerView
    lateinit var studentsAdapter: StudentsAdapter

    private var deleteAllSelectedStudents = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.connect(javaClass)
        studentsRecycler = binding.studentList
        studentsAdapter = StudentsAdapter(
            onItemClicked = { student, card ->
                if (card.isChecked) {
//                    if (binding.checkboxAll.isChecked) {
//                        deleteAllSelectedStudents = false
//                        binding.checkboxAll.isChecked = false
//                    }
                    studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents - student)
                    studentsSharedViewModel.obtainWish(StudentsWish.DismissStudent(student))
                } else {
//                    if (studentsSharedViewModel.nmState.students.size == studentsSharedViewModel.nmState.selectedStudents.size + 1) {
//                        binding.checkboxAll.isChecked = true
//                    }
                    studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents + student)
                    studentsSharedViewModel.obtainWish(StudentsWish.SelectStudent(student))
                }
                card.isChecked = !card.isChecked
            },
            onItemLongClicked = { student ->
                val studentProfileFragment =
                    StudentProfileFragmentDirections.actionGlobalStudentProfileFragment(student)
                findNavController().navigate(studentProfileFragment)
            }) //TODO add student to selected list in state & set student.isChecked to true OR VISE VERSA
        studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents)
        studentsRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        studentsRecycler.adapter = studentsAdapter
        val itemDecoration = SpacesItemDecoration(8)
        studentsRecycler.addItemDecoration(itemDecoration)

        //Fix recycler view padding bottom
        ViewCompat.setOnApplyWindowInsetsListener(studentsRecycler) { view, insets ->
            val windowInsets = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    view.rootWindowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars()).bottom
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    view.rootWindowInsets?.stableInsetTop ?: 0
                }
                else -> {
                    0
                }
            }
            studentsRecycler.updatePadding(bottom = windowInsets)
            insets
        }

        with(studentsSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@StudentsFragment)
        }

        //WARN: wish caught 2 times after navigate from fragment with the same VM, but state obtained correctly
        studentsSharedViewModel.obtainWish(
            StudentsWish.RefreshStudents(
                searchSelectedAttributes = studentsSharedViewModel.nmState.searchSelectedAttributes
            )
        )

        mainActivityViewModel.getStudentsAttributingEvent().observe(viewLifecycleOwner) { studentsAttributingEvent ->
            if (studentsAttributingEvent == true) {
                val studentsAttributingFragment =
                    StudentsFragmentDirections.actionStudentsFragmentToStudentsAttributingFragment()
                findNavController().navigate(R.id.action_StudentsFragment_to_StudentsAttributingFragment)
                mainActivityViewModel.setStudentsAttributingEvent(false)
            }
        }

//        binding.checkboxAll.setOnCheckedChangeListener { compoundButton, b ->
//            if (b) {
//                for (student in studentsSharedViewModel.nmState.students) {
//                    studentsSharedViewModel.obtainWish(StudentsWish.SelectStudent(student))
//                }
//                studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.students)
//                studentsAdapter.notifyDataSetChanged()
//                deleteAllSelectedStudents = true
//            } else {
//                if (deleteAllSelectedStudents) {
//                    for (student in studentsSharedViewModel.nmState.students) {
//                        studentsSharedViewModel.obtainWish(StudentsWish.DismissStudent(student))
//                    }
//                    studentsAdapter.setSelectedStudents(emptySet())
//                    studentsAdapter.notifyDataSetChanged()
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: StudentsState) {
        studentsAdapter.submitList(state.students.toList())
    }

    override fun renderNews(new: StudentsNews) {
        when (new) {
            is StudentsNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

