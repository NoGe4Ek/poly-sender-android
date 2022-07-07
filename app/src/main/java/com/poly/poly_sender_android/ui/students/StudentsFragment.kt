package com.poly.poly_sender_android.ui.students

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
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.AppBar
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.databinding.FragmentStudentsBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentsAdapter
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import com.poly.poly_sender_android.ui.students.mvi.StudentsNews
import com.poly.poly_sender_android.ui.students.mvi.StudentsState
import com.poly.poly_sender_android.ui.students.mvi.StudentsWish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        if (studentsSharedViewModel.nmState.selectedStudents.isEmpty()) {
            App.appBar = AppBar.StudentsBar
        } else {
            App.appBar = AppBar.StudentsSelectedBar
            App.mCurrentActivity.supportActionBar?.title =
                "Selected: ${studentsSharedViewModel.nmState.selectedStudents.size}"
        }
        App.mCurrentActivity.invalidateOptionsMenu()

        studentsRecycler = binding.studentList
        studentsAdapter = StudentsAdapter(
            onItemClicked = { student, card ->
                if (card.isChecked) {
                    //studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents.map { it.id }.toMutableSet() - student.id)
                    studentsSharedViewModel.obtainWish(StudentsWish.DismissStudent(student.id))
                } else {
                    //studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents.map{ it.id }.toMutableSet() + student.id)
                    studentsSharedViewModel.obtainWish(StudentsWish.SelectStudent(student.id))
                }
                card.isChecked = !card.isChecked
            },
            onItemLongClicked = { student ->
                val studentProfileFragment =
                    StudentProfileFragmentDirections.actionGlobalStudentProfileFragment(student)
                findNavController().navigate(studentProfileFragment)
            })
        //studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.selectedStudents.map { it.id }.toSet())
        studentsRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        studentsRecycler.adapter = studentsAdapter

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
        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.searchQueryStateFlow.debounce(300).collect { query ->
                studentsSharedViewModel.obtainWish(
                    StudentsWish.RefreshStudents(
                        studentsSharedViewModel.nmState.searchSelectedAttributes,
                        query
                    )
                )
            }
        }

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.attributingEvent) {
                    val studentsAttributingFragment =
                        StudentsFragmentDirections.actionStudentsFragmentToStudentsAttributingFragment()
                    findNavController().navigate(studentsAttributingFragment)
                    mainActivityViewModel.triggerAttributingEvent(false)
                }

                if (state.selectAllEvent) {
                    if (studentsSharedViewModel.nmState.students.size > studentsSharedViewModel.nmState.selectedStudents.size) {
                        studentsSharedViewModel.obtainWish(StudentsWish.SelectStudents(studentsSharedViewModel.nmState.students.map { it.id }.toSet()))
                        //studentsAdapter.setSelectedStudents(studentsSharedViewModel.nmState.students.map { it.id }.toSet())
                        //studentsAdapter.notifyDataSetChanged()
                        mainActivityViewModel.triggerSelectAllEvent(false)
                    } else {
                        studentsSharedViewModel.obtainWish(StudentsWish.DismissStudents(studentsSharedViewModel.nmState.students.map { it.id }.toSet()))
                        //studentsAdapter.setSelectedStudents(emptySet())
                        //studentsAdapter.notifyDataSetChanged()
                        mainActivityViewModel.triggerSelectAllEvent(false)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun renderState(state: StudentsState) {
        if (state.isLoading) {
            //TODO loading
        }

        studentsAdapter.setSelectedStudents(state.selectedStudents)
        studentsAdapter.notifyDataSetChanged()
        if (state.students.size != studentsAdapter.itemCount || state.selectedStudents.size != studentsAdapter.selectedStudents.size) {
            studentsAdapter.submitList(state.students.toList()) {
                //fix redundant space after attributing
                binding.studentList.post {
                    studentsRecycler.invalidateItemDecorations()
                }
            }
        }
    }

    override fun renderNews(new: StudentsNews) {
        when (new) {
            is StudentsNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

