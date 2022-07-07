package com.poly.poly_sender_android.ui.filters.creationFilter.creationFilterSelection

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
import com.poly.poly_sender_android.databinding.FragmentCreationFilterSelectionBinding
import com.poly.poly_sender_android.mvi.MviView
import com.poly.poly_sender_android.ui.adapters.StudentsAdapter
import com.poly.poly_sender_android.ui.decorators.SpacesItemDecoration
import com.poly.poly_sender_android.ui.filters.creationFilter.CreationFilterSharedViewModel
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterNews
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterState
import com.poly.poly_sender_android.ui.filters.creationFilter.mvi.CreationFilterWish
import com.poly.poly_sender_android.ui.mainActivity.MainActivityViewModel
import com.poly.poly_sender_android.ui.studentProfile.StudentProfileFragmentDirections
import com.poly.poly_sender_android.util.MessageConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@AndroidEntryPoint
class CreationFilterSelectionFragment : Fragment(),
    MviView<CreationFilterState, CreationFilterNews> {

    @Inject
    lateinit var logger: Logger

    private val creationFilterSharedViewModel: CreationFilterSharedViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentCreationFilterSelectionBinding? = null
    private val binding get() = _binding!!

    lateinit var studentsRecycler: RecyclerView
    lateinit var studentsAdapter: StudentsAdapter
    private val itemDecoration = SpacesItemDecoration()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreationFilterSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.connect(javaClass)

        if (creationFilterSharedViewModel.nmState.selectedStudents.isEmpty()) {
            App.appBar = AppBar.CreationSelectionBar
        } else {
            App.appBar = AppBar.CreationSelectionSelectedBar
            App.mCurrentActivity.supportActionBar?.title =
                "Selected: ${creationFilterSharedViewModel.nmState.selectedStudents.size}"
        }
        App.mCurrentActivity.invalidateOptionsMenu()

        studentsRecycler = binding.studentList
        studentsAdapter = StudentsAdapter(
            onItemClicked = { student, card ->
                if (card.isChecked) {
                    creationFilterSharedViewModel.obtainWish(
                        CreationFilterWish.DismissStudent(
                            student.id
                        )
                    )
                } else {
                    creationFilterSharedViewModel.obtainWish(
                        CreationFilterWish.SelectStudent(
                            student.id
                        )
                    )
                }
                card.isChecked = !card.isChecked
            },
            onItemLongClicked = { student ->
                val studentProfileFragment =
                    StudentProfileFragmentDirections.actionGlobalStudentProfileFragment(student)
                findNavController().navigate(studentProfileFragment)
            })
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

        with(creationFilterSharedViewModel) {
            bind(viewLifecycleOwner.lifecycleScope, this@CreationFilterSelectionFragment)
        }

        //WARN: wish caught 2 times after navigate from fragment with the same VM, but state obtained correctly

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.searchQueryStateFlow.debounce(300).collect { query ->
                creationFilterSharedViewModel.obtainWish(
                    CreationFilterWish.RefreshStudents(
                        creationFilterSharedViewModel.nmState.searchSelectedAttributes,
                        query
                    )
                )
            }
        }

        lifecycleScope.launchWhenResumed {
            mainActivityViewModel.stateFlow.collect { state ->
                if (state.nextEvent) {
                    mainActivityViewModel.triggerNext(false)
                    if (creationFilterSharedViewModel.nmState.selectedStudents.isNotEmpty()) {
                        creationFilterSharedViewModel.apply {
                            obtainWish(
                                CreationFilterWish.UpdateSharedStorageBySelection(
                                    nmState.students,
                                    nmState.selectedStudents
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            MessageConstants.ERROR_EMPTY_FILL,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                if (state.attributingEvent) {
                    val creationFilterSelectionAttributingFragment =
                        CreationFilterSelectionFragmentDirections.actionCreationFilterSelectionFragmentToCreationFilterSelectionAttributingFragment()
                    findNavController().navigate(creationFilterSelectionAttributingFragment)
                    mainActivityViewModel.triggerAttributingEvent(false)
                }
                if (state.selectAllEvent) {
                    if (creationFilterSharedViewModel.nmState.students.size > creationFilterSharedViewModel.nmState.selectedStudents.size) {
                        creationFilterSharedViewModel.obtainWish(
                            CreationFilterWish.SelectStudents(
                                creationFilterSharedViewModel.nmState.students.map { it.id }
                                    .toSet()
                            )
                        )

                        studentsAdapter.setSelectedStudents(creationFilterSharedViewModel.nmState.students.map { it.id }
                            .toSet())
                        studentsAdapter.notifyDataSetChanged()
                        mainActivityViewModel.triggerSelectAllEvent(false)
                    } else {
                        creationFilterSharedViewModel.obtainWish(
                            CreationFilterWish.DismissStudents(
                                creationFilterSharedViewModel.nmState.students.map { it.id }
                                    .toSet()
                            )
                        )
                        studentsAdapter.setSelectedStudents(emptySet())
                        studentsAdapter.notifyDataSetChanged()
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

    override fun renderState(state: CreationFilterState) {
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

    override fun renderNews(new: CreationFilterNews) {
        when (new) {
            is CreationFilterNews.Message -> {
                Toast.makeText(requireContext(), new.content, Toast.LENGTH_SHORT).show()
            }
        }
    }
}